package com.juyeon.team.teamcoder.domain.posts;

import com.juyeon.team.teamcoder.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
