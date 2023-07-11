package com.example.knockknock.model;

import com.example.knockknock.constant.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Column(name = "USERNAME")
    private String name;


    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "IS_ANONYMOUS")
    private boolean isAnonymous;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDAY")
    private int birthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

}
