package com.kob.backend.service.user.info;

import java.util.Map;

public interface UpdateUserInfoService {
    Map<String, String> updateInfo(Integer userId, String photo);
}
