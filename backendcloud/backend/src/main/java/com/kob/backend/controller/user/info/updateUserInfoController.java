package com.kob.backend.controller.user.info;

import com.kob.backend.service.user.info.UpdateUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class updateUserInfoController {
    @Autowired
    UpdateUserInfoService updateUserInfoService;

    @PostMapping("/api/user/info/updateinfo/")
    Map<String, String> updateInfo(
                                   @RequestParam(value = "user_photo_file", required = false) MultipartFile photo) {
        System.out.println("--------------------------");
        //System.out.println(userId);
        System.out.println(photo);
        return null;
//        return updateUserInfoService.updateInfo(userId, photo);
    }
}
