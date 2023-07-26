package com.example.thymeleaf;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/hi")
    public String viewHomePage(Model model){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Thinh",21,"IT"));
        list.add(new Employee(2,"Chou",21,"HR"));
        list.add(new Employee(3,"Duy",21,"Help Desk"));
        model.addAttribute("list",list);
        return "index";
    }
}
