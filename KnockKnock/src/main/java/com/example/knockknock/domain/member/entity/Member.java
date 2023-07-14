package com.example.knockknock.domain.member.entity;

import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.member.dto.MemberUpdateRequestDto;
import com.example.knockknock.global.timestamp.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_GENDER")
    private Gender memberGender;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDAY")
    private String birthDay;

    @Column(name = "AGE")
    private Integer age;

    public void updateMember(MemberUpdateRequestDto request) {
        if (request.getMemberName() != null) {
            this.memberName = request.getMemberName();
        }
        if (request.getNickName() != null) {
            this.nickName = request.getNickName();
        }
        if (request.getPhoneNumber() != null) {
            this.phoneNumber = request.getPhoneNumber();
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
