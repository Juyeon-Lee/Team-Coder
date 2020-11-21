package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.user.User;

import java.util.List;

public interface CustomGroupRepository {
    public List<String> findTagByGroup(Group groupEntity);

    public List<Group> findAllFilterAge(int age);
}
