package com.juyeon.team.teamcoder.web.dto.user;

import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private List<String> tags;
    private String email;
    private int birth;
    private Location location;
    private EduLevel education;

    @Builder
    public UserUpdateRequestDto(String name, List<String> tags, String email, String birth,
                                String location, String education) {
        this.name = name;
        this.tags = tags;
        this.email = email;
        this.birth = Integer.parseInt(birth);
        this.location = Location.valueOf(location);
        this.education = EduLevel.valueOf(education.toUpperCase());
    }
}
