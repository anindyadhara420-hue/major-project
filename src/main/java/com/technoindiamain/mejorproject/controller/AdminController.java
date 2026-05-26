package com.technoindiamain.mejorproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.technoindiamain.mejorproject.entity.Course;
import com.technoindiamain.mejorproject.entity.Degree;
import com.technoindiamain.mejorproject.entity.PageContent;
import com.technoindiamain.mejorproject.service.CollegeService;
import com.technoindiamain.mejorproject.service.FileUploadService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CollegeService collegeService;
    private final FileUploadService fileUploadService;

    public AdminController(CollegeService collegeService, FileUploadService fileUploadService) {
        this.collegeService = collegeService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public String dashboard() {
        return "admin/dashboard";
    }

    // --- Degree Management ---

    @GetMapping("/degrees")
    public String listDegrees(Model model) {
        model.addAttribute("degrees", collegeService.getAllDegrees());
        return "admin/degrees";
    }

    @GetMapping("/degrees/new")
    public String showCreateDegreeForm(Model model) {
        model.addAttribute("degree", new Degree());
        return "admin/degree_form";
    }

    @PostMapping("/degrees")
    public String saveDegree(@ModelAttribute("degree") Degree degree) {
        collegeService.saveDegree(degree);
        return "redirect:/admin/degrees";
    }

    @GetMapping("/degrees/edit/{id}")
    public String showEditDegreeForm(@PathVariable Long id, Model model) {
        Degree degree = collegeService.getDegreeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid degree Id:" + id));
        model.addAttribute("degree", degree);
        return "admin/degree_form";
    }

    @GetMapping("/degrees/delete/{id}")
    public String deleteDegree(@PathVariable Long id) {
        collegeService.deleteDegree(id);
        return "redirect:/admin/degrees";
    }

    // --- Course Management ---

    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", collegeService.getAllCourses());
        return "admin/courses";
    }

    @GetMapping("/courses/new")
    public String showCreateCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("degrees", collegeService.getAllDegrees());
        return "admin/course_form";
    }

    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course) {
        collegeService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditCourseForm(@PathVariable Long id, Model model) {
        Course course = collegeService.getCourseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("course", course);
        model.addAttribute("degrees", collegeService.getAllDegrees());
        return "admin/course_form";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        collegeService.deleteCourse(id);
        return "redirect:/admin/courses";
    }

    // --- Page Content Management ---

    @GetMapping("/pages")
    public String listPages(Model model) {
        model.addAttribute("pages", collegeService.getAllPageContents());
        return "admin/page_contents";
    }

    @GetMapping("/pages/new")
    public String showCreatePageForm(@RequestParam(required = false) String slug,
            @RequestParam(required = false) String category,
            Model model) {
        PageContent page = new PageContent();
        if (slug != null) {
            page.setSlug(slug);
        }
        if (category != null) {
            try {
                page.setCategory(PageContent.Category.valueOf(category.toUpperCase()));
            } catch (Exception e) {
                // Ignore invalid category
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("categories", PageContent.Category.values());
        return "admin/page_content_form";
    }

    @PostMapping("/pages")
    public String savePage(@ModelAttribute("page") PageContent page,
            @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile,
            @RequestParam(value = "documentFile", required = false) org.springframework.web.multipart.MultipartFile documentFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileUploadService.uploadFile(imageFile);
                page.setImageUrl(imagePath);
            } else if (page.getId() != null) {
                // Retain existing image if not uploading new one
                collegeService.getPageContentById(page.getId()).ifPresent(existing -> {
                    if (existing.getImageUrl() != null) {
                        page.setImageUrl(existing.getImageUrl());
                    }
                });
            }
            
            if (documentFile != null && !documentFile.isEmpty()) {
                String documentPath = fileUploadService.uploadFile(documentFile);
                page.setDocumentUrl(documentPath);
            } else if (page.getId() != null) {
                // Retain existing document if not uploading new one
                collegeService.getPageContentById(page.getId()).ifPresent(existing -> {
                    if (existing.getDocumentUrl() != null) {
                        page.setDocumentUrl(existing.getDocumentUrl());
                    }
                });
            }

            collegeService.savePageContent(page);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            // Handle error appropriately, maybe add a flash attribute for error
        }
        return "redirect:/admin/pages";
    }

    @GetMapping("/pages/edit/{id}")
    public String showEditPageForm(@PathVariable Long id, Model model) {
        PageContent page = collegeService.getPageContentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid page Id:" + id));
        model.addAttribute("page", page);
        model.addAttribute("categories", PageContent.Category.values());
        return "admin/page_content_form";
    }

    @GetMapping("/pages/delete/{id}")
    public String deletePage(@PathVariable Long id) {
        collegeService.deletePageContent(id);
        return "redirect:/admin/pages";
    }

    @GetMapping("/pages/edit-by-slug/{slug}")
    public String editPageBySlug(@PathVariable String slug, @RequestParam(required = false) String category, Model model) {
        return collegeService.getPageContentBySlug(slug)
                .map(page -> {
                    model.addAttribute("page", page);
                    model.addAttribute("categories", PageContent.Category.values());
                    return "admin/page_content_form";
                })
                .orElseGet(() -> {
                    PageContent page = new PageContent();
                    page.setSlug(slug);
                    if (category != null) {
                        try {
                            page.setCategory(PageContent.Category.valueOf(category.toUpperCase()));
                        } catch (Exception e) {}
                    }
                    model.addAttribute("page", page);
                    model.addAttribute("categories", PageContent.Category.values());
                    return "admin/page_content_form";
                });
    }

    // --- Notice Management ---

    @GetMapping("/notices")
    public String listNotices(Model model) {
        model.addAttribute("notices", collegeService.getAllNotices());
        return "admin/notices";
    }

    @GetMapping("/notices/new")
    public String showCreateNoticeForm(Model model) {
        model.addAttribute("notice", new com.technoindiamain.mejorproject.entity.Notice());
        model.addAttribute("categories", com.technoindiamain.mejorproject.entity.Notice.Category.values());
        return "admin/notice_form";
    }

    @PostMapping("/notices")
    public String saveNotice(@ModelAttribute("notice") com.technoindiamain.mejorproject.entity.Notice notice) {
        if (notice.getPublishDate() == null) {
            notice.setPublishDate(java.time.LocalDate.now());
        }
        collegeService.saveNotice(notice);
        return "redirect:/admin/notices";
    }

    @GetMapping("/notices/edit/{id}")
    public String showEditNoticeForm(@PathVariable Long id, Model model) {
        com.technoindiamain.mejorproject.entity.Notice notice = collegeService.getNoticeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + id));
        model.addAttribute("notice", notice);
        model.addAttribute("categories", com.technoindiamain.mejorproject.entity.Notice.Category.values());
        return "admin/notice_form";
    }

    @GetMapping("/notices/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        collegeService.deleteNotice(id);
        return "redirect:/admin/notices";
    }

    // --- Event Management ---

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", collegeService.getAllEvents());
        return "admin/events";
    }

    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new com.technoindiamain.mejorproject.entity.Event());
        return "admin/event_form";
    }

    @PostMapping("/events")
    public String saveEvent(@ModelAttribute("event") com.technoindiamain.mejorproject.entity.Event event,
                            @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileUploadService.uploadFile(imageFile);
                event.setImageUrl(imagePath);
            } else if (event.getId() != null) {
                // If editing and no new image, keep old image
                com.technoindiamain.mejorproject.entity.Event existingEvent = collegeService.getEventById(event.getId()).orElse(null);
                if (existingEvent != null) {
                    event.setImageUrl(existingEvent.getImageUrl());
                }
            }
            collegeService.saveEvent(event);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/events";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        com.technoindiamain.mejorproject.entity.Event event = collegeService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        model.addAttribute("event", event);
        return "admin/event_form";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        collegeService.deleteEvent(id);
        return "redirect:/admin/events";
    }

    // --- Gallery Management ---

    @GetMapping("/gallery")
    public String listGalleryImages(Model model) {
        model.addAttribute("images", collegeService.getAllGalleryImages());
        return "admin/gallery";
    }

    @GetMapping("/gallery/new")
    public String showCreateGalleryForm(Model model) {
        model.addAttribute("galleryImage", new com.technoindiamain.mejorproject.entity.GalleryImage());
        return "admin/gallery_form";
    }

    @PostMapping("/gallery")
    public String saveGalleryImage(@ModelAttribute("galleryImage") com.technoindiamain.mejorproject.entity.GalleryImage galleryImage,
                                   @RequestParam(value = "imageFiles", required = false) org.springframework.web.multipart.MultipartFile[] imageFiles) {
        try {
            if (imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()) {
                for (org.springframework.web.multipart.MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        String imagePath = fileUploadService.uploadFile(file);
                        com.technoindiamain.mejorproject.entity.GalleryImage newImage = new com.technoindiamain.mejorproject.entity.GalleryImage();
                        newImage.setImageUrl(imagePath);
                        newImage.setCategoryOrEvent(galleryImage.getCategoryOrEvent());
                        collegeService.saveGalleryImage(newImage);
                    }
                }
            } else {
                 collegeService.saveGalleryImage(galleryImage);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/gallery";
    }

    @GetMapping("/gallery/delete/{id}")
    public String deleteGalleryImage(@PathVariable Long id) {
        collegeService.deleteGalleryImage(id);
        return "redirect:/admin/gallery";
    }

    // --- Contact Message Management ---

    @GetMapping("/contact-messages")
    public String listContactMessages(Model model) {
        model.addAttribute("messages", collegeService.getAllContactMessages());
        return "admin/contact_messages";
    }

    @GetMapping("/contact-messages/delete/{id}")
    public String deleteContactMessage(@PathVariable Long id) {
        collegeService.deleteContactMessage(id);
        return "redirect:/admin/contact-messages";
    }

    // --- Site Settings ---
    @GetMapping("/settings")
    public String viewSiteSettings(Model model) {
        model.addAttribute("siteSetting", collegeService.getSiteSetting());
        return "admin/site_settings";
    }

    @PostMapping("/settings/save")
    public String saveSiteSettings(@ModelAttribute("siteSetting") com.technoindiamain.mejorproject.entity.SiteSetting siteSetting,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        collegeService.saveSiteSetting(siteSetting);
        redirectAttributes.addFlashAttribute("successMessage", "Site settings updated successfully.");
        return "redirect:/admin/settings";
    }

    // --- Hero Slider Management ---

    @GetMapping("/hero-sliders")
    public String listHeroSliders(Model model) {
        model.addAttribute("sliders", collegeService.getAllHeroSliders());
        return "admin/hero_sliders";
    }

    @GetMapping("/hero-sliders/new")
    public String showCreateHeroSliderForm(Model model) {
        model.addAttribute("slider", new com.technoindiamain.mejorproject.entity.HeroSlider());
        return "admin/hero_slider_form";
    }

    @PostMapping("/hero-sliders")
    public String saveHeroSlider(@ModelAttribute("slider") com.technoindiamain.mejorproject.entity.HeroSlider slider,
                                 @RequestParam(value = "imageFile", required = false) org.springframework.web.multipart.MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = fileUploadService.uploadFile(imageFile);
                slider.setImageUrl(imagePath);
            } else if (slider.getId() != null) {
                com.technoindiamain.mejorproject.entity.HeroSlider existingSlider = collegeService.getHeroSliderById(slider.getId()).orElse(null);
                if (existingSlider != null) {
                    slider.setImageUrl(existingSlider.getImageUrl());
                }
            }
            collegeService.saveHeroSlider(slider);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/hero-sliders";
    }

    @GetMapping("/hero-sliders/edit/{id}")
    public String showEditHeroSliderForm(@PathVariable Long id, Model model) {
        com.technoindiamain.mejorproject.entity.HeroSlider slider = collegeService.getHeroSliderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid slider Id:" + id));
        model.addAttribute("slider", slider);
        return "admin/hero_slider_form";
    }

    @GetMapping("/hero-sliders/delete/{id}")
    public String deleteHeroSlider(@PathVariable Long id) {
        collegeService.deleteHeroSlider(id);
        return "redirect:/admin/hero-sliders";
    }

    // --- Admission Management ---

    @GetMapping("/admissions")
    public String listAdmissions(Model model) {
        model.addAttribute("admissions", collegeService.getAllAdmissions());
        return "admin/admissions";
    }
}
