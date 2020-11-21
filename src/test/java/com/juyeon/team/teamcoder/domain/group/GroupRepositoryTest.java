package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.posts.Posts;
import com.juyeon.team.teamcoder.domain.posts.PostsRepository;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.service.group.GroupService;
import com.juyeon.team.teamcoder.web.dto.GroupSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomGroupRepository customGroupRepository;

    @After  // 단위 테스트가 끝날 때마다 수행되는 메소드
    public void cleanup() {
        groupRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String name = "테스트 그룹";
        String content = "테스트 설명";
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("c");
        tags.add("web");

        Long id = userRepository.save(User.builder().name("user1").email("email1").build()).getId();

        GroupSaveRequestDto gDto1 = GroupSaveRequestDto.builder()
                .name(name).aim("STUDY")
                .description(content)
                .ownerId(id)
                .education("JUNIOR")
                .location("SEOUL")
                .maxAge(27).minAge(20)
                .maxNum(4).tags(new ArrayList<>())
                .build();

        groupService.save(gDto1);

        //when
        List<Group> groupList = groupRepository.findAll();
        //findallwithtags 만들어서 써야하나????

        //then
        Group group = groupList.get(0);
        assertThat(group.getName()).isEqualTo(name);
        assertThat(group.getDescription()).isEqualTo(content);

        List<String> tagList = customGroupRepository.findTagByGroup(group);
        assertThat(tagList).isEmpty();//.isEqualTo(tagList);
        // repository에 getall tags 까지 못할까..?
    }

    /*
    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4, 0,0,0);
        groupRepository.save(Posts.builder()
        .title("title")
        .content("content")
        .author("author")
        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate="
        +posts.getCreatedDate()+", modifiedDate="+ posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }*/
}
