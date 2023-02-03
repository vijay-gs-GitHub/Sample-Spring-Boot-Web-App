package com.poc.springboot.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UtilServ{

static Logger logger = LogManager.getLogger(UtilServ.class);


public static boolean isPasswordMatching(String userPass, String sysPass) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(sysPass.getBytes());
    byte[] digest = md.digest();
    String passHash = bytesToHex(digest);
    logger.info("pass hash "+passHash);
    return passHash.equals(userPass);
}

private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
        sb.append(String.format("%02x", b));
    }
    return sb.toString();
}

}