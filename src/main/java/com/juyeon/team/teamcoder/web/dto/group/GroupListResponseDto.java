package com.juyeon.team.teamcoder.web.dto.group;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GroupListResponseDto {
    private Long id;
    private String name;
    private User manager;
    private String aim;
    private String location;
    private String status;
    private Num num;
    private Period period;
    private Age age;
    private List<String> tags;

    public GroupListResponseDto(Group entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.manager = entity.getManager();
        this.aim = entity.getAim().getTitle();
        this.location = entity.getLocation().getTitle();
        this.num = entity.getMemberNum();  // currentNum 반영
        this.period = entity.getWorkPeriod();
        this.age = entity.getAgeLimit();
        this.status = entity.getStatus().getTitle();
    }

    public void setTags(List<String> tags){
        this.tags = tags;
    }
}
