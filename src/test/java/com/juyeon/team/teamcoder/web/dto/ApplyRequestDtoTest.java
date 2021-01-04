package com.juyeon.team.teamcoder.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplyRequestDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        Long id = 3L;
        String comment = "i want to participate";

        //when
        ApplyRequestDto dto = new ApplyRequestDto(id, comment);

        //then
        assertThat(dto.getGroupId()).isEqualTo(id);
        assertThat(dto.getComment()).isEqualTo(comment);
    }
}
