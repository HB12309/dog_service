package ebing.top.dog.service.clazz;

import java.io.Serializable;

public class Singleton implements Serializable {
	private volatile static Singleton singleton;
	private Singleton (){}
	public static Singleton getSingleton() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}

	/**
	 * 序列化会通过反射调用无参数的构造方法创建一个新的对象。
	 * 所以我们要自己定义 readResolve，目的：防止序列化、反序列化破坏单例
	 * @return
	 */
	private Object readResolve() {
		return singleton;
	}
}
