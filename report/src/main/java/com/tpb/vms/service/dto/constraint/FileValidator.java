/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.service.dto.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Viet Do-Van
 */
@Slf4j
public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (value.getSize() > 50000000) {
            return false;
        }
        String originalFilename = value.getOriginalFilename();
        if (originalFilename == null || StringUtils.isEmpty(originalFilename) || originalFilename.length() > FILE_NAME_MAX_LENGTH) {
            return false;
        }
        String filebasename = FilenameUtils.getBaseName(originalFilename);
        long count = FILENAME_BLACK_LIST.stream().filter(x -> StringUtils.contains(filebasename, x)).count();
        if (count > 0) {
            return false;
        }
        String extension = FilenameUtils.getExtension(originalFilename);
        count = EXTENSION_WHITE_LIST.stream().filter(x -> StringUtils.equalsIgnoreCase(x, extension)).count();
        return count > 0;
    }

    private static final List<String> EXTENSION_WHITE_LIST = Arrays.asList("doc", "docx", "dot", "dotx",
            "eml",
            "gif",
            "htm", "html",
            "jpg", "jpeg",
            "pdf", "png",
            "ppt", "pptm", "pptx",
            "txt",
            "xla", "xlam", "xls", "xlsm", "xlsx","msg");

    private static final List<String> FILENAME_BLACK_LIST = Arrays.asList("/",
            "\\",
            ".",
            "whoami",
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
            ";",
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

    private static final int FILE_NAME_MAX_LENGTH = 300;
}
