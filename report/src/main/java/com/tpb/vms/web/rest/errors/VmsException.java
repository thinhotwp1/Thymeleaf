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
public class VmsException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4165174023978038335L;

	private final String status;
    private final String code;
    private final List<String> listParams;

    
    public VmsException() {
		this.status = null;
		this.code = null;
		this.listParams = null;
	}

	public VmsException(String status, String code, String message) {
		super(message);
		this.status = status;
		this.code = code;
		this.listParams = null;
	}

	public VmsException(String status, String code, String message, List<String> listParams) {
		super(message);
		this.status = status;
		this.code = code;
		this.listParams = listParams;
	}

}
