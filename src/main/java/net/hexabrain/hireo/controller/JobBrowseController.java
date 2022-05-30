package net.hexabrain.hireo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/job")
public class JobBrowseController {

    @GetMapping("/list")
    public String list() {
        return "job/list";
    }
}
