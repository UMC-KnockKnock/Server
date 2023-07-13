package com.example.knockknock.domain.model;

import com.example.knockknock.constant.UserStatus;
import com.example.knockknock.dto.MemberUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @Column(name = "USERNAME")
    private String name;


    @Column(name = "NICKNAME")
    private String nickName;


    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDAY")
    private Integer birthDay;

    @Column(name = "AGE")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;


    public void updateMember(MemberUpdateRequestDto request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getNickName() != null) {
            this.nickName = request.getNickName();
        }
        if (request.getPhone() != null) {
            this.phone = request.getPhone();
        }
        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }
        if (request.getBirthDay() != null) {
            this.birthDay = request.getBirthDay();
        }
    }

}
