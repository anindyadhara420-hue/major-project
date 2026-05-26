package com.technoindiamain.mejorproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoindiamain.mejorproject.entity.HeroSlider;

@Repository
public interface HeroSliderRepository extends JpaRepository<HeroSlider, Long> {
    List<HeroSlider> findByIsActiveTrueOrderByDisplayOrderAsc();
    List<HeroSlider> findAllByOrderByDisplayOrderAsc();
}
