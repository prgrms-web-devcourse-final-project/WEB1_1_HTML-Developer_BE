package com.backend.allreva.survey.command.domain;

import com.backend.allreva.common.event.Event;
import com.backend.allreva.survey.command.domain.value.Region;
import com.backend.allreva.survey.query.application.domain.SurveyDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.backend.allreva.common.config.KafkaConfig.TOPIC_SURVEY_SAVE;

@Getter
@NoArgsConstructor
public class SurveySavedEvent extends Event {

    private final String topic = TOPIC_SURVEY_SAVE;

    private Long surveyId;
    private String title;
    private Region region;
    private LocalDate endDate;

    public SurveySavedEvent(final Survey survey) {
        this.surveyId = survey.getId();
        this.title = survey.getTitle();
        this.region = survey.getRegion();
        this.endDate = survey.getEndDate();
    }

    public SurveyDocument to() {
        return SurveyDocument.builder()
                .id(surveyId.toString())
                .title(title)
                .region(region.name())
                .edDate(endDate)
                .participationCount(0)
                .build();
    }
}