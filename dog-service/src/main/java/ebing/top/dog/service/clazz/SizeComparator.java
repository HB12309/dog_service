package ebing.top.dog.service.clazz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class HDTV {
	private int size;
	private String brand;

	public HDTV(int size, String brand) {
		this.size = size;
		this.brand = brand;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}

class SizeComparator implements Comparator<HDTV> {
	@Override
	public int compare(HDTV tv1, HDTV tv2) {
		int tv1Size = tv1.getSize();
		int tv2Size = tv2.getSize();

		if (tv1Size > tv2Size) {
			return 1;
		} else if (tv1Size < tv2Size) {
			return -1;
		} else {
			return 0;
		}
	}
}

/**
 * 不允许出现Main函数，因为是公共的
 */
//public class Main {
//	public static void main(String[] args) {
//		HDTV tv1 = new HDTV(55, "Samsung");
//		HDTV tv2 = new HDTV(60, "Sony");
//		HDTV tv3 = new HDTV(42, "Panasonic");
//
//		ArrayList<HDTV> al = new ArrayList<HDTV>();
//		al.add(tv1);
//		al.add(tv2);
//		al.add(tv3);
//
//		Collections.sort(al, new SizeComparator());
//		for (HDTV a : al) {
//			System.out.println(a.getBrand());
//		}
//	}
//}
