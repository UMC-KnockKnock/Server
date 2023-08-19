package com.example.knockknock.domain.gathering.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GatheringNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gatheringNotificationId;

    @Column(name = "TITLE")
    private String title;

    @OneToOne
    @JoinColumn(name="GATHERING_Id", nullable = false)
    private Gathering gathering;

    @Column
    private LocalDateTime createdDate; // Gathering의 생성 날짜

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private GatheringNotificationRepeat notificationRepeat;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active;  // 기본값을 활성화 상태로 설정.


    public void setGathering(Gathering gathering) {
        this.gathering = gathering;
    }

    public void setActive() {
        this.active = !active;
    }

    public void setNotificationRepeat(GatheringNotificationRepeat notificationRepeat) {
        this.notificationRepeat = notificationRepeat;
    }
}
