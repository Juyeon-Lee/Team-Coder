package com.juyeon.team.teamcoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyRequestDto {
    private Long groupId;
    private String comment;
}
