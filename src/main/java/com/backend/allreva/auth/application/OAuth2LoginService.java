package com.backend.allreva.auth.application;

import com.backend.allreva.auth.application.dto.UserInfo;

public interface OAuth2LoginService {

    String getRequestURL();
    UserInfo getUserInfo(String authorizationCode);
}
