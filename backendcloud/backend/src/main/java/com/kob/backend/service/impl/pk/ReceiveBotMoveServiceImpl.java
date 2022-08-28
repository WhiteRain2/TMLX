package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.consumer.untils.Game;
import com.kob.backend.consumer.untils.Player;
import com.kob.backend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer botId, Integer direction) {
        if (WebSocketServer.users.get(userId) != null) {
            Game game = WebSocketServer.users.get(userId).game;
            if (game != null) {
                Player playerA = game.getPlayerA();
                Player playerB = game.getPlayerB();
                if (playerA.getId().equals(userId) && playerA.getBotId().equals(botId)) {
                    game.setNextStepA(direction);
                } else if (playerB.getId().equals(userId) && playerB.getBotId().equals(botId)) {
                    game.setNextStepB(direction);
                }
            }
        }
        return "receive bot move success";
    }
}
