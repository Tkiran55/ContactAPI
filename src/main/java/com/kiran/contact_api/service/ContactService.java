package com.kiran.contact_api.service;

import com.kiran.contact_api.exception.ContactNotFoundException;
import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts() {
        log.info("Fetching all contacts");
        List<Contact> contacts = repository.findAll();
        log.debug("Found {} contacts", contacts.size());
        return contacts;
    }

    public Contact getContactById(Long id) {
        log.info("Fetching contact with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Contact not found with id: {}", id);
                    return new ContactNotFoundException(id);
                });
    }

    public Contact createContact(Contact contact) {
        log.info("Creating new contact: {}", contact.getName());
        Contact saved = repository.save(contact);
        log.info("Contact created with id: {}", saved.getId());
        return saved;
    }

    public Contact updateContact(Long id, Contact contactDetails) {
        log.info("Updating contact with id: {}", id);
        Contact contact = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Contact not found for update, id: {}", id);
                    return new ContactNotFoundException(id);
                });

        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhone(contactDetails.getPhone());
        contact.setCategory(contactDetails.getCategory());

        Contact updated = repository.save(contact);
        log.info("Contact updated successfully: {}", id);
        return updated;
    }

    public void deleteContact(Long id) {
        log.info("Deleting contact with id: {}", id);
        if (!repository.existsById(id)) {
            log.error("Contact not found for deletion, id: {}", id);
            throw new ContactNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Contact deleted successfully: {}", id);
    }

    public List<Contact> getContactsByCategory(String category) {
        log.info("Fetching contacts by category: {}", category);
        return repository.findByCategory(category);
    }

    public List<Contact> searchContacts(String name) {
        log.info("Searching contacts with name: {}", name);
        return repository.findByNameContainingIgnoreCase(name);
    }
}