package com.by.core.util;

import org.apache.commons.id.Hex;

public class UUIDUtil {

	public static String getUUID() {
		return new String(Hex.encodeHex(org.apache.commons.id.uuid.UUID.randomUUID().getRawBytes()));
	}
	
}
