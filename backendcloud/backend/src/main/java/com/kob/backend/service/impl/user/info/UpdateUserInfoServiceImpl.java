package com.kob.backend.service.impl.user.info;

import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.info.UpdateUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateUserInfoServiceImpl implements UpdateUserInfoService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Map<String, String> updateInfo(Integer userId, String photo) {
        File test = new File("C:/Users/25445/Desktop/1.png");
        try {
            test.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(test);
            fw.write(photo);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> resp = new HashMap<>();
        User user = userMapper.selectById(userId);
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            resp.put("error_message", "error");
            return resp;
        }
        if(!path.exists()) path = new File("");
        System.out.println("path:" + path.getAbsolutePath());
        File file = new File(path.getAbsolutePath(),String.format("static/images/%s.png", userId));
        System.out.println("file path: " + file.getAbsolutePath());

        try {
            if (!file.createNewFile()) {
                file.delete();
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(photo);
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            resp.put("error_message", "error");
            return resp;
        }
        resp.put("error_message", "success");
        return resp;
    }
}
