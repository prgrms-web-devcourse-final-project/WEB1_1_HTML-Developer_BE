package com.backend.allreva.support;

import com.backend.allreva.common.util.DateHolder;
import java.time.LocalDate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class FixedDateHolder implements DateHolder {

    @Override
    public LocalDate getDate() {
        return LocalDate.of(2024, 9, 1);
    }
}
