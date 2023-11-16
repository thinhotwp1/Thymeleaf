package com.example.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerEmployee {
    @Autowired
    RestControllerEmployee restControllerEmployee;

    @GetMapping("/hi")
    public String viewHomePage(Model model) {
        List<Employee> list = restControllerEmployee.getListEmployee();
        System.out.println(model.getClass());
        model.addAttribute("listData", list);
        return "index";
    }
}
