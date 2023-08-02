package com.example.knockknock.domain.gathering.service;

import com.example.knockknock.domain.friend.repository.FriendRepository;
import com.example.knockknock.domain.gathering.dto.request.GatheringRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringUpdateRequestDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringDetailResponseDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringResponseDto;
import com.example.knockknock.domain.gathering.repository.GatheringRepository;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public void createGathering(GatheringRequestDto request){

    }

    @Transactional
    public List<GatheringResponseDto> getGatherings(UserDetailsImpl userDetails) {

    }

    @Transactional
    public GatheringDetailResponseDto getGatheringDetail(Long gatheringId, UserDetailsImpl userDetails) {

    }

    @Transactional
    public void updateGatheringInfo(Long gatheringId, GatheringUpdateRequestDto requst, UserDetailsImpl userDetails) {

    }

    @Transactional
    public void deleteGathering(Long gatheringId, UserDetailsImpl userDetails) {

    }
}
