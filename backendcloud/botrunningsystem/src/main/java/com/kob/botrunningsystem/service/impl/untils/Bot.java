package com.kob.botrunningsystem.service.impl.untils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bot {
    Integer userId;
    Integer botId;
    String botCode;
    String input;
}
