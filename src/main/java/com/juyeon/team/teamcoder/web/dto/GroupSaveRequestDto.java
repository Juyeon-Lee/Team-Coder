package com.juyeon.team.teamcoder.web.dto;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.tagGroup.TagGroup;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class GroupSaveRequestDto {

    private String name;
    private Long ownerId;
    private GroupAim aim;
    private Location location;
    private EduLevel education;
    private String description;
    private int maxNum;
    private int minAge;
    private int maxAge;
    private LocalDate start;
    private LocalDate end;
    private List<String> tags;

    @Builder
    public GroupSaveRequestDto(String name, Long ownerId, String aim,
                               String location, String education, String description,
                               int maxNum, int minAge, int maxAge,
                               LocalDate start, LocalDate end, List<String> tags) {
        this.name = name;
        this.ownerId = ownerId;
        this.aim = GroupAim.valueOf(aim);
        this.location = Location.valueOf(location);
        this.education = EduLevel.valueOf(education);
        this.description = description;
        this.maxNum = maxNum;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.start = start;
        this.end = end;
        this.tags = tags;
    }

    public Group toEntity(User user){
        // ownerId 로 user 찾기
        return Group.builder()   // Group domain에서 자동으로 처음에 status recruit으로 설정
                .name(name)
                .manager(user)
                .aim(aim)
                .location(location)
                .education(education)
                .description(description)
                .memberNum(new Num(maxNum, 0))
                .workPeriod(new Period(start, end))
                .ageLimit(new Age(minAge, maxAge))
                .build();
    }
}
