package com.app.mega.config.util.sns;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SnsSenderDto {

    private String mobileNo;
    private String smsTxt;

    @Builder
    public SnsSenderDto(String mobileNo, String smsTxt) {
        this.mobileNo = mobileNo; this.smsTxt = smsTxt;
    }
}