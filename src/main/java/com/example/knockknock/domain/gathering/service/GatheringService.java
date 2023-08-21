package com.example.knockknock.domain.gathering.service;

import com.example.knockknock.domain.friend.entity.Friend;
import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.domain.gathering.dto.request.GatheringRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringDetailResponseDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringResponseDto;
import com.example.knockknock.domain.gathering.entity.Gathering;
import com.example.knockknock.domain.gathering.repository.GatheringRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import com.example.knockknock.global.naverclient.LocalSearchRequestDto;
import com.example.knockknock.global.naverclient.LocalSearchResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final FriendRepository friendRepository;
    private final MemberIsLoginService memberIsLoginService;

    private final LocationRecommendService locationRecommendService;

    private final List<String> SEARCH_QUERIES = Arrays.asList("맛집", "카페", "술집", "관광지", "쇼핑몰");



    @Transactional
    public void createGathering(GatheringRequestDto request, UserDetailsImpl userDetails){
        Member member = memberIsLoginService.isLogin(userDetails);
        List<Friend> gatheringMembers= friendRepository.findAllById(request.getGatheringMemberIds());
        Gathering gathering = Gathering.builder()
                .member(member)
                .title(request.getTitle())
                .location(request.getLocation())
                .gatheringMembers(gatheringMembers)
                .build();

        gatheringRepository.save(gathering);

    }

    @Transactional
    public LocalSearchResponseDto recommendLocation(Long gatheringId){

        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));

        String location = gathering.getLocation();
        String currentQuery = SEARCH_QUERIES.get(gathering.getQueryIndex());
        LocalSearchRequestDto request = new LocalSearchRequestDto();
        request.setQuery(location + currentQuery);
        gathering.setQueryIndex((gathering.getQueryIndex() + 1) % SEARCH_QUERIES.size());;
        return locationRecommendService.localSearch(request);
    }

    @Transactional
    public List<GatheringResponseDto> getGatherings(UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        List<Gathering> gatherings = gatheringRepository.findAllByMember(member);
        return gatherings.stream()
                .map(GatheringResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public GatheringDetailResponseDto getGatheringDetail(Long gatheringId, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));

        return GatheringDetailResponseDto.of(gathering);
    }

    @Transactional
    public void updateGatheringInfo(Long gatheringId, GatheringUpdateRequestDto request, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));
        List<Friend> gatheringMembers= gathering.getGatheringMembers();
        gathering.updateGathering(request, gatheringMembers);

    }

    @Transactional
    public void deleteGathering(Long gatheringId, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        gatheringRepository.deleteById(gatheringId);

    }
}
