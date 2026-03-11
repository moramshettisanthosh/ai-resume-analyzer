package com.example.analyzer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.analyzer.service.ResumeService;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Analyze Resume
    @PostMapping("/analyze")
    public String analyzeResume(@RequestParam("file") MultipartFile file,
                                @RequestParam("jobDescription") String jobDescription,
                                Model model) {

        Map<String, Object> result = resumeService.analyzeResume(file, jobDescription);

        model.addAttribute("score", result.get("score"));
        model.addAttribute("result", result.get("result"));

        return "result";
    }
}