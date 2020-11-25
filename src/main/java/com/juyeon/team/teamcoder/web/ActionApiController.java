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
    @PostMapping("/api/v1/participate/apply")
    public Long apply(@RequestBody ApplyRequestDto requestDto,
                             @LoginUser SessionUser user){
        return participateService.apply(user.getId(),
                requestDto.getGroupId(), requestDto.getComment()).getId();
    }

    @DeleteMapping("/api/v1/participate/{partiId}")
    public String quit(@PathVariable String partiId){
        participateService.quit(Long.valueOf(partiId));
        return partiId;
    }

    @PostMapping("/api/v1/participate/approve/{partiId}")
    public String approve(@PathVariable String partiId){
        participateService.approve(Long.valueOf(partiId));
        return partiId;
    }

    @PostMapping("/api/v1/participate/reject/{partiId}")
    public String reject(@PathVariable String partiId){
        participateService.reject(Long.valueOf(partiId));
        return partiId;
    }

    //저장하기


}
