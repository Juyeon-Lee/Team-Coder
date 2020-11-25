package com.juyeon.team.teamcoder.domain.participate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartiStatus {
    APPLY("신청함"),
    JOIN("참여중"),
    REJECTED("거절됨");

    private final String title;
}
