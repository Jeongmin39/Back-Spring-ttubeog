package com.ttubeog.domain.game.dto.response;

import com.ttubeog.domain.benefit.domain.BenefitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRouletteRes {

    @Schema(description = "게임 ID", example = "1")
    private Long gameId;

    @Schema(description = "매장ID", example = "1")
    private Long storeId;

    @Schema(description = "옵션 내용", example = "[\"5% 할인\",\"아메리카노 증정\"]")
    private List<String> options;

    private List<RouletteBenefitResDto> benefits;

    @Builder
    public UpdateRouletteRes(Long gameId, Long storeId, List<String> options, List<RouletteBenefitResDto> benefits) {
        this.gameId = gameId;
        this.storeId = storeId;
        this.options = options;
        this.benefits = benefits;
    }
}
