package com.technoindiamain.mejorproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.technoindiamain.mejorproject.entity.ContactMessage;
import com.technoindiamain.mejorproject.entity.Course;
import com.technoindiamain.mejorproject.entity.Degree;
import com.technoindiamain.mejorproject.entity.Event;
import com.technoindiamain.mejorproject.entity.GalleryImage;
import com.technoindiamain.mejorproject.entity.HeroSlider;
import com.technoindiamain.mejorproject.entity.Notice;
import com.technoindiamain.mejorproject.entity.PageContent;
import com.technoindiamain.mejorproject.entity.SiteSetting;
import com.technoindiamain.mejorproject.repository.ContactMessageRepository;
import com.technoindiamain.mejorproject.repository.CourseRepository;
import com.technoindiamain.mejorproject.repository.DegreeRepository;
import com.technoindiamain.mejorproject.repository.EventRepository;
import com.technoindiamain.mejorproject.repository.GalleryImageRepository;
import com.technoindiamain.mejorproject.repository.HeroSliderRepository;
import com.technoindiamain.mejorproject.repository.NoticeRepository;
import com.technoindiamain.mejorproject.repository.PageContentRepository;
import com.technoindiamain.mejorproject.repository.SiteSettingRepository;
import com.technoindiamain.mejorproject.entity.Admission;
import com.technoindiamain.mejorproject.repository.AdmissionRepository;

@Service
public class CollegeService {

    private final DegreeRepository degreeRepository;
    private final CourseRepository courseRepository;
    private final PageContentRepository pageContentRepository;
    private final NoticeRepository noticeRepository;
    private final EventRepository eventRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final ContactMessageRepository contactMessageRepository;
    private final SiteSettingRepository siteSettingRepository;
    private final HeroSliderRepository heroSliderRepository;
    private final AdmissionRepository admissionRepository;

    public CollegeService(DegreeRepository degreeRepository, CourseRepository courseRepository,
            PageContentRepository pageContentRepository, NoticeRepository noticeRepository,
            EventRepository eventRepository, GalleryImageRepository galleryImageRepository,
            ContactMessageRepository contactMessageRepository, SiteSettingRepository siteSettingRepository,
            HeroSliderRepository heroSliderRepository, AdmissionRepository admissionRepository) {
        this.degreeRepository = degreeRepository;
        this.courseRepository = courseRepository;
        this.pageContentRepository = pageContentRepository;
        this.noticeRepository = noticeRepository;
        this.eventRepository = eventRepository;
        this.galleryImageRepository = galleryImageRepository;
        this.contactMessageRepository = contactMessageRepository;
        this.siteSettingRepository = siteSettingRepository;
        this.heroSliderRepository = heroSliderRepository;
        this.admissionRepository = admissionRepository;
    }
    // Degree Operations
    public List<Degree> getAllDegrees() {
        return degreeRepository.findAll();
    }

    public Optional<Degree> getDegreeById(Long id) {
        return degreeRepository.findById(id);
    }

    public void saveDegree(Degree degree) {
        degreeRepository.save(degree);
    }

    public void deleteDegree(Long id) {
        degreeRepository.deleteById(id);
    }

    // Course Operations
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // PageContent Operations
    public List<PageContent> getAllPageContents() {
        return pageContentRepository.findAll();
    }

    public List<PageContent> getPageContentsByCategory(PageContent.Category category) {
        return pageContentRepository.findByCategory(category);
    }

    public Optional<PageContent> getPageContentById(Long id) {
        return pageContentRepository.findById(id);
    }

    public Optional<PageContent> getPageContentBySlug(String slug) {
        return pageContentRepository.findBySlug(slug);
    }

    public Optional<PageContent> getPageContentByCategoryAndSlug(PageContent.Category category, String slug) {
        return pageContentRepository.findByCategoryAndSlug(category, slug);
    }

    public void savePageContent(PageContent pageContent) {
        pageContentRepository.save(pageContent);
    }

    public void deletePageContent(Long id) {
        pageContentRepository.deleteById(id);
    }

    // Notice Operations
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "publishDate"));
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // Event Operations
    public List<Event> getAllEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventDate"));
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // GalleryImage Operations
    public List<GalleryImage> getAllGalleryImages() {
        return galleryImageRepository.findAll();
    }

    public Optional<GalleryImage> getGalleryImageById(Long id) {
        return galleryImageRepository.findById(id);
    }

    public void saveGalleryImage(GalleryImage galleryImage) {
        galleryImageRepository.save(galleryImage);
    }

    public void deleteGalleryImage(Long id) {
        galleryImageRepository.deleteById(id);
    }

    // ContactMessage Operations
    public List<ContactMessage> getAllContactMessages() {
        return contactMessageRepository.findAll(Sort.by(Sort.Direction.DESC, "submissionDate"));
    }

    public Optional<ContactMessage> getContactMessageById(Long id) {
        return contactMessageRepository.findById(id);
    }

    public void saveContactMessage(ContactMessage contactMessage) {
        contactMessageRepository.save(contactMessage);
    }

    public void deleteContactMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }

    // SiteSetting Operations
    public SiteSetting getSiteSetting() {
        return siteSettingRepository.findById(1L).orElseGet(() -> {
            SiteSetting defaultSettings = new SiteSetting(
                "Techno Main is a premier core engineering institute.",
                "Salt Lake City, Sector V, Kolkata, West Bengal", 
                "+91 (123) 456-7890", 
                "info@technomain.edu", 
                "© 2024 Techno Main Salt Lake. All Rights Reserved."
            );
            return siteSettingRepository.save(defaultSettings);
        });
    }

    public void saveSiteSetting(SiteSetting siteSetting) {
        siteSetting.setId(1L); // Ensure ID is always 1
        siteSettingRepository.save(siteSetting);
    }

    // HeroSlider Operations
    public List<HeroSlider> getAllHeroSliders() {
        return heroSliderRepository.findAllByOrderByDisplayOrderAsc();
    }

    public List<HeroSlider> getActiveHeroSliders() {
        return heroSliderRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public Optional<HeroSlider> getHeroSliderById(Long id) {
        return heroSliderRepository.findById(id);
    }

    public void saveHeroSlider(HeroSlider heroSlider) {
        heroSliderRepository.save(heroSlider);
    }

    public void deleteHeroSlider(Long id) {
        heroSliderRepository.deleteById(id);
    }

    // Admission Operations
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll(Sort.by(Sort.Direction.DESC, "submissionDate"));
    }

    public void saveAdmission(Admission admission) {
        admissionRepository.save(admission);
    }
}
