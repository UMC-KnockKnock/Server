package com.example.knockknock.domain.gathering.service;

import com.example.knockknock.domain.gathering.dto.request.GatheringNotificationRequestDto;
import com.example.knockknock.domain.gathering.dto.request.GatheringNotificationUpdateRequestDto;
import com.example.knockknock.domain.gathering.dto.response.GatheringNotificationResponseDto;
import com.example.knockknock.domain.gathering.entity.Gathering;
import com.example.knockknock.domain.gathering.entity.GatheringNotification;
import com.example.knockknock.domain.gathering.repository.GatheringNotificationRepository;
import com.example.knockknock.domain.gathering.repository.GatheringRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GatheringNotificationService {
    private final GatheringRepository gatheringRepository;
    private final GatheringNotificationRepository gatheringNotificationRepository;

    @Transactional
    public void createNotification(Long gatheringId, GatheringNotificationRequestDto request){
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));
        GatheringNotification notification = GatheringNotification.builder()
                                                .gathering(gathering)
                                                .title(gathering.getTitle())
                                                .notificationRepeat(request.getNotificationRepeat())
                                                .createdDate(gathering.getCreatedAt())
                                                .active(request.isActive())
                                                .build();
        gatheringNotificationRepository.save(notification);
    }

    @Transactional
    public GatheringNotificationResponseDto getNotification(Long gatheringId){
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));
        GatheringNotification notification = gatheringNotificationRepository.findByGathering(gathering);
        return GatheringNotificationResponseDto.of(notification);
    }

    @Transactional
    public void updateRepeat(Long gatheringId, GatheringNotificationUpdateRequestDto request){
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));
        GatheringNotification notification = gatheringNotificationRepository.findByGathering(gathering);
        notification.setNotificationRepeat(request.getNotificationRepeat());
        gatheringNotificationRepository.save(notification);
    }

    @Transactional
    public Boolean updateStatus(Long gatheringId){
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.GATHERING_NOT_FOUND));
        GatheringNotification notification = gatheringNotificationRepository.findByGathering(gathering);
        notification.setActive();
        gatheringNotificationRepository.save(notification);
        return notification.isActive();
    }

}
