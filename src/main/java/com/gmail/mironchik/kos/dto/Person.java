package com.gmail.mironchik.kos.dto;

import java.util.Set;

/**
 * Created by kmironchyk on 8/31/2015.
 */
public class Person {
    private Long id;
    private Set<Long> friends;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id.equals(person.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
