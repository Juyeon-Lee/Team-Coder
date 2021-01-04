package com.juyeon.team.teamcoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter  // 모든 필드의 getter를 설정해준다.
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자를 생성해준다.
public class ApplyRequestDto {
    private final Long groupId; // final 로 선언해야 자동생성된다.
    private final String comment;
}
