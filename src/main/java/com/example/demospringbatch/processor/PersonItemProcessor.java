package com.example.demospringbatch.processor;

import com.example.demospringbatch.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;


public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person item) throws Exception {
        String firstName = item.getFirstName().toUpperCase();
        String lastName = item.getLastName().toUpperCase();
        String phone = item.getPhone();

        Person person = new Person(firstName, lastName, phone);
        LOG.info("Convirtiendo ("+ item +") a ("+ person + ")");
        return person;
    }



}
