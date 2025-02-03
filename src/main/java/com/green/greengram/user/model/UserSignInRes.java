package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Schema(title="로그인 응답")
public class UserSignInRes {
    private long userId;
    private String nickName;
    private String pic;
    private String accessToken;

//    @JsonIgnore //swagger 표시 안 되지만, 응답 때 빼는 역할도 한다.
//    private String upw;
//    @JsonIgnore
//    private String message;
}
