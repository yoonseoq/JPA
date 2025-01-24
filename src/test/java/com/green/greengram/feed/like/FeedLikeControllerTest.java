package com.green.greengram.feed.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.config.jwt.JwtProperties;
import com.green.greengram.config.jwt.TokenAuthenticationFilter;
import com.green.greengram.config.jwt.TokenProvider;
import com.green.greengram.config.security.WebSecurityConfig;
import com.green.greengram.feed.like.model.FeedLikeReq;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = FeedLikeController.class
        , excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class FeedLikeControllerTest {
    @Autowired
    ObjectMapper objectMapper; //JSON사용
    @Autowired
    MockMvc mockMvc; //요청(보내고)-응답(받기) 처리
    @MockBean
    FeedLikeService feedLikeService; //가짜 객체를 만들고 빈등록한다.

    final String BASE_URL = "/api/feed/like";
    final long feedId_2 = 2L;
    FeedLikeTestCommon common;

    @BeforeEach
    void setUp() {
        common = new FeedLikeTestCommon(objectMapper);
    }

    @Test
    @DisplayName("좋아요 등록")
    void feedLikeReg() throws Exception {
        feedLikeToggle(1);
    }

    @Test
    @DisplayName("좋아요 취소")
    void feedLikeCancel() throws Exception {
        feedLikeToggle(0);
    }


    private void feedLikeToggle(final int result) throws Exception {
        FeedLikeReq givenParam = common.getGivenParam(feedId_2);

        given(feedLikeService.feedLikeToggle(givenParam)).willReturn(result);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL).queryParams(common.getParameter(feedId_2)) );

        String expectedResJson = common.getExpectedResJson(result);
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));

        verify(feedLikeService).feedLikeToggle(givenParam);
    }

    private MultiValueMap<String, String> getParameter() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>(1);
        queryParams.add("feedId", String.valueOf(feedId_2));
        queryParams.add("userId", "value");
        queryParams.add("name", "hong");
        return queryParams;
    }

    private FeedLikeReq getGivenParam(long feedId) {
        FeedLikeReq givenParam = new FeedLikeReq();
        givenParam.setFeedId(feedId_2);
        return givenParam;
    }

    private String getExpectedResJson(int result) throws Exception {
        ResultResponse expectedRes = ResultResponse.<Integer>builder()
                .resultMessage(result == 0 ? "좋아요 취소" : "좋아요 등록")
                .resultData(result)
                .build();
        return objectMapper.writeValueAsString(expectedRes);
    }
}