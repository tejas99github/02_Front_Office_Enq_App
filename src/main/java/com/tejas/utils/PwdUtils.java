package com.tejas.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils {

	private PwdUtils() {

	}

	public static String generateRandomPwd() {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return RandomStringUtils.random(6, characters);

	}
}
