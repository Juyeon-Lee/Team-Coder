package com.juyeon.team.teamcoder.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.juyeon.team.teamcoder.config.LocalDateAdapter;
import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.user.EduLevel;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.web.dto.group.GroupSaveRequestDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    /*
    * withMockUser를 사용하기 위해 MockMvc에서만 작동하는데
    * 현재 @SpringBootTest로만 되어있음.
    * 여기서 함께 MockMvc를 사용하기 위한 코드
    * */
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()).build();

    }
    // MockMvc 코드 종료

    @After
    public void tearDown() throws Exception {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Group_등록된다() throws Exception {
        //given
        String aim="STUDY"; //STUDY, CONTEST, PROJECT, OTHER
        String loc="SEOUL";
        String name = "test group";
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("c");
        LocalDate ago = LocalDate.now().minusDays(3);
        LocalDate after = LocalDate.now().plusDays(3);

        Long manager = userRepository.save(User.builder().name("user1").email("email1").build()).getId();

        GroupSaveRequestDto requestDto = GroupSaveRequestDto.builder()
                .name(name).aim(aim)
                .description("")
                .ownerId(manager)
                .education("JUNIOR")
                .location(loc)
                .maxAge(27).minAge(20)
                .start(ago).end(after)
                .maxNum(4).tags(tags)
                .build();

        String url = "http://localhost:" + port + "/api/v1/group";
        //LocalDate json parser error 방지 위해
        SimpleModule simpleModule = new SimpleModule().addSerializer(LocalDate.class, new LocalDateAdapter());

        String json = new ObjectMapper()
                .registerModule(simpleModule)
                .writeValueAsString(requestDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        System.out.println(requestBuilder.toString());

        //when
        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk());

        //then
        List<Group> all = groupRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getLocation()).isEqualTo(Location.valueOf(loc));

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Group_수정된다() throws Exception {
        //given
        String aim="STUDY"; //STUDY, CONTEST, PROJECT, OTHER
        String loc="SEOUL";
        String name = "test group";
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("c");
        LocalDate ago = LocalDate.now().minusDays(3);
        LocalDate after = LocalDate.now().plusDays(3);

        Long manager = userRepository.save(User.builder().name("user1").email("email1").build()).getId();

        Group saveGroups = groupRepository.save(Group.builder()
            .name(name).aim(GroupAim.valueOf(aim))
            .manager(userRepository.getOne(manager))
            .education(EduLevel.BACHELOR)
            .location(Location.SEOUL)
            .ageLimit(new Age(20,30))
            .memberNum(new Num(5,0))
            .workPeriod(new Period(ago,after))
            .build());

        Long updateId = saveGroups.getId();

        GroupUpdateRequestDto requestDto = GroupUpdateRequestDto.builder()
                .name(name).aim("CONTEST")
                .education("JUNIOR")
                .location("SEOUL")
                .minAge(20).maxAge(30)
                .maxNum(5).currentNum(1)
                .start(ago).end(after)
                .tags(tags)
                .build();

        String url = "http://localhost:" + port + "/api/v1/group/" + updateId;

        SimpleModule simpleModule = new SimpleModule().addSerializer(LocalDate.class, new LocalDateAdapter());
        String json = new ObjectMapper().registerModule(simpleModule)
                .writeValueAsString(requestDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        //when
        mvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());

        //then
        List<Group> all = groupRepository.findAll();
        assertThat(all.get(0).getAim()).isEqualTo(GroupAim.CONTEST);
        assertThat(all.get(0).getEducation()).isEqualTo(EduLevel.JUNIOR);
    }
    // 삭제 delete + /api/v1/group/{groupId}

    // 예외 처리 잘 되는지
}
