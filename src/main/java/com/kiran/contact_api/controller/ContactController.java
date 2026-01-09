package com.kiran.contact_api.controller;

import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return service.getContactById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return service.createContact(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id,
                                 @Valid @RequestBody Contact contact) {
        return service.updateContact(id, contact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable Long id) {
        service.deleteContact(id);
    }

    @GetMapping("/category/{category}")
    public List<Contact> getByCategory(@PathVariable String category) {
        return service.getContactsByCategory(category);
    }

    @GetMapping("/search")
    public List<Contact> searchContacts(@RequestParam String name) {
        return service.searchContacts(name);
    }
}