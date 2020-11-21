package com.juyeon.team.teamcoder.web.dto;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
public class GroupResponseDto {

    private Long id;
    private String name;
    private User manager;
    private GroupAim aim;
    private Location location;
    private EduLevel education;
    private String description;
    private String file;
    private GroupStatus status;
    private Num num;
    private Period period;
    private Age age;
    private List<String> tags;

    public GroupResponseDto(Group entity, List<String> tags) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.manager = entity.getManager();
        this.aim = entity.getAim();
        this.location = entity.getLocation();
        this.education = entity.getEducation();
        this.description = entity.getDescription();
        this.file = entity.getFilePath();
        this.num = entity.getMemberNum();  // currentNum 반영
        this.period = entity.getWorkPeriod();
        this.age = entity.getAgeLimit();
        this.status = entity.getStatus();
        this.tags = tags;
    }
}
