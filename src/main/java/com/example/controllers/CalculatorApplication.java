package com.example.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SpringBootApplication
@Controller
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "calculate";
    }

    @GetMapping("/error")
    public String showErrorPage(Model model) {
        model.addAttribute("error", "An error occurred. Please check your expression and try again.");
        return "calculate"; // Return the same calculate page with the error message
    }

    @GetMapping("/calculate")
    public String calculateExpression(@RequestParam("expression") String expression, Model model) {
        try {
            double result = evaluateExpression(expression);
            model.addAttribute("result", result);
        } catch (ScriptException e) {
            return "redirect:/error"; // Redirect to the error page
        }
        return "calculate";
    }


    private double evaluateExpression(String expression) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn"); // Используем Nashorn (JavaScript для Java) движок выполнения
        if (engine == null) {
            throw new ScriptException("Script engine is null");
        }
        return (double) engine.eval(expression);
    }
}
