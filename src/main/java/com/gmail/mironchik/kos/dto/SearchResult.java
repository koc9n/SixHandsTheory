package com.gmail.mironchik.kos.dto;

import java.util.Set;

/**
 * Created by kmironchyk on 8/31/2015.
 */
public class SearchResult {
    private Person firstPerson;
    private Person secondPerson;
    private Set<HandShake> handShakes;

    public Person getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(Person firstPerson) {
        this.firstPerson = firstPerson;
    }

    public Person getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(Person secondPerson) {
        this.secondPerson = secondPerson;
    }

    public Set<HandShake> getHandShakes() {
        return handShakes;
    }

    public void setHandShakes(Set<HandShake> handShakes) {
        this.handShakes = handShakes;
    }
}
