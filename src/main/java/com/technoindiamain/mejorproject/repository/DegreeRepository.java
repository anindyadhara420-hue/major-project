package com.technoindiamain.mejorproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoindiamain.mejorproject.entity.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

}
