package com.example.demo.repository;

import com.example.demo.DemoApplication;
import com.example.demo.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testFindByName() {
        Optional<Person> person = personRepository.findByName("rod");
        assertTrue(person.isPresent());
    }

    @Test
    void testFindByNameNotExisting() {
        Optional<Person> person = personRepository.findByName("nyor");
        assertFalse(person.isPresent());
    }
}
