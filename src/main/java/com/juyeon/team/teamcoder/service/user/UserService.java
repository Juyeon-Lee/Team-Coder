package com.juyeon.team.teamcoder.service.user;

import com.juyeon.team.teamcoder.domain.group.GroupRepository;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tag.TagRepository;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;
import com.juyeon.team.teamcoder.domain.tagUser.TagUserRepository;
import com.juyeon.team.teamcoder.domain.user.CustomUserRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.web.dto.user.UserResponseDto;
import com.juyeon.team.teamcoder.web.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final TagRepository tagRepository;
    private final TagUserRepository tagUserRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto){  // exclude picture
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));

        Set<TagUser> tagUsers = syncTagUser(requestDto.getTags(), user);

        user.updateRegister(requestDto.getName(), requestDto.getEducation(),
                requestDto.getBirth(), requestDto.getLocation(), tagUsers);
        return id;
    }

    /*
    기존 tag string 미리 저장 후 변경된 tag 하나씩 삭제 ,모든 작업 완료 후 남아있는 태그유저 삭제
     */
    private Set<TagUser> syncTagUser(List<String> tags, User user) {
        //tag, taguser 객체 생성 후 연결
        Set<TagUser> newTagUsers = new HashSet<TagUser>();
        for(String st : tags){
            Optional<Tag> optTag = tagRepository.findByName(st);
            Tag tag;
            if(optTag.isPresent()){ // 이미 존재하면 해당 태그로 연결
                tag=optTag.get();
            }else{
                tag=tagRepository.save(new Tag(st));
                //tagUser 검사 필요 없음 -> 바로 추가
                newTagUsers.add(tagUserRepository.save(TagUser.builder()
                        .tag(tag).user(user).build()));
                continue;
            }

            Optional<TagUser> optTagUser = tagUserRepository.findByTagAndUser(tag, user);
            if(optTagUser.isPresent()){  // 이미 존재하면 해당 태그로 연결
                newTagUsers.add(optTagUser.get());
            }else{
                newTagUsers.add(tagUserRepository.save(TagUser.builder()
                                            .tag(tag).user(user).build()));
            }
        }
        return newTagUsers;
    }

    @Transactional
    public Long updatePic(Long id, String fileName) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));

        user.updatePic(fileName);
        userRepository.save(user);

        return id;
    }

    @Transactional
    public void delete (Long id) throws IllegalAccessException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다. id="+ id));
        //TODO: 삭제 시 경고 / 모두 삭제 방법 중 선택하기
        if(!groupRepository.findAllByManager(user).isEmpty()){
            throw new IllegalAccessException("그룹의 매니저인 상태로는 탈퇴할 수 없습니다.");
        }
        userRepository.delete(user);
    }

    public UserResponseDto findById(Long id){
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id="+ id));

        //List<TagUser> tagusers = (List<TagUser>) customUserRepository.findTagUserByUser(entity);
//        List<String> tags = new ArrayList<String>();
//        for(TagUser t : tagusers){
//            tags.add(t.getTag().getName());
//            System.out.println("<tag출력>======================"+tags.toString());
//        }
        List<String> tags = (List<String>) customUserRepository.findTagByUser(entity);
        System.out.println(tags.toString());
        return new UserResponseDto(entity, tags);
    }


}
