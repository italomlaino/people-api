package com.italomlaino.peopleapi.web.person;

import com.italomlaino.peopleapi.domain.entity.Person;
import com.italomlaino.peopleapi.domain.view.PageView;
import com.italomlaino.peopleapi.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService personService) {
        this.service = personService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<Person> findByName(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return service.findByName(name, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Person update(@PathVariable long id, @RequestBody Person person) {
        return service.update(id, person);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
