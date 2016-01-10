import functools
import sys
import pyinotify
import subprocess
import os
import re
import chardet #@UnresolvedImport
import threading

class smiItem(object):
	def __init__(self):
		self.start_ms = 0L
		self.start_ts = '00:00:00,000'
		self.end_ms = 0L
		self.end_ts = '00:00:00,000'
		self.contents = None
		self.linecount = 0
	@staticmethod
	def ms2ts(ms):
		hours = ms / 3600000L
		ms -= hours * 3600000L
		minutes = ms / 60000L
		ms -= minutes * 60000L
		seconds = ms / 1000L
		ms -= seconds * 1000L
		s = '%02d:%02d:%02d,%03d' % (hours, minutes, seconds, ms)
		return s
	def convertSrt(self):
		if self.linecount == 4:
			i=1 #@UnusedVariable
		# 1) convert timestamp
		self.start_ts = smiItem.ms2ts(self.start_ms)
		self.end_ts = smiItem.ms2ts(self.end_ms-10)
		# 2) remove new-line
		self.contents = re.sub(r'\s+', ' ', self.contents)
		# 3) remove web string like "&nbsp";
		self.contents = re.sub(r'&[a-z]{2,5};', '', self.contents)
		# 4) replace "<br>" with '\n';
		self.contents = re.sub(r'(<br>)+', '\n', self.contents, flags=re.IGNORECASE)
		# 5) find all tags
		fndx = self.contents.find('<')
		if fndx >= 0:
			contents = self.contents
			sb = self.contents[0:fndx]
			contents = contents[fndx:]
			while True:
				m = re.match(r'</?([a-z]+)[^>]*>([^<>]*)', contents, flags=re.IGNORECASE)
				if m == None: break
				contents = contents[m.end(2):]
				#if m.group(1).lower() in ['font', 'b', 'i', 'u']:
				if m.group(1).lower() in ['b', 'i', 'u']:
					sb += m.string[0:m.start(2)]
				sb += m.group(2)
			self.contents = sb
		self.contents = self.contents.strip()
		self.contents = self.contents.strip('\n')
	def __repr__(self):
		s = '%d:%d:<%s>:%d' % (self.start_ms, self.end_ms, self.contents, self.linecount)
		return s

def convertSMI(smi_file):
	if not os.path.exists(smi_file):
		sys.stderr.write('Cannot find smi file <%s>\n' % smi_file)
		return False
	rndx = smi_file.rfind('.')
	srt_file = '%s.srt' % smi_file[0:rndx]

	ifp = open(smi_file)
	smi_sgml = ifp.read()#.upper()
	ifp.close()
	chdt = chardet.detect(smi_sgml)
	if chdt['encoding'] != 'UTF-8':
		smi_sgml = unicode(smi_sgml, chdt['encoding'].lower()).encode('utf-8')

	# skip to first starting tag (skip first 0xff 0xfe ...)
	try:
		fndx = smi_sgml.find('<SYNC')
	except Exception, e:
		print chdt
		raise e
	if fndx < 0:
		return False
	smi_sgml = smi_sgml[fndx:]
	lines = smi_sgml.split('\n')
	
	srt_list = []
	sync_cont = ''
	si = None
	last_si = None
	linecnt = 0
	for line in lines:
		linecnt += 1
		sndx = line.upper().find('<SYNC')
		if sndx >= 0:
			m = re.search(r'<sync\s+start\s*=\s*(\d+)>(.*)$', line, flags=re.IGNORECASE)
			if not m:
				raise Exception('Invalid format tag of <Sync start=nnnn> with "%s"' % line)
			sync_cont += line[0:sndx]
			last_si = si
			if last_si != None:
				last_si.end_ms = long(m.group(1))
				last_si.contents = sync_cont
				srt_list.append(last_si)
				last_si.linecount = linecnt
				#print '[%06d] %s' % (linecnt, last_si)
			sync_cont = m.group(2)
			si = smiItem()
			si.start_ms = long(m.group(1))
		else:
			sync_cont += line
			
	ofp = open(srt_file, 'w')
	ndx = 1
	for si in srt_list:
		si.convertSrt()
		if si.contents == None or len(si.contents) <= 0:
			continue
		#print si
		sistr = '%d\n%s --> %s\n%s\n\n' % (ndx, si.start_ts, si.end_ts, si.contents)
		#sistr = unicode(sistr, 'utf-8').encode('euc-kr')
		ofp.write(sistr)
		ndx += 1
	ofp.close()
	return True

#def convertSMIWrapper(logger, smi_file):
#	if convertSMI(smi_file):
#		logger.write("Converting <%s> OK!" % smi_file)
#	else:
#		logger.write("Converting <%s> Failed" % smi_file)

class Smi2Srt(pyinotify.ProcessEvent):
	def __init__(self, logger):
		self._logger = logger;

	def process_IN_CLOSE_WRITE(self, event):
		if event.dir == False:
#			if convertSMI(event.pathname):
#				self._logger.write("Converting <%s> OK!" % event.pathname)
#			else:
#				self._logger.write("Converting <%s> Failed" % event.pathname)
#			converter = threading.Thread(target=convertSMIWrapper, args=(self._logger, event.pathname))
#			converter.start()
			convertSMI(event.pathname)

fo = file('/tmp/smi2srtAutochanger.log', 'w');
try:
	wm = pyinotify.WatchManager()
	handler = Smi2Srt(logger=fo)
	notifier = pyinotify.Notifier(wm, default_proc_fun=handler)
	
	wm.add_watch('/home/sh/trunk', pyinotify.IN_CLOSE_WRITE)
	wm.add_watch('/home/sh/usb', pyinotify.IN_CLOSE_WRITE)
	
	notifier.loop()
	
except pyinotify.NotifierError, err:
	print >> sys.stderr, err

finally:
	fo.close
