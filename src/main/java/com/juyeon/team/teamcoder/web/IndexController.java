package com.juyeon.team.teamcoder.web;

import com.juyeon.team.teamcoder.config.auth.LoginUser;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.service.posts.PostsService;
import com.juyeon.team.teamcoder.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // @LoginUser 어노테이션으로 세션 정보 값 가져옴.
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

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

    @GetMapping("/user/info")
    public String userInfo(Model model, @LoginUser SessionUser user){
        if(user != null){
            model.addAttribute("user", user);
            model.addAttribute("userName", user.getName());
        }
        return "user-info";
    }

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
        return "apply-list";
    }

    @GetMapping("/search")
    public String search(Model model){
        return "search-group";
    }

    @GetMapping("/group")
    public String group() { return "group-manage"; }

    @GetMapping("/group/save")
    public String groupSave() { return "group-save"; }

    @GetMapping("/group/update")
    public String groupUpdate() { return "group-update"; }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
