/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpb.vms.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Viet Do-Van
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public ModelMapper createModelMapper() {
        return new ModelMapper();
    }
}
