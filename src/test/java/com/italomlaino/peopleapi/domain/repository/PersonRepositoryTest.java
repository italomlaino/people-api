package com.italomlaino.peopleapi.domain.repository;

import com.italomlaino.peopleapi.TestConfig;
import com.italomlaino.peopleapi.test.PersonTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonTestHelper personTestHelper;

    @Test
    public void findByName_whenSimple_thenReturnPage() {
        var john = personTestHelper.createSample();
        entityManager.persist(john);
        entityManager.flush();

        var page = personRepository.findByNameContainingIgnoreCase("John", PageRequest.of(0, 10, Sort.unsorted()));

        assertThat(page.getTotalElements(), equalTo(1L));
        assertThat(page.getTotalPages(), equalTo(1));
        assertThat(page.getSize(), equalTo(10));
        assertThat(page.getSort(), equalTo(Sort.unsorted()));
        assertThat(page.getContent(), equalTo(List.of(john)));
    }

    @Test
    public void findByName_whenComposite_thenReturnPage() {
        var john = personTestHelper.createSample();
        john.setName("John Biden");
        entityManager.persist(john);
        entityManager.flush();

        var page = personRepository.findByNameContainingIgnoreCase("John", PageRequest.of(0, 10, Sort.unsorted()));

        assertThat(page.getTotalElements(), equalTo(1L));
        assertThat(page.getTotalPages(), equalTo(1));
        assertThat(page.getSize(), equalTo(10));
        assertThat(page.getSort(), equalTo(Sort.unsorted()));
        assertThat(page.getContent(), equalTo(List.of(john)));
    }
}
