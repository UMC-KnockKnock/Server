package com.example.knockknock.domain.member;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {


        public static String LoginUsername(){
            UserDetails MemberDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return MemberDetails.getUsername();
        }
}
