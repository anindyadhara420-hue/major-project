package com.technoindiamain.mejorproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "site_settings")
public class SiteSetting {

    @Id
    private Long id = 1L; // Only one record should ever exist

    @Column(length = 1000)
    private String aboutText;

    private String address;
    private String phone;
    private String email;
    private String copyrightText;

    // Constructors
    public SiteSetting() {
    }

    public SiteSetting(String aboutText, String address, String phone, String email, String copyrightText) {
        this.id = 1L;
        this.aboutText = aboutText;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.copyrightText = copyrightText;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }
}
