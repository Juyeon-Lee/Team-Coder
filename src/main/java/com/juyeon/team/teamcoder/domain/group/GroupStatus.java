package com.juyeon.team.teamcoder.domain.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupStatus {
    RECRUIT("모집중"),
    FULL("모집 완료"),
    TERMINATED("활동 종료");

    private final String title;

    public String getTitle(){
        return title;
    }

}
