package com.kiran.contact_api.repository;

import com.kiran.contact_api.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // Spring Data JPA creates implementation automatically!
    List<Contact> findByCategory(String category);

    List<Contact> findByNameContainingIgnoreCase(String name);
}