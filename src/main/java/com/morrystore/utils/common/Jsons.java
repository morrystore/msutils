package com.morrystore.utils.common;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Jsons {
	private static Gson gson;

	private Jsons() {
	}

	static {
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gson = gb.create();
	}

	public static final String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static final <T> T fromJson(final String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static final <T> T fromJson(final String json, Type t) {
		return gson.fromJson(json, t);
	}
}
