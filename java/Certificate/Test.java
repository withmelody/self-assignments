import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		Test t = new Test();
		// t.Question3();
		// t.Question6();
		// t.Question8();
		// t.Question15();
		// t.Question17();

		// 문제중에 이걸 쓰는게 있었는데..
		// (new Test()).Question6();
	}

	public void Question3() {
		int i = 0;

		do {
			i++;
			if (i % 2 == 0) {
				continue;
			}
			System.out.print(i++);
		} while (i < 6);
	}

	private void Question6() {
		Book book = new Book("C", "Lee", "500");
		book.print();
		book.methodA(book);
		book.print();
	}

	private void Question8() {
		Map<String, Phone> phoneList = new HashMap<>();
		phoneList.put("order1", new Phone("LG-A"));
		phoneList.put("order2", new SmartPhone("LG-B"));

		for (String order : phoneList.keySet()) {
			System.out.print(phoneList.get(order) + "");
		}
	}

	private void Question15() {
		List<Integer> list = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}

		int sum = 0;
		for (int data : list) {
			sum += data;
		}

		System.out.println(sum);
	}

	public static String Q17_result = "0";

	private void Question17() {
		Q17_method(1);
		System.out.println(Q17_result);
	}

	private void Q17_method(int i) {
		try {
			if (i == 1) {
				throw new Exception();
			}
			Q17_result += "1";
		} catch (Exception e) {
			Q17_result += "2";
			return;
		} finally {
			Q17_result += "3";
		}
		Q17_result += "4";
	}

	class Super {
		public final void methodA(Book book) {
		}
	}

	class Sub extends Super {
		// @Override
		// public void methodA() {} // error
	}

	class Book {
		String mAuthor, mTitle, mPage;

		public Book(String author, String title, String page) {
			mAuthor = author;
			mTitle = title;
			mPage = page;
		}

		public void methodA(Book book) {
			book = new Book("author", "title", "page");
		}

		public void print() {
			System.out.println(mAuthor + "-" + mTitle + "-" + mPage);
		}
	}

	class Phone {
		private String phoneModel;

		public Phone(String phoneModel) {
			this.phoneModel = phoneModel;
		}

		@Override
		public String toString() {
			return "LG-C";
		}
	}

	class SmartPhone extends Phone {
		public SmartPhone(String phoneModel) {
			super(phoneModel);
		}

		@Override
		public String toString() {
			return "LG-D";
		}
	}
}
