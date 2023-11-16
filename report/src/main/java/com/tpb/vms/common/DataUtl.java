package com.tpb.vms.common;

import java.util.Map;

public class DataUtl {
	private DataUtl() {
		   throw new IllegalStateException("Utility Class");
	}
	public static String getString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String getData(Map<String, Object> map, String key) {
		return getString(map.get(key.toUpperCase()));
	}
}
