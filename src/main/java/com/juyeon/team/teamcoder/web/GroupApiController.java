package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.web.dto.group.GroupResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupSaveRequestDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@ControllerAdvice
public class GroupApiController {
    // 로그인 후 유저 상세정보 입력, 수정, 탈퇴

    @Autowired
    private final GroupService groupService;

    @PostMapping("/api/v1/group")
    public Long save(@RequestBody GroupSaveRequestDto requestDto){
        Group group = groupService.save(requestDto);
        return group.getId();
    }

    @PutMapping("/api/v1/group/{groupId}")
    public Long update(@PathVariable String groupId,
                       @RequestBody GroupUpdateRequestDto requestDto){
        return groupService.update(Long.valueOf(groupId), requestDto);
    }

    @GetMapping("/api/v1/group/{id}")
    public GroupResponseDto findById (@PathVariable Long id) { return groupService.findById(id); }

    @DeleteMapping("/api/v1/group/{id}") //groupId
    public String delete(@PathVariable String id){
        groupService.delete(Long.valueOf(id));
        return id;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllDelete(IllegalAccessException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllArgument(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }
}
