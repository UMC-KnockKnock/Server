package com.example.knockknock.domain.member.entity;

import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PasswordResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String code;

}
