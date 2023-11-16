/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Viet Do-Van
 */
@Component
public class MessageProvider {

    private final MessageSource messageSource;

    public MessageProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, DEFAULT_LOCALE);
    }

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

}
