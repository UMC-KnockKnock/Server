package com.example.knockknock.domain.member.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthenticationService {

    public String generateCode(){
        Random random = new Random();

        int code = random.nextInt(9000) +1000;
        return String.valueOf(code);
    }


}
