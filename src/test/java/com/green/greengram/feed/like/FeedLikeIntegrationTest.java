package com.green.greengram.feed.like;

import com.green.greengram.BaseIntegrationTest;
import com.green.greengram.WithAuthUser;
import com.green.greengram.feed.like.model.FeedLikeReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithAuthUser
public class FeedLikeIntegrationTest extends BaseIntegrationTest {
    final String BASE_URL = "/api/feed/like";
    FeedLikeTestCommon common;

    @BeforeAll
    void setUp() {
        common = new FeedLikeTestCommon(objectMapper);
    }

    @Test
    @DisplayName("좋아요 등록")

    void feedLikeReq() throws Exception {
        final int regSuccessResult = 1;
        final long feedIdNotExisted = 5L;
        feedLikeToggle(regSuccessResult, feedIdNotExisted);
    }
    @Test
    @DisplayName("좋아요 취소")
    void feedLikeCancel() throws Exception {
        final int regSuccessResult = 0;
        final long feedIdNotExisted = 2L;
        feedLikeToggle(regSuccessResult, feedIdNotExisted);
    }

    void feedLikeToggle(final int result, final long feedId) throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get(BASE_URL).queryParams(common.getParameter(feedId)) );

        String expectedResJson = common.getExpectedResJson(result);
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));
    }

}
