package com.example.analyzer.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    public Map<String, Object> analyzeResume(MultipartFile file, String jobDescription) {

        Map<String, Object> result = new HashMap<>();

        try {

            InputStream inputStream = file.getInputStream();
            PDDocument document = PDDocument.load(inputStream);

            PDFTextStripper stripper = new PDFTextStripper();
            String resumeText = stripper.getText(document).toLowerCase();

            document.close();

            String jdText = jobDescription.toLowerCase();

            List<String> skills = Arrays.asList(
                    "java",
                    "python",
                    "sql",
                    "html",
                    "css",
                    "spring",
                    "spring boot",
                    "git",
                    "docker",
                    "machine learning"
            );

            List<String> matchedSkills = new ArrayList<>();
            List<String> missingSkills = new ArrayList<>();

            for (String skill : skills) {

                if (jdText.contains(skill)) {

                    if (resumeText.contains(skill)) {
                        matchedSkills.add(skill.toUpperCase());
                    } else {
                        missingSkills.add(skill.toUpperCase());
                    }

                }

            }

            int totalRequired = matchedSkills.size() + missingSkills.size();
            int score = 0;

            if (totalRequired > 0) {
                score = (matchedSkills.size() * 100) / totalRequired;
            }

            StringBuilder output = new StringBuilder();

            output.append("<h3>Matched Skills</h3>");

            for (String skill : matchedSkills) {
                output.append("<span style='color:green;'>✔ ")
                        .append(skill)
                        .append("</span><br>");
            }

            output.append("<h3>Missing Skills</h3>");

            for (String skill : missingSkills) {
                output.append("<span style='color:red;'>✘ ")
                        .append(skill)
                        .append("</span><br>");
            }

            output.append("<h3>Recommended Skills</h3>");

            for (String skill : missingSkills) {
                output.append("• Learn ")
                        .append(skill)
                        .append("<br>");
            }

            result.put("score", score);
            result.put("result", output.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}