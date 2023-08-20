package com.example.knockknock.domain.friend.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class FriendRequestDto {
    private String friendName;
    private String nickName;
    private String phoneNumber;
}
