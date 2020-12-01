package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.service.S3Service;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final UserService userService;
    private final GroupService groupService;
    private final S3Service s3Service;

    @PostMapping("/api/v1/user/pic/{id}")
    public String updateUserPic(@PathVariable String id,
                                @RequestParam("pastPath") String pastPath,
                            @RequestPart("picture") MultipartFile multipartFile) throws IOException {

        String uploadDir = s3Service.upload(pastPath, multipartFile,"users/");
        userService.updatePic(Long.valueOf(id), uploadDir);

        return "You successfully uploaded " + uploadDir + "!";
    }

    @PostMapping("/api/v1/group/pic/{id}")
    public String updateGroupPic(@PathVariable String id,
                                 @RequestParam("pastPath") String pastPath,
                            @RequestPart("file") MultipartFile multipartFile) throws IOException{

        String uploadDir = s3Service.upload(pastPath, multipartFile,"groups/");
        groupService.updateFile(Long.valueOf(id), uploadDir);

        return "You successfully uploaded " + uploadDir + "!";
    }
}
