package com.example.knockknock.domain.member.dto;

import jakarta.validation.constraints.Email;

import java.util.Optional;

public record MemberUpdateDto(@Email Optional<String> email, Optional<String> nickName, Optional<String> phoneNumber){

}

