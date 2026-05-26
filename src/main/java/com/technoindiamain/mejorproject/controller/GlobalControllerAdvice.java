package com.technoindiamain.mejorproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.technoindiamain.mejorproject.entity.PageContent;
import com.technoindiamain.mejorproject.service.CollegeService;

@ControllerAdvice(assignableTypes = { PublicController.class })
public class GlobalControllerAdvice {

    private final CollegeService collegeService;

    public GlobalControllerAdvice(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("explorePages", collegeService.getPageContentsByCategory(PageContent.Category.EXPLORE));
        model.addAttribute("academicPages", collegeService.getPageContentsByCategory(PageContent.Category.ACADEMIC));
        model.addAttribute("facilitiesPages",
                collegeService.getPageContentsByCategory(PageContent.Category.FACILITIES));
        model.addAttribute("siteSetting", collegeService.getSiteSetting());
    }
}
