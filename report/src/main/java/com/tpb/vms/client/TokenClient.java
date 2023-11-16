/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.client;

/**
 *
 * @author Viet Do-Van
 */
public interface TokenClient {

    public boolean validateToken(String token);

    static final String API_KEY_HEADER = "apikey";
}
