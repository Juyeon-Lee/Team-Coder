package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.user.User;

import java.util.List;

public interface CustomGroupRepository {
    public List<String> findTagByGroup(Group groupEntity);

    public List<Group> findAllByCondition(String aim, String period, int age,
                                          String loc, List<String> tags);
}
