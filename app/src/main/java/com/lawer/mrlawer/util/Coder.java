package com.lawer.mrlawer.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Coder {

    public static String encodePassword(String userName, String passWord) {
        return new String(Hex.encodeHex(DigestUtils.md5(userName + passWord)));
    }
}
