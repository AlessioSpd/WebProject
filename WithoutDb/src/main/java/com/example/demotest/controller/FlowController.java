package com.example.demotest.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FlowController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView  ciao(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginAdmin.html");
        return modelAndView;
    }
}
