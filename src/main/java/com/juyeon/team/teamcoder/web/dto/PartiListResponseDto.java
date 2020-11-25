package com.juyeon.team.teamcoder.web.dto;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.participate.PartiStatus;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import lombok.Getter;

import java.util.List;

@Getter
public class PartiListResponseDto {
    private Long id;
    private Long groupId;
    private String groupName;
    private Long userId;
    private String userName;
    private String status;
    private String applyComment;

    public PartiListResponseDto(Participate entity) {
        this.id = entity.getId();
        this.groupId = entity.getGroup().getId();
        this.groupName = entity.getGroup().getName();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getName();
        this.status = entity.getStatus().getTitle();
        this.applyComment = entity.getApplyComment();
    }
}
