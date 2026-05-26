package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoindiamain.mejorproject.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
