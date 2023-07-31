package com.example.knockknock.domain.board.service;

import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public Page<PostDetailResponseDto> getPostsByBoard(BoardType boardType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Post> postPage = postRepository.findByBoardType(boardType, pageable);
        return postPage.map(PostDetailResponseDto::of);
    }

    @Transactional
    public Page<PostDetailResponseDto> getPostsByAgeGroup(BoardType boardType, Integer ageGroup, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        int ageGroupStart = ageGroup;
        int ageGroupEnd = ageGroupStart + 9;
        Page<Post> postPage = postRepository.findByBoardTypeAndMemberAgeBetween(boardType, ageGroupStart, ageGroupEnd, pageable);
        return postPage.map(PostDetailResponseDto::of);
    }

    @Transactional
    public Page<PostSearchResponseDto> searchPost(BoardType boardType, SearchType searchType, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Post> postPage = switch (searchType) {
            case TITLE -> postRepository.findByBoardTypeAndTitleContainingIgnoreCase(boardType, keyword, pageable);
            case CONTENT -> postRepository.findByBoardTypeAndContentContainingIgnoreCase(boardType, keyword, pageable);
            case TITLE_AND_CONTENT -> postRepository.findByBoardTypeAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(boardType, keyword, keyword, pageable);
        };
        return postPage.map(PostSearchResponseDto::from);
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        List<Post> posts = postRepository.findByMember(member);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByHashtag(BoardType boardType, String tagName) {
        List<Post> posts = postRepository.findByBoardTypeAndHashtags_TagNameContainingIgnoreCase(boardType, tagName);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }


}
