package com.kob.botrunningsystem.untils;

public interface BotInterface {
    Integer nextMove(int[][] map, int meSx, int meSy, String meSteps, int youSx, int youSy, String youSteps);
}
