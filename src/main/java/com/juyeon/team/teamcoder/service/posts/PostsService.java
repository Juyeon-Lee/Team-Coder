package com.juyeon.team.teamcoder.service.posts;

import com.juyeon.team.teamcoder.domain.posts.Posts;
import com.juyeon.team.teamcoder.domain.posts.PostsRepository;
import com.juyeon.team.teamcoder.web.dto.PostsListResponseDto;
import com.juyeon.team.teamcoder.web.dto.PostsResponseDto;
import com.juyeon.team.teamcoder.web.dto.PostsSaveRequestDto;
import com.juyeon.team.teamcoder.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)   // 조회 기능만 남겨둔다.
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());    // repository에서 넘어온 stream을 mapping해서 Dto로 변환하여 List로 반환한다.
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);

    }
}
