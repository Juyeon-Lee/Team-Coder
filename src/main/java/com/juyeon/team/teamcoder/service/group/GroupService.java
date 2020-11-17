package com.juyeon.team.teamcoder.service.group;

import com.juyeon.team.teamcoder.domain.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;

}
