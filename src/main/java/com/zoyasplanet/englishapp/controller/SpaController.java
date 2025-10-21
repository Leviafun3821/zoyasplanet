package com.zoyasplanet.englishapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SpaController {

    @SuppressWarnings("unused")
    @GetMapping("/{path:[^.]*}")
    public String redirect(@PathVariable("path") String path) {
        return "forward:/index.html";
    }

}
