package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.service.user.UserService;
import com.juyeon.team.teamcoder.web.dto.GroupResponseDto;
import com.juyeon.team.teamcoder.web.dto.OptionDto;
import com.juyeon.team.teamcoder.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;
    private final GroupService groupService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // @LoginUser 어노테이션으로 세션 정보 값 가져옴.
        //model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){
            model.addAttribute("groups", groupService.findAll());
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/search")
    public String search(Model model){
        return "search_group";
    }

    // =================================로그인/로그아웃/권한없음=======================================
    /*
    nav-header에서 로그인/로그아웃 버튼 눌렀을 때 이동
     */
    @GetMapping("/logoption")
    public String loginOption(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getName());
        }
        return "logoption";
    }

    @GetMapping("/duplicated-login")
    public String duplicatedLogin(){
        return "duplicated";
    }

    @GetMapping("/user/denied")
    public String Denied(){
        return "user_denied";
    }

    // =================================유저 정보 관련=======================================
    @GetMapping("/user/info")
    public String userInfo(Model model, @LoginUser SessionUser user){
        if(user != null){
            UserResponseDto dto = userService.findById(user.getId());
            OptionDto optionDto = new OptionDto();
            model.addAttribute("loc", optionDto.getLocOption());
            model.addAttribute("edu", optionDto.getEduOption());
            model.addAttribute("user", dto);
            model.addAttribute("userName", user.getName());
        }
        return "user_info";
    }

    // =================================그룹 저장, 신청=======================================
    @GetMapping("/user/storage")
    public String userStorage(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getName());
        }
        return "storage";
    }

    @GetMapping("/user/apply")
    public String userApply(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getName());
        }
        return "apply_list";
    }

    
    // =================================소유 그룹 관련=======================================
    @GetMapping("/group")
    public String group(Model model, @LoginUser SessionUser user) {
        if(user != null){
            model.addAttribute("groups",groupService.findAllByManagerDesc(user.getId()));
            model.addAttribute("userName", user.getName());
        }
        return "group_manage";
    }

    @GetMapping("/group/save")
    public String groupSave(Model model, @LoginUser SessionUser user){
        if(user != null){
            OptionDto optionDto = new OptionDto();
            model.addAttribute("loc", optionDto.getLocOption());
            model.addAttribute("edu", optionDto.getEduOption());
            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getName());
        }
        return "group_save";
    }

    @GetMapping("/group/update/{id}")
    public String groupUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        if(user != null){
            GroupResponseDto dto = groupService.findById(id);
            OptionDto optionDto = new OptionDto();
            model.addAttribute("loc", optionDto.getLocOption());
            model.addAttribute("edu", optionDto.getEduOption());
            model.addAttribute("group", dto);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getName());
        }
        return "group_update";
    }

}
