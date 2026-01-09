package com.kiran.contact_api.service;

import com.kiran.contact_api.exception.ContactNotFoundException;
import com.kiran.contact_api.model.Contact;
import com.kiran.contact_api.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository repository;

    @InjectMocks
    private ContactService service;

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = new Contact("Kiran", "kiran@email.com", "1234567890", "FAMILY");
        contact.setId(1L);
    }

    @Test
    @DisplayName("Should return all contacts")
    void getAllContacts_ReturnsListOfContacts() {
        // Arrange
        List<Contact> contacts = Arrays.asList(contact);
        when(repository.findAll()).thenReturn(contacts);

        // Act
        List<Contact> result = service.getAllContacts();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Kiran", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return contact by ID")
    void getContactById_ExistingId_ReturnsContact() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(contact));

        // Act
        Contact result = service.getContactById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Kiran", result.getName());
    }

    @Test
    @DisplayName("Should throw exception when contact not found")
    void getContactById_NonExistingId_ThrowsException() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () -> {
            service.getContactById(999L);
        });
    }

    @Test
    @DisplayName("Should create contact successfully")
    void createContact_ValidContact_ReturnsCreatedContact() {
        // Arrange
        when(repository.save(any(Contact.class))).thenReturn(contact);

        // Act
        Contact result = service.createContact(contact);

        // Assert
        assertNotNull(result);
        assertEquals("Kiran", result.getName());
        verify(repository, times(1)).save(any(Contact.class));
    }

    @Test
    @DisplayName("Should update contact successfully")
    void updateContact_ExistingId_ReturnsUpdatedContact() {
        // Arrange
        Contact updatedDetails = new Contact("Kiran Updated", "updated@email.com", "9999999999", "FRIEND");
        when(repository.findById(1L)).thenReturn(Optional.of(contact));
        when(repository.save(any(Contact.class))).thenReturn(contact);

        // Act
        Contact result = service.updateContact(1L, updatedDetails);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(any(Contact.class));
    }

    @Test
    @DisplayName("Should delete contact successfully")
    void deleteContact_ExistingId_DeletesContact() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        service.deleteContact(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing contact")
    void deleteContact_NonExistingId_ThrowsException() {
        // Arrange
        when(repository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ContactNotFoundException.class, () -> {
            service.deleteContact(999L);
        });
    }
}