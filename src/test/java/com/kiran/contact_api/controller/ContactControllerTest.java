package com.kiran.contact_api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("GET /api/contacts - Should return empty list")
    void getAllContacts_Empty_ReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("POST /api/contacts - Should create contact")
    void createContact_ValidData_ReturnsCreated() throws Exception {
        Contact contact = new Contact("Kiran", "kiran@email.com", "1234567890", "FAMILY");

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Kiran"))
                .andExpect(jsonPath("$.email").value("kiran@email.com"));
    }

    @Test
    @DisplayName("POST /api/contacts - Should return 400 for invalid data")
    void createContact_InvalidData_ReturnsBadRequest() throws Exception {
        Contact contact = new Contact("", "invalid-email", "abc", "FAMILY");

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contact)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/contacts/{id} - Should return 404 for non-existing")
    void getContactById_NonExisting_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/contacts/999"))
                .andExpect(status().isNotFound());
    }
}