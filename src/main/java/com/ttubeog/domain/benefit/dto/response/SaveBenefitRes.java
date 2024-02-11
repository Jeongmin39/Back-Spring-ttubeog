package com.ttubeog.domain.benefit.dto.response;

import com.ttubeog.domain.benefit.domain.BenefitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaveBenefitRes {

    @Schema(description = "멤버-혜택ID", example = "1")
    private Long id;

    @Schema(description = "혜택 ID", example = "1")
    private Long benefitId;

    @Schema(description = "내용", example = "아메리카노 20% 할인")
    private String content;

    @Schema(description = "혜택타입", example = "SALE")
    private BenefitType type;

    @Schema(description = "사용 여부", example = "false")
    private Boolean used;

    @Schema(description = "만료 여부", example = "false")
    private Boolean expried;

    @Schema(description = "혜택 저장 시간", example = "2024-01-12 09:30:50")
    private LocalDateTime createdAt;

    @Builder
    public SaveBenefitRes(Long id, Long benefitId, String content, BenefitType type, Boolean used, Boolean expried, LocalDateTime createdAt) {
        this.id = id;
        this.benefitId = benefitId;
        this.content = content;
        this.type = type;
        this.used = used;
        this.expried = expried;
        this.createdAt = createdAt;
    }
}
