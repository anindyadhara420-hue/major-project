package com.technoindiamain.mejorproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.technoindiamain.mejorproject.entity.PageContent;
import com.technoindiamain.mejorproject.service.CollegeService;

@Controller
public class PublicController {

    private final CollegeService collegeService;

    public PublicController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping({ "/", "/index" })
    public String index(Model model) {
        model.addAttribute("degrees", collegeService.getAllDegrees());
        model.addAttribute("notices", collegeService.getAllNotices());
        model.addAttribute("events", collegeService.getAllEvents());
        model.addAttribute("images", collegeService.getAllGalleryImages());
        model.addAttribute("heroSliders", collegeService.getActiveHeroSliders());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/admissions")
    public String admissions(Model model) {
        model.addAttribute("admission", new com.technoindiamain.mejorproject.entity.Admission());
        model.addAttribute("courses", collegeService.getAllCourses());
        return "admissions";
    }

    @GetMapping("/explore/experience")
    public String experience(Model model) {
        model.addAttribute("page", collegeService
                .getPageContentByCategoryAndSlug(PageContent.Category.EXPLORE, "experience").orElse(new PageContent()));
        return "pages/explore/experience";
    }

    @GetMapping("/explore/mission-vision")
    public String missionVision(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.EXPLORE, "mission-vision")
                        .orElse(new PageContent()));
        return "pages/explore/mission";
    }

    @GetMapping("/explore/faculty")
    public String faculty(Model model) {
        model.addAttribute("page", collegeService
                .getPageContentByCategoryAndSlug(PageContent.Category.EXPLORE, "faculty").orElse(new PageContent()));
        return "pages/explore/faculty";
    }

    @GetMapping("/academic/program")
    public String academicProgram(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.ACADEMIC, "academic-program")
                        .orElse(new PageContent()));
        return "pages/academic/program";
    }

    @GetMapping("/academic/interactive-learning")
    public String interactiveLearning(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.ACADEMIC, "interactive-learning")
                        .orElse(new PageContent()));
        return "pages/academic/learning";
    }

    @GetMapping("/facilities/libraries")
    public String libraries(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.FACILITIES, "libraries")
                        .orElse(new PageContent()));
        return "pages/facilities/libraries";
    }

    @GetMapping("/facilities/laboratory")
    public String laboratory(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.FACILITIES, "laboratory")
                        .orElse(new PageContent()));
        return "pages/facilities/laboratory";
    }

    @GetMapping("/facilities/smart-class")
    public String smartClass(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.FACILITIES, "smart-class")
                        .orElse(new PageContent()));
        return "pages/facilities/smart_class";
    }

    @GetMapping("/facilities/soft-skills")
    public String softSkills(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.FACILITIES, "soft-skills")
                        .orElse(new PageContent()));
        return "pages/facilities/soft_skills";
    }

    @GetMapping("/notices")
    public String notices(Model model) {
        model.addAttribute("notices", collegeService.getAllNotices());
        return "notices";
    }
    
    @GetMapping("/placement")
    public String placement(Model model) {
        model.addAttribute("page",
                collegeService.getPageContentByCategoryAndSlug(PageContent.Category.PLACEMENT, "placement")
                        .orElse(new PageContent()));
        return "pages/placement";
    }

    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", collegeService.getAllEvents());
        return "events";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("images", collegeService.getAllGalleryImages());
        return "gallery";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactMessage", new com.technoindiamain.mejorproject.entity.ContactMessage());
        return "contact";
    }

    @org.springframework.web.bind.annotation.PostMapping("/contact/submit")
    public String submitContact(@org.springframework.web.bind.annotation.ModelAttribute("contactMessage") com.technoindiamain.mejorproject.entity.ContactMessage message,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        message.setSubmissionDate(java.time.LocalDateTime.now());
        collegeService.saveContactMessage(message);
        redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
        return "redirect:/contact";
    }

    @org.springframework.web.bind.annotation.PostMapping("/admissions/submit")
    public String submitAdmission(@org.springframework.web.bind.annotation.ModelAttribute("admission") com.technoindiamain.mejorproject.entity.Admission admission,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        admission.setSubmissionDate(java.time.LocalDateTime.now());
        collegeService.saveAdmission(admission);
        redirectAttributes.addFlashAttribute("successMessage", "Your admission application has been submitted successfully!");
        return "redirect:/admissions";
    }
}
