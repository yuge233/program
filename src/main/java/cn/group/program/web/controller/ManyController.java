package cn.group.program.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManyController {

    @GetMapping("/many")
    public String many(){
        return "many";
    }
}
