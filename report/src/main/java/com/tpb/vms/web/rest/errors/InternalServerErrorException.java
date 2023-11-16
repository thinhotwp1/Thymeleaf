/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

/**
 *
 * @author Viet Do-Van
 */
public class InternalServerErrorException extends VmsException {

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String status, String message) {
        super(status, null, message);
    }

}
