package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.web.dto.GroupListResponseDto;
import com.juyeon.team.teamcoder.web.dto.GroupUpdateRequestDto;
import com.juyeon.team.teamcoder.web.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ActionApiController {

    @Autowired
    private final GroupService groupService;

    // 참여, 신청
    @GetMapping("/api/v1/search/")
    public List<GroupListResponseDto> update(@RequestBody SearchRequestDto requestDto){
        return groupService.findAllByCondition(requestDto.getAim(), requestDto.getPeriod(),
                requestDto.getAge(), requestDto.getLoc(),
                requestDto.getTags());
    }
}
