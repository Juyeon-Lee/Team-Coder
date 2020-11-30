package com.juyeon.team.teamcoder.service.group;

import com.juyeon.team.teamcoder.domain.group.*;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tag.TagRepository;
import com.juyeon.team.teamcoder.domain.tagGroup.TagGroup;
import com.juyeon.team.teamcoder.domain.tagGroup.TagGroupRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.web.dto.group.GroupListResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupSaveRequestDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final CustomGroupRepository customGroupRepository;
    private final TagGroupRepository tagGroupRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Transactional
    public Group save(GroupSaveRequestDto requestDto){
        Long id = requestDto.getOwnerId();
        System.out.println("id출력=========================="+id.toString());
        User user = userRepository.findById(id)  // 그룹매니저
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));
        // 먼저 group 저장.
        Group group = groupRepository.save(requestDto.toEntity(user));
        //tag, taguser 객체 생성 후 연결
        group.setTagGroups(syncTagGroup(requestDto.getTags(), group));
        group.make(); // manage의 생성그룹 리스트에 추가
        return group;
    }

    @Transactional
    public Long update(Long groupId, GroupUpdateRequestDto requestDto) {
        //이미 만들어져있음
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹의 정보가 없습니다. id="+ groupId));

        group.update(requestDto.getName(), requestDto.getFile(), requestDto.getAim(),
                requestDto.getEducation(), requestDto.getLocation(),
                requestDto.getDescription(),
                new Num(requestDto.getMaxNum(),group.getMemberNum().getCurrentNum()),
                new Age(requestDto.getMinAge(),requestDto.getMaxAge()),
                new Period(requestDto.getStart(), requestDto.getEnd()),
                syncTagGroup(requestDto.getTags(), group)); //tag, taggroup 객체 생성 후 연결
        return groupId;
    }

    /*
    기존 tag string 미리 저장 후 변경된 tag 하나씩 삭제 ,모든 작업 완료 후 남아있는 태그그룹 삭제
     */
    private Set<TagGroup> syncTagGroup(List<String> tags, Group group) {

        Set<TagGroup> newTagGroups = new HashSet<>();
        for(String st : tags){
            Optional<Tag> optTag = tagRepository.findByName(st);
            Tag tag;
            if(optTag.isPresent()){ // 이미 존재하면 해당 태그로 연결
                tag=optTag.get();
            }else{
                tag=tagRepository.save(new Tag(st));
                //tagGroup 검사 필요 없음 -> 바로 추가
                newTagGroups.add(tagGroupRepository.save(TagGroup.builder()
                        .tag(tag).group(group).build()));
                continue;
            }

            Optional<TagGroup> optTagGroup = tagGroupRepository.findByTagAndGroup(tag, group);
            if(optTagGroup.isPresent()){  // 이미 존재하면 해당 태그그룹으로 연결
                TagGroup tg = optTagGroup.get();
                newTagGroups.add(tg);
                // 전 set에서 삭제
                //origianlTagGroups.remove(tg);
            }else{
                newTagGroups.add(tagGroupRepository.save(TagGroup.builder()
                        .tag(tag).group(group).build()));
            }

        }
        return newTagGroups;
    }

    @Transactional
    public Long updateFile(Long id, String fileName) throws IOException {
        Group group = groupRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 그룹의 정보가 없습니다. id="+id));

        group.updateFile(fileName);
        groupRepository.save(group);

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 그룹의 정보가 없습니다. id="+id));

        groupRepository.delete(group);
    }

    // ================================find=========================================
    public GroupResponseDto findById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 그룹의 정보가 없습니다. id="+id));

        List<String> tags= customGroupRepository.findTagByGroup(group);
        return new GroupResponseDto(group, tags);
    }

    @Transactional(readOnly = true) // 조회 기능만 남겨둠.
    public List<GroupListResponseDto> findAllByManagerDesc(Long id){
        User user = userRepository.findById(id)  // 그룹매니저
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 정보가 없습니다. id="+id));

        // tags String으로 변환해서 넣을 건지 - 그냥 세부 화면에만 tag 띄우자..
        return groupRepository.findAllByManager(user)
                .stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)  // index.html에서 그룹 리스트
    public List<GroupListResponseDto> findAll(){
        return groupRepository.findAll()
                .stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<GroupListResponseDto> findAllByCondition(String aim, String period, int age,
                                   String loc, String tag) {
        List<String> tags = new ArrayList<>(Arrays.asList(tag.split(",")));  // 콤마로 구분

        return customGroupRepository.findAllByCondition(aim,period,age, loc, tags)
                .stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }
}
