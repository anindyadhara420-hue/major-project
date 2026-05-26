package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.technoindiamain.mejorproject.entity.Admission;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {
}
