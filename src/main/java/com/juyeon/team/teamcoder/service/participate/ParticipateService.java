package com.juyeon.team.teamcoder.service.participate;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.group.GroupRepository;
import com.juyeon.team.teamcoder.domain.participate.Participate;
import com.juyeon.team.teamcoder.domain.participate.ParticipateRepository;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParticipateService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ParticipateRepository partiRepository;

    //========================for user ================================================
    @Transactional
    public Participate apply(Long userId, Long groupId, String comment){
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

    //========================for group manager ================================================
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
