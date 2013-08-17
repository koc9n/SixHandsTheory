package com.gmail.mironchik.kos.dto;

/**
 * Date: 17.08.13
 * Time: 16:21
 */
public class Friend {
    private String profileId;
    private boolean checked;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
