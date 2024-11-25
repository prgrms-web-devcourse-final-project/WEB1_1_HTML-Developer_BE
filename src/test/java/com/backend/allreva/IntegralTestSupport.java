package com.backend.allreva;

import com.backend.allreva.common.config.JpaAuditingConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@MockBean(JpaAuditingConfig.class)
public abstract class IntegralTestSupport {

}
