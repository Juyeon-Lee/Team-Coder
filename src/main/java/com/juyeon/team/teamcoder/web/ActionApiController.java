package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.service.participate.ParticipateService;
import com.juyeon.team.teamcoder.service.participate.StoreService;
import com.juyeon.team.teamcoder.web.dto.ApplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@ControllerAdvice
public class ActionApiController {

    @Autowired
    private final ParticipateService participateService;

    @Autowired
    private final StoreService storeService;

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
    @PostMapping("/api/v1/storage/{groupId}")
    public Long store(@PathVariable String groupId,
                      @LoginUser SessionUser user){
        return storeService.addToStorage(user.getId(), Long.valueOf(groupId)).getId();
    }

    @DeleteMapping("/api/v1/storage/{storeId}")
    public Long deleteFromStorage(@PathVariable String storeId,
                      @LoginUser SessionUser user){
        return storeService.deleteFromStorage(Long.valueOf(storeId));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRedundant(IllegalAddException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        //컨트롤러 클래스나 컨트롤러 메소드 위에 @ControllerAdvice 사용해서 사용 범위를 정할 수도 있다.
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllArgument(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }
}
