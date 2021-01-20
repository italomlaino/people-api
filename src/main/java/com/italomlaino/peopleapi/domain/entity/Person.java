package com.italomlaino.peopleapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @Basic(optional = false)
    private long id;

    @Column(nullable = false)
    @Basic(optional = false)
    @NotBlank
    @Size(min = 5)
    @NotNull
    @Pattern(
            regexp = "^[a-zA-Z\\s]*$",
            message = "must only contains letters or space")
    private String name;

    @Column(nullable = false)
    @Basic(optional = false)
    @Past
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

    @Column(nullable = false)
    @Basic(optional = false)
    @NotNull
    private MaritalStatus maritalStatus;

    private String spouseName;

    @Past
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date spouseBirthday;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthDay) {
        this.birthday = birthDay;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public Date getSpouseBirthday() {
        return spouseBirthday;
    }

    public void setSpouseBirthday(Date spouseBirthday) {
        this.spouseBirthday = spouseBirthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var person = (Person) o;
        return id == person.id &&
                Objects.equals(name, person.name) &&
                Objects.equals(birthday, person.birthday) &&
                maritalStatus == person.maritalStatus &&
                Objects.equals(spouseName, person.spouseName) &&
                Objects.equals(spouseBirthday, person.spouseBirthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, maritalStatus, spouseName, spouseBirthday);
    }
}
