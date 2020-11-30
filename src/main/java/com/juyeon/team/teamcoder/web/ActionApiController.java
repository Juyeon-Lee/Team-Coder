package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.service.participate.ParticipateService;
import com.juyeon.team.teamcoder.service.participate.StoreService;
import com.juyeon.team.teamcoder.web.dto.ApplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ActionApiController {

    @Autowired
    private final ParticipateService participateService;

    @Autowired
    private final StoreService storeService;

    // 참여- 신청, 취소, 승인, 거절
    @PostMapping("/api/v1/participate/apply")
    public @ResponseBody Long apply(@RequestBody ApplyRequestDto requestDto,
                             @LoginUser SessionUser user){
        return participateService.apply(user.getId(),
                requestDto.getGroupId(), requestDto.getComment()).getId();
    }

    @DeleteMapping("/api/v1/participate/{partiId}")
    @ResponseStatus(value= HttpStatus.OK)
    public String quit(@PathVariable String partiId){
        participateService.quit(Long.valueOf(partiId));
        return partiId;
    }

    @PostMapping("/api/v1/participate/approve/{partiId}")
    @ResponseStatus(value= HttpStatus.OK)
    public String approve(@PathVariable String partiId){
        participateService.approve(Long.valueOf(partiId));
        return partiId;
    }

    @PostMapping("/api/v1/participate/reject/{partiId}")
    @ResponseStatus(value= HttpStatus.OK)
    public String reject(@PathVariable String partiId){
        participateService.reject(Long.valueOf(partiId));
        return partiId;
    }

    //저장하기
    @PostMapping("/api/v1/storage/{groupId}")
    @ResponseStatus(value= HttpStatus.OK)
    public Long store(@PathVariable String groupId,
                      @LoginUser SessionUser user){
        return storeService.addToStorage(user.getId(), Long.valueOf(groupId)).getId();
    }

    @DeleteMapping("/api/v1/storage/{storeId}")
    @ResponseStatus(value= HttpStatus.OK)
    public Long deleteFromStorage(@PathVariable String storeId,
                      @LoginUser SessionUser user){
        return storeService.deleteFromStorage(Long.valueOf(storeId));
    }
}
