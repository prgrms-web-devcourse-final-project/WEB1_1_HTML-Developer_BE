package com.backend.allreva;

import com.backend.allreva.common.config.JpaAuditingConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "aws.region=us-east-1",
        "view.count.schedule.rate=20"
})
@MockBean(JpaAuditingConfig.class)
@SpringBootTest
public abstract class IntegralTestSupport {
}
