package controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;

@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @RequestMapping("/f")
    public String f() {
        return "test";
    }
}
