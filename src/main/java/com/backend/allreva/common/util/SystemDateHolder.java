package com.backend.allreva.common.util;

import java.time.LocalDate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SystemDateHolder implements DateHolder {

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
