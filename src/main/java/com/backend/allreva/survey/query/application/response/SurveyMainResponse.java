package com.backend.allreva.survey.query.application.response;

import com.backend.allreva.survey.infra.elasticsearch.SurveyDocument;
import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;

public record SurveyMainResponse(
        Long id,
        String title,
        Region region,
        Integer participationCount,
        LocalDate edDate
) {
        // 기본 생성자 추가
        public static SurveyMainResponse of(
                final Long id,
                final String title,
                final Region region,
                final Integer participationCount,
                final LocalDate edDate
        ) {
            return new SurveyMainResponse(id, title, region, participationCount, edDate);
        }

        public static SurveyDocument toSurveyDocument(
                final SurveyMainResponse surveyDocumentDto
        ) {
                return  new SurveyDocument(
                        surveyDocumentDto.id.toString(),
                        surveyDocumentDto.title,
                        surveyDocumentDto.region.toString(),
                        surveyDocumentDto.participationCount,
                        surveyDocumentDto.edDate
                );
        }

}