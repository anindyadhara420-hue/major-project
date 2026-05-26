package com.technoindiamain.mejorproject.controller;

import com.technoindiamain.mejorproject.entity.AdminUser;
import com.technoindiamain.mejorproject.service.AdminUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

    private final AdminUserService adminUserService;

    public AdminProfileController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public String viewProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Optional<AdminUser> adminUserOpt = adminUserService.getAdminUserByUsername(username);
        
        if (adminUserOpt.isPresent()) {
            model.addAttribute("adminUser", adminUserOpt.get());
        } else {
            return "redirect:/login?logout";
        }
        return "admin/admin_profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match.");
            return "redirect:/admin/profile";
        }

        String username = authentication.getName();
        Optional<AdminUser> adminUserOpt = adminUserService.getAdminUserByUsername(username);
        
        if (adminUserOpt.isPresent()) {
            AdminUser adminUser = adminUserOpt.get();
            adminUser.setPassword(newPassword);
            adminUserService.saveAdminUser(adminUser); // Will encode it
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully. Please login again with the new password.");
            return "redirect:/logout";
        }

        return "redirect:/admin/profile";
    }
}
