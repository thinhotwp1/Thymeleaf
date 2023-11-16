/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.service.dto.constraint;

import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Viet Do-Van
 */
public class CommandValidator implements ConstraintValidator<ValidCommand, String> {

    @Override
    public void initialize(ValidCommand constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        return BLACK_LIST.stream().filter(x -> StringUtils.containsIgnoreCase(value, x)).count() == 0;
    }

    private static final List<String> BLACK_LIST = Arrays.asList("whoami",
            "uname", "ver",
            "ifconfig", "ipconfig",
            "netstat",
            "ps", "tasklist",
            "ping",
            "telnet",
            "whoami",
            "nslookup",
            "&",
            "&&",
            "|",
            "||",
            //        ";",
            "0x0a",
            "\\n",
            "$",
            "`",
            "rm",
            "cp",
            "scp",
            "tracert",
            "powercfg",
            "shutdown",
            "systeminfo",
            "sfc",
            "chkdsk",
            "schtasks",
            "attrib");

}
