package com.juyeon.team.teamcoder.web.dto.group;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.service.S3Service;
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
    private String fileName;
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
        String tmpFile = entity.getFile();
        if(tmpFile== null){
            this.file = "";
            this.fileName = "";
        }else{
            this.file = "https://"+ S3Service.CLOUD_FRONT_DOMAIN_NAME +"/"+ tmpFile;
            this.fileName = tmpFile.substring(tmpFile.lastIndexOf("/") + 1); //groups/ 자르기 위해
        }
        this.num = entity.getMemberNum();  // currentNum 반영
        this.period = entity.getWorkPeriod();
        this.age = entity.getAgeLimit();
        this.status = entity.getStatus();
        this.tags = tags;
    }
}
