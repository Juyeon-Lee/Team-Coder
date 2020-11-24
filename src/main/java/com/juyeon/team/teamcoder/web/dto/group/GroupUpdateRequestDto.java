package com.juyeon.team.teamcoder.web.dto.group;

import com.juyeon.team.teamcoder.domain.group.Age;
import com.juyeon.team.teamcoder.domain.group.GroupAim;
import com.juyeon.team.teamcoder.domain.group.Num;
import com.juyeon.team.teamcoder.domain.group.Period;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class GroupUpdateRequestDto {
    //ownerId 필요없음.
    private String name;
    private GroupAim aim;
    private Location location;
    private EduLevel education;
    private String description;
    private int maxNum;
    private int minAge;
    private int maxAge;
    private LocalDate start;
    private LocalDate end;
    private String file;
    private List<String> tags;

//    @Builder
//    public GroupUpdateRequestDto(String name,
//                                 String aim, String location, String education,
//                                 String description, int maxNum, int currentNum,
//                                 LocalDate start, LocalDate end, int minAge, int maxAge,
//                                 List<String> tags) {
//        this.name = name;
//        this.aim = GroupAim.valueOf(aim);
//        this.location = Location.valueOf(location);
//        this.education = EduLevel.valueOf(education);
//        this.description = description;
//        this.num = new Num(maxNum, currentNum);  // currentNum 반영
//        this.period = new Period(start, end);
//        this.age = new Age(minAge, maxAge);
//        this.tags = tags;
//    }
}
