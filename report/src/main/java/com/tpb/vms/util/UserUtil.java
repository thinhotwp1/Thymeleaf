/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Viet Do-Van
 */
public class UserUtil {

    private UserUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String getUserWithoutDomain(String fullUserId) {
        if (StringUtils.isEmpty(fullUserId)) {
            return StringUtils.EMPTY;
        }
        String[] arr = fullUserId.split(Pattern.quote("\\"));
        return arr[arr.length - 1];
    }

    public static String getDomainFromUser(String fullUserId) {
        if (StringUtils.isEmpty(fullUserId)) {
            return StringUtils.EMPTY;
        }
        String[] arr = fullUserId.split(Pattern.quote("\\"), 2);
        if (arr.length != 2) {
            return StringUtils.EMPTY;
        }
        return arr[0];
    }
}
