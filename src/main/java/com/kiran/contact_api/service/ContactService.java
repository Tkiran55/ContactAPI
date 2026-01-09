package com.kiran.contact_api.service;

import com.kiran.contact_api.exception.ContactNotFoundException;
import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Contact getContactById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    public Contact createContact(Contact contact) {
        return repository.save(contact);
    }

    public Contact updateContact(Long id, Contact contactDetails) {
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));

        contact.setName(contactDetails.getName());
        contact.setEmail(contactDetails.getEmail());
        contact.setPhone(contactDetails.getPhone());
        contact.setCategory(contactDetails.getCategory());

        return repository.save(contact);
    }

    public void deleteContact(Long id) {
        if (!repository.existsById(id)) {
            throw new ContactNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public List<Contact> getContactsByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Contact> searchContacts(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}