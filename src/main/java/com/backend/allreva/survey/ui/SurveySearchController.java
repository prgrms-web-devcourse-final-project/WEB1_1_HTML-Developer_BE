package com.backend.allreva.survey.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.survey.query.application.SurveySearchService;
import com.backend.allreva.survey.query.application.dto.SurveyThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/survey")
public class SurveySearchController {
    private final SurveySearchService surveySearchService;

    @GetMapping("/")
    public Response<List<SurveyThumbnail>> searchSurveyThumbnail(@RequestParam final String query) {
        return Response.onSuccess(
                surveySearchService.searchSurveyThumbnails(query)
        );
    }
}
