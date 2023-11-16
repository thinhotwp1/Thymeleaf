/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import com.tpb.LibEncrypt.LibEncrypt;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Viet Do-Van
 */
@Slf4j
public class CodecUtil {

    private CodecUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String base64Decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static String decrypt(String encryptedPassword) {
        try {
            return LibEncrypt.Decrypt(encryptedPassword, CodecUtil.base64Decode(DEFAULT_KEY));
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            return null;
        }
    }

    public static String encrypt(String plantextPwd) {
        try {
            return LibEncrypt.Encrypt(plantextPwd, CodecUtil.base64Decode(DEFAULT_KEY));
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(encrypt("vmsol#2014"));
    }

    private static final String DEFAULT_KEY = "cGhhbXRoYW5oY29uZw==";

}
