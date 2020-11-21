package com.juyeon.team.teamcoder.domain.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  GroupAim {
    CONTEST("공모전"),
    STUDY("스터디"),
    PROJECT("프로젝트 개발"),
    OTHER("기타");

    private final String title;

}
