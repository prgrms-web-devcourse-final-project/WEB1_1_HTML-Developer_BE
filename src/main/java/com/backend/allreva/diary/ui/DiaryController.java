package com.backend.allreva.diary.ui;

import com.backend.allreva.auth.application.AuthMember;
import com.backend.allreva.common.dto.Response;
import com.backend.allreva.diary.command.application.DiaryCommandService;
import com.backend.allreva.diary.command.application.dto.AddDiaryRequest;
import com.backend.allreva.diary.command.application.dto.UpdateDiaryRequest;
import com.backend.allreva.diary.query.DiaryQueryService;
import com.backend.allreva.diary.query.dto.DiaryDetailResponse;
import com.backend.allreva.diary.query.dto.DiarySummaryResponse;
import com.backend.allreva.member.command.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
@RestController
public class DiaryController {

    private final DiaryCommandService diaryCommandService;
    private final DiaryQueryService diaryQueryService;

    @Operation(summary = "공연 기록 등록", description = "Multipart_Form_Data 로 설정하고 넣어주셔야 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "Multipart_Form_Data"))
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Long> addDiary(
            @RequestPart(value = "request") final AddDiaryRequest request,
            @RequestPart(value = "images", required = false) final List<MultipartFile> images,
            @AuthMember final Member member
    ) {
        Long diaryId = diaryCommandService.add(request, images, member.getId());
        return Response.onSuccess(diaryId);
    }

    @Operation(summary = "공연 기록 수정", description = "Multipart_Form_Data 로 설정하고 넣어주셔야 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "Multipart_Form_Data"))
    })
    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<Void> updateDiary(
            @RequestPart(value = "request") final UpdateDiaryRequest request,
            @RequestPart(value = "images", required = false) final List<MultipartFile> images,
            @AuthMember final Member member
    ) {
        diaryCommandService.update(request, images, member.getId());
        return Response.onSuccess();
    }

    @Operation(summary = "공연 기록 상세 조회", description = "공연 기록 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{diaryId}")
    public Response<DiaryDetailResponse> findDiaryDetail(
            @PathVariable("diaryId") final Long diaryId,
            @AuthMember final Member member
    ) {
        DiaryDetailResponse detail = diaryQueryService.findDetailById(diaryId, member.getId());
        return Response.onSuccess(detail);
    }

    @Operation(summary = "공연 기록 목록 조회", description = "공연 기록 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/list")
    public Response<List<DiarySummaryResponse>> findSummaries(
            @RequestParam(name = "year") final int year,
            @RequestParam(name = "month") final int month,
            @AuthMember final Member member
    ) {
        List<DiarySummaryResponse> summaries = diaryQueryService.findSummaries(
                member.getId(),
                year,
                month
        );
        return Response.onSuccess(summaries);
    }


    @Operation(summary = "공연 기록 삭제", description = "공연 기록 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{diaryId}")
    public Response<Void> deleteDiary(
            @PathVariable("diaryId") final Long diaryId,
            @AuthMember final Member member
    ) {
        diaryCommandService.delete(diaryId, member.getId());
        return Response.onSuccess();
    }
}
