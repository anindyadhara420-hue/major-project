package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoindiamain.mejorproject.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
