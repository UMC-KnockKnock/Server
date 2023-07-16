package com.example.knockknock.domain.board.service;

import com.example.knockknock.domain.board.dto.PostSearchResponseDto;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.board.entity.SearchType;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.NotFoundMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public List<PostDetailResponseDto> getPostsByBoard(BoardType boardType) {
        List<Post> posts = postRepository.findByBoardType(boardType);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByAgeGroup(BoardType boardType, Integer ageGroup) {
        int ageGroupStart = ageGroup;
        int ageGroupEnd = ageGroupStart + 9;
        List<Post> posts = postRepository.findByBoardTypeAndMemberAgeBetween(boardType, ageGroupStart, ageGroupEnd);
        return posts.stream()
                .map(PostDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostDetailResponseDto> getPostsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
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

    @Transactional
    public List<PostSearchResponseDto> searchPost(BoardType boardType, SearchType searchType, String keyword) {

        List<Post> posts = switch (searchType) {
            case TITLE -> postRepository.findByBoardTypeAndTitleContainingIgnoreCase(boardType, keyword);
            case CONTENT -> postRepository.findByBoardTypeAndContentContainingIgnoreCase(boardType, keyword);
            case TITLE_AND_CONTENT -> postRepository.findByBoardTypeAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(boardType, keyword, keyword);
        };

        return posts.stream()
                .map(PostSearchResponseDto::from)
                .collect(Collectors.toList());
    }
}
