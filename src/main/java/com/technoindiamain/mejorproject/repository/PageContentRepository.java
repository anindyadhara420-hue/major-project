package com.technoindiamain.mejorproject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoindiamain.mejorproject.entity.PageContent;

@Repository
public interface PageContentRepository extends JpaRepository<PageContent, Long> {
    Optional<PageContent> findBySlug(String slug);

    List<PageContent> findByCategory(PageContent.Category category);

    Optional<PageContent> findByCategoryAndSlug(PageContent.Category category, String slug);
}
