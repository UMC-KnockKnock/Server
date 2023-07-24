package com.example.knockknock.domain.hashtag.service;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.hashtag.entity.Hashtag;
import com.example.knockknock.domain.hashtag.repository.HashtagRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final PostRepository postRepository;
    private final HashtagRepository hashtagRepository;


    @Transactional
    public void addHashtag(Long postId, HashtagRegisterRequestDto request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        hashtagRepository.save(Hashtag.builder()
                .post(post)
                .tagName(request.getTagName())
                .build());

    }

    @Transactional
    public void deleteHashtag(Long hashtagId) {

        Hashtag hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.HASHTAG_NOT_FOUND));

        hashtagRepository.delete(hashtag);
    }
}
