/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author maykon
 *
 * fonte: http://www.asjava.com/core-java/java-md5-example/
 */
public class CriptoMD5 {

    public static String encryptMD5(String value) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");

        BigInteger number = new BigInteger(1, md.digest(value.getBytes()));

        String hash = number.toString(16);

        while (hash.length() < 32) {
            hash = "0" + hash;
        }

        return hash;
    }
}
