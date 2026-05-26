package com.technoindiamain.mejorproject.service;

import com.technoindiamain.mejorproject.entity.AdminUser;
import com.technoindiamain.mejorproject.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initDefaultAdmin() {
        if (adminUserRepository.count() == 0) {
            AdminUser defaultAdmin = new AdminUser();
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword(passwordEncoder.encode("admin"));
            defaultAdmin.setEnabled(true);
            adminUserRepository.save(defaultAdmin);
            System.out.println("Created default admin user: admin / admin");
        }
    }

    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }

    public Optional<AdminUser> getAdminUserById(Long id) {
        return adminUserRepository.findById(id);
    }

    public Optional<AdminUser> getAdminUserByUsername(String username) {
        return adminUserRepository.findByUsername(username);
    }

    public void saveAdminUser(AdminUser adminUser) {
        // If password is not empty, encode it. 
        // Note: For edit, if password is empty, we keep the old one (handled in controller)
        if (adminUser.getPassword() != null && !adminUser.getPassword().isEmpty()) {
            adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        }
        adminUserRepository.save(adminUser);
    }

    public void deleteAdminUser(Long id) {
        adminUserRepository.deleteById(id);
    }
}
