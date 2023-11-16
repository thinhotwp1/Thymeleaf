package com.example.thymeleaf;

import java.util.ArrayList;
import java.util.List;

@RestController("rest")
public class RestControllerEmployee {

    @GetMapping("/get-list-employee")
    public List<Employee> getListEmployee(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Thinh",21,"IT"));
        list.add(new Employee(2,"Chou",21,"HR"));
        list.add(new Employee(3,"Duy",21,"Help Desk"));
        return list;
    }
}
