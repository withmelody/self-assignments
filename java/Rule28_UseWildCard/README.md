[LINK](http://stackoverflow.com/questions/1906677/super-e-and-extends-e-for-list)
```
"? extends A" means "some type derived from A (or A itself)". So for instance, a List<ByteArrayOutputStream> is compatible with List<? extends OutputStream> - but you shouldn't be able to add a FileOutputStream to such a list - it's meant to be a List<ByteArrayOutputStream>! All you know is that anything you fetch from the list will be an OutputStream of some kind.

"? super A" means "some type which is a superclass of A (or A itself)". So for instance, a List<OutputStream> is compatible with List<? super ByteArrayOutputStream>. You can definitely add a ByteArrayOutputStream to such a list - but if you fetch an item from the list, you can't really guarantee much about it.
```


If you hard to choose which one is appropriate, just remember below

PECS ( Produce - Extends, Consumer - Super )
