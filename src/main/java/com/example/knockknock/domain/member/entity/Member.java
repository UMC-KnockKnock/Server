package com.example.knockknock.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    /*@OneToMany(mappedBy = "member")
    private List<Post> posts; */

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
    private String birthday;

    @Column(name = "AGE")
    private Integer age;

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    //  정보 수정 //
    public void upPassword(BCryptPasswordEncoder bCryptPasswordEncoder, String toBePassword){
        this.password =bCryptPasswordEncoder.encode(password);
    }

    public void upMyPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void upUsername(String email){
        this.email = email;
    }

    public void updateNickName(String nickName){
        this.nickName =  nickName;
    }


    // 페스워드 암호화//
    public void encodePassword(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.password = bCryptPasswordEncoder.encode(password);
    }

    //비밀번호 변경 및 수정 확인

    public boolean matchPassword(BCryptPasswordEncoder bCryptPasswordEncoder, String checkPassword){
        return bCryptPasswordEncoder.matches(checkPassword, getPassword());
    }

    public void calculateAge() {
        if (birthday != null) {
            LocalDate currentDate = LocalDate.now();
            int birthYear = Integer.parseInt(birthday.substring(0, 2));// YYMMDD에서 YY 추출
            int birthMonth = Integer.parseInt(birthday.substring(2, 4));  // YYMMDD에서 MM 추출
            int birthDate = Integer.parseInt(birthday.substring(4, 6));  // YYMMDD에서 DD 추출

            int currentYear = currentDate.getYear();  // 현재 년도의 YY 추출
            int currentMonth = currentDate.getMonthValue();  // 현재 월 추출
            int currentDateOfMonth = currentDate.getDayOfMonth();  // 현재 날짜 추출

            if ((currentYear % 100 < birthYear) && (birthYear < 100)) {
                birthYear += 1900; // 2자리 연도를 4자리로 확장
            }
            if ((currentYear % 100 >= birthYear) && (birthYear < 100)) {
                birthYear += 2000;
            }

            int age = currentYear - birthYear;

            // 생일이 지났는지 체크
            if (birthMonth > currentMonth || (birthMonth == currentMonth && birthDate > currentDateOfMonth)) {
                age--;  // 생일이 지나지 않았다면 나이에서 1을 빼줌
            }

            this.age = age;

        }
    }
}


