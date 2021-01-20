package com.italomlaino.peopleapi.test;

import com.italomlaino.peopleapi.domain.entity.MaritalStatus;
import com.italomlaino.peopleapi.domain.entity.Person;
import org.springframework.boot.test.context.TestComponent;

import java.util.Calendar;

@TestComponent
public class PersonTestHelper {

    public Person createSample() {
        var calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        var person = new Person();
        person.setName("John Rick");
        person.setMaritalStatus(MaritalStatus.MARRIED);
        person.setBirthday(calendar.getTime());
        person.setSpouseName("Marie");
        person.setSpouseBirthday(calendar.getTime());
        return person;
    }
}
