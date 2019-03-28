package ebing.top.dog.service.clazz;

public class Apple {
	private String color;

	public Apple(String color) {
		this.color = color;
	}

	/**
	 * 如果要判断其相等，可以 重写其 equals 和 hashCode 方法，HashMap 的 Key 值可以是 普通new 出来的对象，也可以是 null
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (!(obj instanceof Apple)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return this.color.equals(((Apple) obj).color);
	}

	@Override
	public int hashCode(){
		return this.color.hashCode();
	}
}
