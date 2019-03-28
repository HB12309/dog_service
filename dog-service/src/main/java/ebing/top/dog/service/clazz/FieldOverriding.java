package ebing.top.dog.service.clazz;

/**
 * 上面的Java的重写介绍中明确的说了，重写，指的是方法。并没有提到成员变量。通过上面的例子，其实我们也可以发现，成员变量并没有被重写。
 * 所以，Java中，成员变量并不会被重写。这里就有另外一个词：隐藏。
 * 第一次拿不到，以后拿得到对应的成员变量
 */
class Super {
	String s = "Super";

	String say(){
		return "hello Super";
	}
}

class Sub extends Super {
	String s = "Sub";

	@Override
	String say(){
		return "hello Sub";
	}
}

public class FieldOverriding {

	public static void main(String[] args) {
		Sub c1 = new Sub();
		System.out.println(" c1.s : " + c1.s);
		System.out.println(" c1.say : " + c1.say());

		/**
		 * 第一种，这样拿
		 */
		Super c2 = new Sub();
		System.out.println(" c2.s : " + c2.s);
		System.out.println(" c2.say : " + c2.say());
		/**
		 * 第二种，强制加上父类
		 */
		System.out.println("第二种，s:" + ((Super)c1).s);
	}
}
