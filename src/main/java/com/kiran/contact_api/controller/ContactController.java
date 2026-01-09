package com.kiran.contact_api.controller;

import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Contact", description = "Contact management APIs")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all contacts", description = "Retrieves a list of all contacts")
    public List<Contact> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get contact by ID", description = "Retrieves a specific contact by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    public Contact getContactById(
            @Parameter(description = "Contact ID") @PathVariable Long id) {
        return service.getContactById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create contact", description = "Creates a new contact")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contact created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return service.createContact(contact);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update contact", description = "Updates an existing contact")
    public Contact updateContact(
            @Parameter(description = "Contact ID") @PathVariable Long id,
            @Valid @RequestBody Contact contact) {
        return service.updateContact(id, contact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete contact", description = "Deletes a contact by ID")
    public void deleteContact(
            @Parameter(description = "Contact ID") @PathVariable Long id) {
        service.deleteContact(id);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get contacts by category", description = "Retrieves contacts filtered by category")
    public List<Contact> getByCategory(
            @Parameter(description = "Category name") @PathVariable String category) {
        return service.getContactsByCategory(category);
    }

    @GetMapping("/search")
    @Operation(summary = "Search contacts", description = "Searches contacts by name")
    public List<Contact> searchContacts(
            @Parameter(description = "Search query") @RequestParam String name) {
        return service.searchContacts(name);
    }
}