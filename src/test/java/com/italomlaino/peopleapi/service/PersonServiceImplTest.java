package com.italomlaino.peopleapi.service;

import com.italomlaino.peopleapi.TestConfig;
import com.italomlaino.peopleapi.domain.entity.MaritalStatus;
import com.italomlaino.peopleapi.domain.exception.PersonNotFoundException;
import com.italomlaino.peopleapi.domain.exception.SpouseNotAllowedForSpecifiedMaritalStatusException;
import com.italomlaino.peopleapi.domain.exception.SpouseRequiredForSpecifiedMaritalStatusException;
import com.italomlaino.peopleapi.domain.repository.PersonRepository;
import com.italomlaino.peopleapi.test.PersonTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
public class PersonServiceImplTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonTestHelper personTestHelper;

    @Test
    public void findByName_whenNonEmpty_thenReturnPage() {
        var john = personTestHelper.createSample();
        given(personRepository.findByNameContainingIgnoreCase(any(), any())).willReturn(new PageImpl<>(List.of(john)));

        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        var page = personService.findByName("existentName", pageable);
        assertThat(page.getTotalElements(), equalTo(1L));
        assertThat(page.getContent(), equalTo(List.of(john)));
    }

    @Test
    public void findByName_whenEmpty_thenReturnPage() {
        given(personRepository.findByNameContainingIgnoreCase(any(), any())).willReturn(new PageImpl<>(List.of()));

        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        var page = personService.findByName("inexistentName", pageable);
        assertThat(page.getTotalElements(), equalTo(0L));
    }

    @Test
    public void findById_whenFound_thenReturnPerson() {
        var john = personTestHelper.createSample();
        given(personRepository.findById(any())).willReturn(Optional.of(john));

        var result = personService.findById(1L);
        assertThat(result, is(john));
    }

    @Test
    public void findById_whenNotFound_thenThrowNotFoundException() {
        given(personRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findById(1L));
    }

    @Test
    public void create_whenValid_thenReturnPerson() {
        var john = personTestHelper.createSample();
        given(personRepository.save(any())).willReturn(john);

        var result = personService.create(john);
        assertThat(result, is(john));
    }

    @Test
    public void create_whenMarriedAndInvalidSpouse_thenThrowException() {
        var john = personTestHelper.createSample();
        john.setSpouseBirthday(null);
        given(personRepository.save(any())).willReturn(john);

        assertThrows(SpouseRequiredForSpecifiedMaritalStatusException.class, () -> personService.create(john));
    }

    @Test
    public void create_whenSingleAndNonNullSpouse_thenThrowException() {
        var john = personTestHelper.createSample();
        john.setSpouseName(null);
        john.setMaritalStatus(MaritalStatus.SINGLE);
        given(personRepository.save(any())).willReturn(john);

        assertThrows(SpouseNotAllowedForSpecifiedMaritalStatusException.class, () -> personService.create(john));
    }

    @Test
    public void update_whenValid_thenReturnPerson() {
        var john = personTestHelper.createSample();
        given(personRepository.findById(any())).willReturn(Optional.of(john));

        var johnUpdated = personTestHelper.createSample();
        johnUpdated.setName("New John");
        given(personRepository.save(any())).willReturn(johnUpdated);

        var result = personService.update(1L, johnUpdated);
        assertThat(result, is(johnUpdated));
    }

    @Test
    public void update_whenNotFound_thenThrowNotFoundException() {
        var john = personTestHelper.createSample();
        given(personRepository.existsById(any())).willReturn(false);

        assertThrows(PersonNotFoundException.class, () -> personService.update(1L, john));
    }

    @Test
    public void update_whenMarriedAndInvalidSpouse_thenThrowException() {
        var john = personTestHelper.createSample();
        john.setSpouseName(null);
        john.setSpouseBirthday(new Date());
        given(personRepository.existsById(any())).willReturn(false);

        assertThrows(SpouseRequiredForSpecifiedMaritalStatusException.class, () -> personService.update(1L, john));
    }

    @Test
    public void update_whenSingleAndNonNullSpouse_thenThrowException() {
        var john = personTestHelper.createSample();
        john.setSpouseBirthday(null);
        john.setMaritalStatus(MaritalStatus.SINGLE);
        given(personRepository.existsById(any())).willReturn(false);

        assertThrows(SpouseNotAllowedForSpecifiedMaritalStatusException.class, () -> personService.update(1L, john));
    }

    @Test
    public void delete_whenFound_thenReturnVoid() {
        given(personRepository.existsById(any())).willReturn(true);

        personService.delete(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    public void delete_whenNotFound_thenThrowNotFoundException() {
        given(personRepository.existsById(any())).willReturn(false);

        assertThrows(PersonNotFoundException.class, () -> personService.delete(1L));
    }
}
