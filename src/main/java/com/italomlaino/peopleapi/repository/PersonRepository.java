package com.italomlaino.peopleapi.repository;

import com.italomlaino.peopleapi.domain.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
