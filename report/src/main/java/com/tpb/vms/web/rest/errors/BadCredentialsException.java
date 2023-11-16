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
public class BadCredentialsException extends VmsException {

    public BadCredentialsException(String message) {
        super(null, null, message);
    }

}
