package com.juyeon.team.teamcoder.web.dto.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.service.S3Service;
import lombok.Getter;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.List;

@Getter
public class UserResponseDto {

    private Long id;
    private String name;
    private String picture;
    private int birth;
    private String email;
    private Location location;
    private EduLevel education;
    private List<String> tags;

    public UserResponseDto(User entity, List<String> tags) {
        this.id = entity.getId();
        this.name = entity.getName();
        String tmpFile = entity.getPicture();
        if(tmpFile == null){
            this.picture = "";
        }else{
            this.picture = "https://"+ S3Service.CLOUD_FRONT_DOMAIN_NAME +"/"+tmpFile;
        }
        this.birth = entity.getBirth();
        this.email = entity.getEmail();
        this.location = entity.getLocation();
        this.education = entity.getEducation();
        this.tags = tags;
    }

}
