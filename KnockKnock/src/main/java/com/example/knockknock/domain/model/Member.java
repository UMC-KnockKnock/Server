package com.example.knockknock.domain.model;

import com.example.knockknock.constant.UserStatus;
import com.example.knockknock.dto.MemberUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String birthDay;

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

    public void calculateAge() {
        if (birthDay != null) {
            LocalDate currentDate = LocalDate.now();
            int birthYear = Integer.parseInt(birthDay.substring(0, 2));// YYMMDD에서 YY 추출
            int birthMonth = Integer.parseInt(birthDay.substring(2, 4));  // YYMMDD에서 MM 추출
            int birthDate = Integer.parseInt(birthDay.substring(4, 6));  // YYMMDD에서 DD 추출

            int currentYear = currentDate.getYear();  // 현재 년도의 YY 추출
            int currentMonth = currentDate.getMonthValue();  // 현재 월 추출
            int currentDateOfMonth = currentDate.getDayOfMonth();  // 현재 날짜 추출

            if ((currentYear % 100 < birthYear) && (birthYear < 100)){
                birthYear += 1900; // 2자리 연도를 4자리로 확장
            }
            if ((currentYear % 100 >= birthYear) && (birthYear < 100)) {
                birthYear += 2000;
            }

            int age = currentYear - birthYear;  // 현재 년도의 YY - 생년의 YY

            // 생일이 지났는지 체크
            if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDate > currentDateOfMonth)) {
                age--;  // 생일이 지나지 않았다면 나이에서 1을 빼줌
            }

            this.age = age;
        }
    }

}
