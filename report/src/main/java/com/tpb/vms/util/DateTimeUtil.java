/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * @author Viet Do-Van
 */
public class DateTimeUtil {

    private DateTimeUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static String format(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static String defaultFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
        return localDateTime.format(formatter);
    }

    public static String isoDateTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static Date parseIsoDateTime(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static Date tryParseIsoDateTime(String date) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDateTime tryParseIsoLocalDateTime(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static Date convertString2Date(String formatDate,String value){
        try {
            Date dtValue = new SimpleDateFormat(formatDate).parse(value);
            return dtValue;
        } catch (ParseException e) {
            return null;
        }
    }

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmss";
}
