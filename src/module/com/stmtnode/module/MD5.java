package com.stmtnode.module;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private final String content;

	public MD5(String content) {
		this.content = content;
	}

	public MD5(byte[] bytes) {
		try {
			bytes = MessageDigest.getInstance("MD5").digest(bytes);
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			this.content = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return content.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MD5 other = (MD5) obj;
		if (!content.equals(other.content)) {
			return false;
		}
		return true;
	}

	public String get() {
		return content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return content;
	}

}
