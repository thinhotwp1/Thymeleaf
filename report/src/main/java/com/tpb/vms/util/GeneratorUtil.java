/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import java.time.LocalDateTime;
import java.util.Random;

/**
 *
 * @author Viet Do-Van
 */
public class GeneratorUtil {

    private GeneratorUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String generateRef(String cifNO, LocalDateTime localDateTime) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(REF_RANDOM_BOUND);
        String randomStr;
        if (randomNumber < 10) {
            randomStr = String.format("00%s", randomNumber);
        } else if (randomNumber < 100) {
            randomStr = String.format("0%s", randomNumber);
        } else {
            randomStr = String.valueOf(randomNumber);
        }
        StringBuilder sb = new StringBuilder();
        sb
                .append(cifNO)
                .append(DateTimeUtil.format(localDateTime, REF_DATE_TIME_FORMAT))
                .append(randomStr);
        return sb.toString();
    }

    public static String generateId(String customerGroup, LocalDateTime localDateTime) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(ID_RANDOM_BOUND);
        String randomStr;
        if (randomNumber < 10) {
            randomStr = String.format("00000%s", randomNumber);
        } else if (randomNumber < 100) {
            randomStr = String.format("0000%s", randomNumber);
        } else if (randomNumber < 1000) {
            randomStr = String.format("000%s", randomNumber);
        } else if (randomNumber < 10000) {
            randomStr = String.format("00%s", randomNumber);
        } else if (randomNumber < 100000) {
            randomStr = String.format("0%s", randomNumber);
        } else {
            randomStr = String.valueOf(randomNumber);
        }

        StringBuilder sb = new StringBuilder();
        sb
                .append(customerGroup)
                .append(DateTimeUtil.format(localDateTime, ID_DATE_TIME_FORMAT))
                .append(randomStr);
        return sb.toString();
    }

    private static final String REF_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String ID_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final int ID_RANDOM_BOUND = 1000000;
    private static final int REF_RANDOM_BOUND = 1000;
}
