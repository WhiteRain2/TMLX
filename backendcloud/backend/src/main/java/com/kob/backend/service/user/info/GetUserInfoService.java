package com.kob.backend.service.user.info;

import java.util.Map;

public interface GetUserInfoService {
    Map<String, String> getInfo(Integer userId);
}
