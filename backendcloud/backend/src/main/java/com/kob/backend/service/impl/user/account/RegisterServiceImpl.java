package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.pojo.User;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能大于100");
            return map;
        }
        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度不能大于100");
            return map;
        }
        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }
        String encodedPassword = passwordEncoder.encode(password);
        String[] photos = {
                "https://cdn.acwing.com/media/user/profile/photo/161748_lg_ad3977ce8f.jpg",
                "https://cdn.acwing.com/media/user/profile/photo/201616_lg_b55c96120f.jpg",
                "https://cdn.acwing.com/media/user/profile/photo/182200_lg_0c2afd7eae.jpg",
                "https://cdn.acwing.com/media/user/profile/photo/109194_lg_e9250e44f2.jpg",
                "https://cdn.acwing.com/media/user/profile/photo/114619_lg_a75eadac0c.jpg",
                "https://cdn.acwing.com/media/user/profile/photo/100872_lg_3282c91e26.webp",
                "https://cdn.acwing.com/media/user/profile/photo/62329_lg_ff7781be86.webp",
                "https://cdn.acwing.com/media/user/profile/photo/69613_lg_73ca0939b2.jpg"
        };
        int index=(int)(Math.random() * photos.length);
        User user = new User(null, username, encodedPassword, photos[index], 1500);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
