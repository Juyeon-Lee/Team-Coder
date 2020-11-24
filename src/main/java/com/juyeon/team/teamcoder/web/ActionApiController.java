package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.service.participate.ParticipateService;
import com.juyeon.team.teamcoder.web.dto.ApplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ActionApiController {

    @Autowired
    private final ParticipateService participateService;

    // 참여- 신청, 취소, 승인, 거절
    @PostMapping("/api/v1/participate")
    public Long apply(@RequestBody ApplyRequestDto requestDto,
                             @LoginUser SessionUser user){
        return participateService.apply(user.getId(),
                requestDto.getGroupId(), requestDto.getComment()).getId();
    }

    @DeleteMapping("/api/v1/participate/{id}")
    public Long quit(@PathVariable Long partiId){
        participateService.quit(partiId);
        return partiId;
    }

    @PostMapping("/api/v1/participate/approve/{id}")
    public Long approve(@PathVariable Long partiId){
        participateService.approve(partiId);
        return partiId;
    }

    @PostMapping("/api/v1/participate/reject/{id}")
    public Long reject(@PathVariable Long partiId){
        participateService.reject(partiId);
        return partiId;
    }

    //저장하기

}
