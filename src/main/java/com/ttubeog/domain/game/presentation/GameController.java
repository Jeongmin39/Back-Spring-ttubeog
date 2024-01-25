package com.ttubeog.domain.game.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ttubeog.domain.benefit.dto.request.CreateBenefitReq;
import com.ttubeog.domain.benefit.dto.response.CreateBenefitRes;
import com.ttubeog.domain.game.application.GameService;
import com.ttubeog.domain.game.dto.request.CreateGiftReq;
import com.ttubeog.domain.game.dto.response.CreateGiftRes;
import com.ttubeog.global.config.security.token.CurrentUser;
import com.ttubeog.global.config.security.token.UserPrincipal;
import com.ttubeog.global.payload.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Game", description = "Game API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
public class GameController {

    private final GameService gameService;

    //선물 게임 생성
    @Operation(summary = "선물 게임 생성", description = "선물 게임을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "선물 게임 생성 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CreateGiftRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "선물 게임 생성 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping
    public ResponseEntity<?> createGift(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody CreateGiftReq createGiftReq
    ) throws JsonProcessingException {
        return gameService.createGift(userPrincipal, createGiftReq);
    }

}
