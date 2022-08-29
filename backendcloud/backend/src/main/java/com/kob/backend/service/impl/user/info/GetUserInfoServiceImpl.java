package com.kob.backend.service.impl.user.info;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.info.GetUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetUserInfoServiceImpl implements GetUserInfoService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Map<String, String> getInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("rating", user.getRating().toString());
        return map;
    }
}
