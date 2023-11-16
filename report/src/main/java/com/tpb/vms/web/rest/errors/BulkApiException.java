/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

import java.util.List;
import lombok.Getter;

/**
 *
 * @author Viet Do-Van
 */
@Getter
public class BulkApiException extends VmsException {

    private List<BulkItemError> errors;
    private List<BulkItemError> otherErrors;

    public BulkApiException(String status, String code, String message, List<BulkItemError> errors) {
        super(status, code, message);
        this.errors = errors;
    }

    public BulkApiException(String status, String code, String message, List<BulkItemError> errors, List<BulkItemError> otherErrors) {
        super(status, code, message);
        this.errors = errors;
        this.otherErrors = otherErrors;
    }

    public BulkApiException(String status, String code, String message) {
        super(status, code, message);
    }

}
