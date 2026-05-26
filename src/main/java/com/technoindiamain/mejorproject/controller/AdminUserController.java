package com.technoindiamain.mejorproject.controller;

import com.technoindiamain.mejorproject.entity.AdminUser;
import com.technoindiamain.mejorproject.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", adminUserService.getAllAdminUsers());
        return "admin/admin_users";
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("adminUser", new AdminUser());
        return "admin/admin_user_form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("adminUser") AdminUser adminUser, RedirectAttributes redirectAttributes) {
        try {
            // Handle edit: if password is empty, keep existing password
            if (adminUser.getId() != null && (adminUser.getPassword() == null || adminUser.getPassword().isEmpty())) {
                AdminUser existingUser = adminUserService.getAdminUserById(adminUser.getId()).orElse(null);
                if (existingUser != null) {
                    adminUser.setPassword(existingUser.getPassword());
                }
            }
            adminUserService.saveAdminUser(adminUser);
            redirectAttributes.addFlashAttribute("successMessage", "Admin user saved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving user. Username might already exist.");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        AdminUser user = adminUserService.getAdminUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        // Don't send password to view
        user.setPassword(""); 
        model.addAttribute("adminUser", user);
        return "admin/admin_user_form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (adminUserService.getAllAdminUsers().size() <= 1) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete the last admin user.");
            return "redirect:/admin/users";
        }
        adminUserService.deleteAdminUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "Admin user deleted successfully.");
        return "redirect:/admin/users";
    }
}
