package com.italomlaino.peopleapi.service;

import com.italomlaino.peopleapi.domain.entity.MaritalStatus;
import com.italomlaino.peopleapi.domain.entity.Person;
import com.italomlaino.peopleapi.domain.exception.PersonNotFoundException;
import com.italomlaino.peopleapi.domain.exception.SpouseNotAllowedForSpecifiedMaritalStatusException;
import com.italomlaino.peopleapi.domain.exception.SpouseRequiredForSpecifiedMaritalStatusException;
import com.italomlaino.peopleapi.domain.repository.PersonRepository;
import com.italomlaino.peopleapi.domain.view.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.repository = personRepository;
    }

    @Override
    public PageView<Person> findByName(String name, Pageable pageable) {
        return PageView.of(repository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Override
    public Person findById(long id) {
        return repository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    public Person create(Person person) {
        validate(person);

        return repository.save(person);
    }

    @Override
    public Person update(long id, Person person) {
        validate(person);

        var oldPerson = repository.findById(id).orElseThrow(PersonNotFoundException::new);
        oldPerson.setName(person.getName());
        oldPerson.setBirthday(person.getBirthday());
        oldPerson.setMaritalStatus(person.getMaritalStatus());
        oldPerson.setSpouseName(person.getSpouseName());
        oldPerson.setSpouseBirthday(person.getSpouseBirthday());

        return repository.save(oldPerson);
    }

    @Override
    public void delete(long id) {
        if (!repository.existsById(id))
            throw new PersonNotFoundException();

        repository.deleteById(id);
    }

    private void validate(Person person) {
        var spouseRequired = person.getMaritalStatus() == MaritalStatus.MARRIED;
        var invalidSpouse = (person.getSpouseName() == null || person.getSpouseName().isBlank()) &&
                person.getSpouseBirthday() == null;

        if (invalidSpouse && spouseRequired) throw new SpouseRequiredForSpecifiedMaritalStatusException();

        if (!invalidSpouse && !spouseRequired) throw new SpouseNotAllowedForSpecifiedMaritalStatusException();
    }
}
