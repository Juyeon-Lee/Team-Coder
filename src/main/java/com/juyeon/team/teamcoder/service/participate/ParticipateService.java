package com.juyeon.team.teamcoder.service.participate;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.group.GroupRepository;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.participate.ParticipateRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import com.juyeon.team.teamcoder.web.dto.PartiListResponseDto;
import com.juyeon.team.teamcoder.web.dto.group.GroupListResponseDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ParticipateService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ParticipateRepository partiRepository;

    //========================for user ================================================
    @Transactional
    public Participate apply(Long userId, Long groupId, String comment){
        if(partiRepository.findByUser_idAndGroup_id(userId,groupId).isPresent()){
            throw new IllegalAddException("이미 지원했습니다. '참여 신청 현황'에서 확인해보세요.");
        }
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 없습니다. id="+ groupId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id="+ userId));

        return partiRepository.save(Participate.builder()
                .group(group).user(user).comment(comment).build());
    }

    @Transactional
    public void quit(Long participateId){
        Participate parti = partiRepository.findById(participateId)
                .orElseThrow(()-> new IllegalArgumentException("해당 신청/참여 기록이 없습니다. id="+participateId));

        partiRepository.delete(parti);
    }

    public List<PartiListResponseDto> findAllByUser(Long userId){
        return partiRepository.findAllByUser_IdOrderByIdAsc(userId)
                .stream().map(PartiListResponseDto::new)
                .collect(Collectors.toList());
    }

    //========================for group manager ================================================
    public List<PartiListResponseDto> findAllByGroup(Long groupId){
        return partiRepository.findAllByGroup_IdOrderByIdAsc(groupId)
                .stream().map(PartiListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void approve(Long id){
        Participate parti = partiRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 신청/참여 기록이 없습니다. id="+id));
        parti.approve();
    }

    @Transactional
    public void reject(Long id){
        Participate parti = partiRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 신청/참여 기록이 없습니다. id="+id));
        parti.reject();
    }
}
