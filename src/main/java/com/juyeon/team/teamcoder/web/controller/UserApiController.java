package com.juyeon.team.teamcoder.web.controller;

import com.juyeon.team.teamcoder.domain.user.Role;
import com.juyeon.team.teamcoder.service.RoleService;
import com.juyeon.team.teamcoder.service.user.UserService;
import com.juyeon.team.teamcoder.web.dto.user.UserResponseDto;
import com.juyeon.team.teamcoder.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@ControllerAdvice
public class UserApiController {
    // 로그인 후 유저 상세정보 입력, 수정, 탈퇴

    private  final UserService userService;
    private final RoleService roleService;

    private final HttpSession httpSession;

    @PutMapping("/api/v1/user/{id}")
    public Long update(@PathVariable String id,
                       @RequestBody UserUpdateRequestDto requestDto){

        roleService.reloadRolesForAuthenticatedUser(Role.USER.getKey());
        return userService.update(Long.valueOf(id), requestDto);
    }

    @GetMapping("/api/v1/user/{id}")
    public UserResponseDto findById (@PathVariable Long id) { return userService.findById(id); }

    @DeleteMapping("/api/v1/user/{id}")
    public String delete(@PathVariable String id) throws IllegalAccessException {
        userService.delete(Long.valueOf(id));
        // 자동 로그아웃/ 세션 재할당
        httpSession.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
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
