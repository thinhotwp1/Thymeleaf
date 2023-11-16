/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.common;

/**
 *
 * @author Viet Do-Van
 */
public class AppConst {

    public static final String FORMAT_DATE = "dd/MM/yyyy";

    public static class SqlResult {

        public static final String COLUMN_ERROR_CODE_NAME = "ECODE";
        public static final String COLUMN_ERROR_DESCRIPTION_NAME = "EDESC";
        public static final String SUCCESS_CODE = "00";
    }

    public enum ActionStatus {
        OPEN("O"), CLOSE("C");
        private final String code;

        ActionStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    public enum AppStatus {
        OPEN("O"), CLOSE("C");
        private final String code;

        AppStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

    
}
