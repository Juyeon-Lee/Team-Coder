package com.juyeon.team.teamcoder.web.dto.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
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
        this.picture = entity.getPicture();
        this.birth = entity.getBirth();
        this.email = entity.getEmail();
        this.location = entity.getLocation();
        this.education = entity.getEducation();
        this.tags = tags;
    }

    /*
    private HashMap<String, Object> userMap = new HashMap<String, Object>();

    public UserResponseDto(User entity, List<String> tags) {
        this.userMap.put("id", entity.getId());
        this.userMap.put("name", entity.getName());
        this.userMap.put("picture", entity.getPicture());
        this.userMap.put("birth", entity.getBirth());
        this.userMap.put("email", entity.getEmail());
        this.userMap.put("location", entity.getLocation());
        this.userMap.put("tags", tags);
    }*/


    public String toJson(){
        JSONObject json = new JSONObject();
        json.appendField("id", this.id);
        json.appendField("name", this.name);
        json.appendField("picture", this.picture);
        json.appendField("birth", this.birth);
        json.appendField("email", this.email);
        json.appendField("location", this.location);
        json.appendField("education", this.education);
        json.appendField("tags", this.tags);
        return json.toJSONString();
    }
}
