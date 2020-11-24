package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.domain.user.Role;
import com.juyeon.team.teamcoder.service.RoleService;
import com.juyeon.team.teamcoder.service.user.UserService;
import com.juyeon.team.teamcoder.web.dto.user.UserResponseDto;
import com.juyeon.team.teamcoder.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    // 로그인 후 유저 상세정보 입력, 수정, 탈퇴

    private  final UserService userService;
    private final RoleService roleService;

    @PutMapping("/api/v1/user/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody UserUpdateRequestDto requestDto){

        roleService.reloadRolesForAuthenticatedUser(Role.USER.getKey());
        return userService.update(id, requestDto);
    }

    @PostMapping("/api/v1/user/pic/{id}")
    public String updatePic(@PathVariable Long id,
                       @RequestPart("picture") MultipartFile multipartFile) throws IOException{
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        String uploadDir = "user-photos/" + id;
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        userService.updatePic(id, fileName);

        return "You successfully uploaded " + fileName + "!";
    }

    @GetMapping("/api/v1/user/{id}")
    public UserResponseDto findById (@PathVariable Long id) { return userService.findById(id); }

    @DeleteMapping("/api/v1/user/{id}")
    public Long delete(@PathVariable Long id){
        userService.delete(id);
        //TODO : 자동 로그아웃/ 세션 재할당
        return id;
    }
}
