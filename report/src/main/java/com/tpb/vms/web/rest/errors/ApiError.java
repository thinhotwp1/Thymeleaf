/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.web.rest.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Viet Do-Van
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String status;
    private String message;
}
