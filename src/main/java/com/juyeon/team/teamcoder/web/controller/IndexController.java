package com.juyeon.team.teamcoder.web.controller;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.service.participate.ParticipateService;
import com.juyeon.team.teamcoder.service.participate.StoreService;
import com.juyeon.team.teamcoder.service.user.UserService;
import com.juyeon.team.teamcoder.web.dto.*;
import com.juyeon.team.teamcoder.web.dto.group.GroupListResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupResponseDto;
import com.juyeon.team.teamcoder.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;
    private final GroupService groupService;
    private final ParticipateService participateService;
    private final StoreService storeService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // @LoginUser 어노테이션으로 세션 정보 값 가져옴.
        OptionDto optionDto = new OptionDto();
        model.addAttribute("loc", optionDto.getLocOption());
        model.addAttribute("search",new SearchRequestDto());
        model.addAttribute("groups", groupService.findAll());
        if(user != null){
            model.addAttribute("userName", user.getName());
            // model.addAttribute("list", participateService.findAllByUser(user.getId()));
        }
        return "index";
    }

    @GetMapping("/search/main")
    public String search(Model model, @LoginUser SessionUser user){
        OptionDto optionDto = new OptionDto();
        model.addAttribute("loc", optionDto.getLocOption());
        model.addAttribute("search",new SearchRequestDto());
        model.addAttribute("groups", new ArrayList<GroupListResponseDto>());
        return "search_group";
    }

    @GetMapping("/search/result")
    public ModelAndView searchEnter(@ModelAttribute("groups") SearchRequestDto requestDto,
                                    ModelMap model, @LoginUser SessionUser user){
        OptionDto optionDto = new OptionDto();
        model.addAttribute("loc", optionDto.getLocOption());
        model.addAttribute("search",new SearchRequestDto());
        model.addAttribute("groups", groupService.findAllByCondition(requestDto.getAim(), requestDto.getPeriod(),
                requestDto.getAge(), requestDto.getLoc(), requestDto.getTags()));
        return new ModelAndView("search_group",model);
    }

    @PostMapping("/search/redirect")
    public String searchRedirect(@ModelAttribute SearchRequestDto requestDto,
                                 RedirectAttributes attributes){
        attributes.addFlashAttribute("groups", requestDto);
        return "redirect:/search/result";
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
    public String Denied(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
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
            model.addAttribute("list", storeService.findAllById(user.getId()));
        }
        return "storage";
    }

    @GetMapping("/user/apply")
    public String userApply(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getName());
            model.addAttribute("list", participateService.findAllByUser(user.getId()));
        }
        return "apply_list";
    }

    @GetMapping("/group/detail/{groupId}")  //그룹 세부정보 조회
    public String groupDetail(@PathVariable Long groupId, Model model,
                            @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        model.addAttribute("group", groupService.findById(groupId));
        return "group_detail";
    }

    @GetMapping("/user/detail/{userId}")
    public String userDetail(@PathVariable Long userId, Model model,
                             @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        model.addAttribute("userInfo", userService.findById(userId));
        return "user_detail";
    }

    // =================================소유 그룹 관련=======================================
    @GetMapping("/group/{groupId}/apply/users")
    public String groupApplyUsers(@PathVariable Long groupId,
                                  Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("list", participateService.findAllByGroup(groupId));
            model.addAttribute("userName", user.getName());
        }
        return "apply_users";
    }

    @GetMapping("/group/manage")
    public String groupManage(Model model, @LoginUser SessionUser user) {
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

    @GetMapping("/privacy/rule")
    public String privacyRule(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "privacy_rule";
    }
}
