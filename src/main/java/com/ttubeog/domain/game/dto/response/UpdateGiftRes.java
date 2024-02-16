package com.ttubeog.domain.game.dto.response;

import com.ttubeog.domain.benefit.domain.BenefitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateGiftRes {

    @Schema(description = "게임 ID", example = "1")
    private Long gameId;

    @Schema(description = "혜택 ID", example = "1")
    private Long benefitId;

    @Schema(description = "매장ID", example = "1")
    private Long storeId;

    @Schema(description = "시간제한", example = "00:00:15")
    private LocalTime timeLimit;

    @Schema(description = "선물개수", example = "3")
    private Integer giftCount;

    @Schema(description = "혜택 내용", example = "아메리카노 20% 할인")
    private String benefitContent;

    @Schema(description = "혜택 종류", example = "SALE")
    private BenefitType benefitType;

    @Builder
    public UpdateGiftRes(Long gameId, Long benefitId, Long storeId, LocalTime timeLimit, Integer giftCount, String benefitContent, BenefitType benefitType) {
        this.gameId = gameId;
        this.benefitId = benefitId;
        this.storeId = storeId;
        this.timeLimit = timeLimit;
        this.giftCount = giftCount;
        this.benefitContent = benefitContent;
        this.benefitType = benefitType;
    }
}
