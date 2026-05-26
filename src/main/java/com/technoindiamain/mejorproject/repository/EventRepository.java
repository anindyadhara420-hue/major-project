package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoindiamain.mejorproject.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
