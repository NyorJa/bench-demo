package com.example.demo.service;

import com.example.demo.exceptions.DetailException;
import com.example.demo.model.Person;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface PersonService {

    Person findByName(String name) throws EntityNotFoundException;

    Optional<Person> findByAddress(String address);

    void createPerson(Person build);

}
