package com.backend.allreva.search.query.application.dto;

import com.backend.allreva.search.query.domain.SurveyDocument;
import com.backend.allreva.survey.command.domain.value.Region;

import java.time.LocalDate;

public record SurveyDocumentDto(
        Long id,
        String title,
        Region region,
        Integer participationCount,
        LocalDate edDate
) {
        // 기본 생성자 추가
        public static SurveyDocumentDto of(
                Long id,
                String title,
                Region region,
                Integer participationCount,
                LocalDate edDate
        ) {
            return new SurveyDocumentDto(id, title, region, participationCount, edDate);
        }

        public static SurveyDocument toSurveyDocument(SurveyDocumentDto surveyDocumentDto) {
                return  new SurveyDocument(
                        surveyDocumentDto.id.toString(),
                        surveyDocumentDto.title,
                        surveyDocumentDto.region.toString(),
                        surveyDocumentDto.participationCount,
                        surveyDocumentDto.edDate
                );
        }

}