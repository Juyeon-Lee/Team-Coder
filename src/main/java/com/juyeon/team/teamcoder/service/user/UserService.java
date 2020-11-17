package com.juyeon.team.teamcoder.service.user;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tag.TagRepository;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;
import com.juyeon.team.teamcoder.domain.tagUser.TagUserRepository;
import com.juyeon.team.teamcoder.domain.user.CustomUserRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.web.dto.UserResponseDto;
import com.juyeon.team.teamcoder.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final TagRepository tagRepository;
    private final TagUserRepository tagUserRepository;

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto){  // exclude picture
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));

        //tag, taguser 객체 생성 후 연결
        List<TagUser> tagUsers = new ArrayList<TagUser>();
        for(String st : requestDto.getTags()){
            Optional<Tag> optionalTag = tagRepository.findByName(st);
            Tag tag;
            if(optionalTag.isPresent()){ // 이미 존재하면 해당 태그로 연결
                tag=optionalTag.get();
            }else{
                tag=tagRepository.save(new Tag(st));
            }

            Optional<TagUser> optionalTagUser = tagUserRepository.findByTagAndUser(tag,user);
            if(optionalTagUser.isPresent()){  // 이미 존재하면 해당 태그로 연결
                tagUsers.add(optionalTagUser.get());
            }else{
                tagUsers.add(tagUserRepository.save(TagUser.builder()
                                            .tag(tag).user(user).build()));
            }
        }

        user.updateRegister(requestDto.getName(), "",
                requestDto.getEducation(), requestDto.getBirth(),
                requestDto.getLocation(), tagUsers);
        return id;
    }

    @Transactional
    public Long updatePic(Long id, String fileName) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));

        user.updatePic(fileName);
        userRepository.save(user);

        return id;
    }

    public UserResponseDto findById(Long id){
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id="+ id));

        List<TagUser> tagusers = customUserRepository.findTagUserByUser(entity);
        List<String> tags = new ArrayList<String>();
        for(TagUser t : tagusers){
            tags.add(t.getTag().getName());
        }
        return new UserResponseDto(entity, tags);
    }

    @Transactional
    public void delete (Long id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다. id="+ id));

        userRepository.delete(user);
    }



}
