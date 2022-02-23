package test;

import core.Core;

public class Test {

	public static void main(String[] args) {
		try {
			Core.init();
			Core.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
