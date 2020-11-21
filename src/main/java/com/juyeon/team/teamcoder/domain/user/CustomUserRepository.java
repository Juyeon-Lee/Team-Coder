package com.juyeon.team.teamcoder.domain.user;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;

import java.util.List;

public interface CustomUserRepository {
    public List findTagByUser(User userEntity);
}
