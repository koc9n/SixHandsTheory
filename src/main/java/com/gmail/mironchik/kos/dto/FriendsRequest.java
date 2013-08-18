package com.gmail.mironchik.kos.dto;

/**
 * Created with IntelliJ IDEA.
 * User: roman
 * Date: 18.08.13
 * Time: 4:24
 * To change this template use File | Settings | File Templates.
 */
public class FriendsRequest {
    private String ids;
    private Integer step;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
