package com.gmail.mironchik.kos.dto;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 17.08.13
 * Time: 16:21
 */
public class HandShake implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Integer step;
    @JsonIgnore
    private List<Integer> owners;

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public void setOwners(List<Integer> owners) {
        this.owners = owners;
    }

}
