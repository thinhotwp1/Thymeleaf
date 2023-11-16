package com.tpb.vms.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HydroHashUtil {
	private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

	private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		// Static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	private static String toHexString(byte[] hash) {
		// Convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);
		// Convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

	public static String toHex(byte[] data) {
		char[] chars = new char[data.length * 2];
		for (int i = 0; i < data.length; i++) {
			chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
			chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
		}
		return new String(chars);
	}

	public static String hashTransactionKey(String strToHash) throws NoSuchAlgorithmException {
		return toHex(getSHA(strToHash));
	}

}
