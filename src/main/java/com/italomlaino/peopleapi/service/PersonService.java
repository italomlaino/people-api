package com.italomlaino.peopleapi.service;

import com.italomlaino.peopleapi.domain.entity.Person;
import com.italomlaino.peopleapi.domain.view.PageView;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    PageView<Person> findByName(String name, Pageable pageable);

    Person findById(long id);

    Person create(Person person);

    Person update(long id, Person person);

    void delete(long id);
}
