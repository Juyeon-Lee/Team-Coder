package com.juyeon.team.teamcoder.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto implements Serializable {
    private String aim;
    private String period;
    private String loc;
    private String tags;
    private int age;


}
