package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoindiamain.mejorproject.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
