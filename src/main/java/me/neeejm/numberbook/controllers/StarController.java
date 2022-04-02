package me.neeejm.numberbook.controllers;

import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.neeejm.numberbook.model.Contact;
import me.neeejm.numberbook.repository.ContactRepository;

@RequestMapping("/contacts")
@RestController
public class StarController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> get() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact get(@PathVariable(name = "id") int idContact) {
        return contactRepository.findById(idContact).get();
    }

    @PostMapping
    public void add(@RequestBody Contact contact) {
        for (Contact c : contactRepository.findAll()) {
            if (c.getName().equals(contact.getName()) && c.getPhoneNumber().equals(contact.getPhoneNumber())) {
                return;
            }
        }
        contactRepository.save(contact);
    }

    @GetMapping("/find")
    public ResponseEntity<Contact> find(@PathParam("value") String value) {
        for (Contact c : contactRepository.findAll()) {
            if (c.getName().toLowerCase().equals(value.toLowerCase())
                    || c.getPhoneNumber().equals(value)) {
                return new ResponseEntity<Contact>(c, HttpStatus.OK);
            }
        }
        return null;
    }

    @PutMapping
    public void update(@RequestBody Contact contact) {
        Contact mContact = contactRepository.getById(contact.getId());

        if (mContact != null) {
            mContact.setPhoneNumber(contact.getPhoneNumber());
            mContact.setName(contact.getName());
            contactRepository.save(mContact);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") int idContact) {
        contactRepository.findById(idContact);
    }
}
