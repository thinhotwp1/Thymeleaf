/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Viet Do-Van
 */
@Slf4j
public class AppNodeKeyUtil {

    private AppNodeKeyUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String hashpw(String keyPlant) {
        return BCrypt.hashpw(keyPlant, BCrypt.gensalt(12));
    }

    public static Boolean checkpw(String plantext, String hashed) {
        return BCrypt.checkpw(plantext, hashed);
    }
}
