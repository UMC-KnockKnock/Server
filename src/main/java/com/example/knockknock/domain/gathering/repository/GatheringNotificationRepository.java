package com.example.knockknock.domain.gathering.repository;

import com.example.knockknock.domain.gathering.entity.Gathering;
import com.example.knockknock.domain.gathering.entity.GatheringNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringNotificationRepository extends JpaRepository<GatheringNotification, Long> {

    GatheringNotification findByGathering(Gathering gathering);
}
