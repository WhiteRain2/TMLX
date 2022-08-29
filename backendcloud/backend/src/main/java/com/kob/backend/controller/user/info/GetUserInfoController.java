package com.kob.backend.controller.user.info;

import com.kob.backend.service.user.info.GetUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUserInfoController {
    @Autowired
    GetUserInfoService getUserInfoService;

    @GetMapping("/api/user/info/getinfo/")
    public Map<String, String> getUserInfo(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        return getUserInfoService.getInfo(userId);
    }
}
