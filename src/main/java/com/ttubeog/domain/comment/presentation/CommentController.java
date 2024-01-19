package com.ttubeog.domain.comment.presentation;

import com.ttubeog.domain.comment.application.CommentService;
import com.ttubeog.domain.comment.dto.request.UpdateCommentReq;
import com.ttubeog.domain.comment.dto.request.WriteCommentReq;
import com.ttubeog.domain.comment.dto.response.UpdateCommentRes;
import com.ttubeog.domain.comment.dto.response.WriteCommentRes;
import com.ttubeog.global.config.security.token.CurrentUser;
import com.ttubeog.global.config.security.token.UserPrincipal;
import com.ttubeog.global.payload.ErrorResponse;
import com.ttubeog.global.payload.Message;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "Comment API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WriteCommentRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "댓글 작성 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @PostMapping
    public ResponseEntity<?> writeComment(
            @Parameter(description = "AccessToken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody WriteCommentReq writeCommentReq
    ) {
        return commentService.writeComment(userPrincipal, writeCommentReq);
    }

    // 댓글 수정
    @Operation(summary = "댓글 수정", description = "댓글 내용을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdateCommentRes.class) ) } ),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @PatchMapping
    public ResponseEntity<?> updateComment(
            @Parameter(description = "AccessToken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateCommentReq updateCommentReq
    ) {
        return commentService.updateComment(userPrincipal, updateCommentReq);
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "댓글 삭제 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @Parameter(description = "AccessToken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long commentId
    ) {
        return commentService.deleteComment(userPrincipal, commentId);
    }

    // 댓글 조회
}
