package com.italomlaino.peopleapi.domain.entity;

import com.italomlaino.peopleapi.TestConfig;
import com.italomlaino.peopleapi.test.PersonTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
public class PersonTest {

    @Autowired
    private PersonTestHelper personTestHelper;

    @Test
    public void equals_whenEquals_returnTrue() {
        var person1 = personTestHelper.createSample();
        var person2 = personTestHelper.createSample();

        assertThat(person1.equals(person2), is(true));
    }

    @Test
    public void equals_whenNotEquals_returnFalse() {
        var person1 = personTestHelper.createSample();
        person1.setName("Eric");
        var person2 = personTestHelper.createSample();

        assertThat(person1.equals(person2), is(false));
    }

    @Test
    public void hashCode_whenEquals_returnSameHashCode() {
        var person1 = personTestHelper.createSample();
        var person2 = personTestHelper.createSample();

        assertThat(person1.hashCode(), is(person2.hashCode()));
    }

    @Test
    public void hashCode_whenNotEquals_returnDifferentHashCode() {
        var person1 = personTestHelper.createSample();
        person1.setName("Eric");
        var person2 = personTestHelper.createSample();

        assertThat(person1.hashCode(), is(not(person2.hashCode())));
    }
}
