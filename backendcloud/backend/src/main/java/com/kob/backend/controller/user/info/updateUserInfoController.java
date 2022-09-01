package com.kob.backend.controller.user.info;

import com.kob.backend.service.user.info.UpdateUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class updateUserInfoController {
    @Autowired
    UpdateUserInfoService updateUserInfoService;

    @PostMapping("/api/user/info/updateinfo/")
    Map<String, String> updateInfo(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        String photo = data.get("user_photo");
        return updateUserInfoService.updateInfo(userId, photo);
    }
}
