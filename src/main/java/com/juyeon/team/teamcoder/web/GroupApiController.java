package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.web.dto.group.GroupResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupSaveRequestDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class GroupApiController {
    // 로그인 후 유저 상세정보 입력, 수정, 탈퇴

    @Autowired
    private final GroupService groupService;

    @PostMapping("/api/v1/group")
    public Long save(@RequestBody GroupSaveRequestDto requestDto){
        Group group = groupService.save(requestDto);
        return group.getId();
    }

    @PutMapping("/api/v1/group/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody GroupUpdateRequestDto requestDto){
        return groupService.update(id, requestDto);
    }

    @PostMapping("/api/v1/group/pic/{id}")
    public String updatePic(@PathVariable Long id,
                       @RequestPart("file") MultipartFile multipartFile) throws IOException{
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "group-files/" + id;
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        groupService.updateFile(id, fileName);

        return "You successfully uploaded " + fileName + "!";
    }

    @GetMapping("/api/v1/group/{id}")
    public GroupResponseDto findById (@PathVariable Long id) { return groupService.findById(id); }

    @DeleteMapping("/api/v1/group/{id}")
    public Long delete(@PathVariable Long id){
        groupService.delete(id);
        return id;
    }
}
