package com.example.demo.service;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.DetailException;
import com.example.demo.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    void testFindByName() {
        assertNotNull(personService.findByName("rod"));
    }

    @Test
    void testFindByNameNullResult() {
        assertNull(personService.findByName("nyor"));
    }

    @Test
    void testCreatePerson() {
        Person person = Person.builder()
                              .address("add")
                              .name("rod")
                              .createdDate(LocalDate.now())
                              .build();
        personService.createPerson(person);

        assertEquals(2, person.getId());
    }

}
