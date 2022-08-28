package com.kob.botrunningsystem.service.impl.untils;

import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class Consumer extends Thread {
    private Bot bot;
    private static RestTemplate restTemplate;
    private final static String receiveBotMoveUrl = "http://127.0.0.1:3000/pk/receive/bot/move/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();

        try {
            this.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private String addUid(String code, String uid) {
        int k = code.indexOf(" implements java.util.function.Supplier<Integer> ");
        return code.substring(0, k) + uid + code.substring(k);
    }

    private String getCode(String nextMoveCode) {
        String before = "package com.kob.botrunningsystem.untils;\n" +
                "\n" +
                "import java.io.File;\n" +
                "import java.io.FileNotFoundException;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "public class Bot implements java.util.function.Supplier<Integer> {\n" +
                "    static class Cell {\n" +
                "        public int x, y;\n" +
                "        public Cell(int x, int y) {\n" +
                "            this.x = x;\n" +
                "            this.y = y;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    private boolean check_tail_increasing(int steps) {\n" +
                "        if (steps <= 10) return true;\n" +
                "        return steps % 3 == 1;\n" +
                "    }\n" +
                "\n" +
                "    public List<Cell> getCells(int sx, int sy, String steps) {\n" +
                "        steps = steps.substring(1, steps.length()-1);\n" +
                "        List<Cell> res = new ArrayList<>();\n" +
                "\n" +
                "        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};\n" +
                "        int x = sx, y = sy;\n" +
                "        int step = 0;\n" +
                "        res.add(new Cell(x, y));\n" +
                "        for (int i = 0; i < steps.length(); i++) {\n" +
                "            int d = steps.charAt(i) - '0';\n" +
                "            x += dx[d];\n" +
                "            y += dy[d];\n" +
                "            res.add(new Cell(x, y));\n" +
                "            if (!check_tail_increasing(++step)) {\n" +
                "                res.remove(0);\n" +
                "            }\n" +
                "        }\n" +
                "        return res;\n" +
                "    }\n";
        String after = " @Override\n" +
                "    public Integer get() {\n" +
                "        File file = new File(\"input.txt\");\n" +
                "        try {\n" +
                "            Scanner sc = new Scanner(file);\n" +
                "            String input = sc.next();\n" +
                "            String[] strs = input.split(\"#\");\n" +
                "            int[][] g = new int[13][14];\n" +
                "            for (int i = 0, k = 0; i < 13; i++) {\n" +
                "                for (int j = 0; j < 14; j++, k++) {\n" +
                "                    if (strs[0].charAt(k) == '1') {\n" +
                "                        g[i][j] = 1;\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "            int meSx = Integer.parseInt(strs[1]), meSy = Integer.parseInt(strs[2]);\n" +
                "            int youSx = Integer.parseInt(strs[4]), youSy = Integer.parseInt(strs[5]);\n" +
                "            String meSteps = strs[3], youSteps = strs[6];\n" +
                "            return nextMove(g, meSx, meSy, meSteps, youSx, youSy, youSteps);\n" +
                "        } catch (FileNotFoundException e) {\n" +
                "            throw new RuntimeException(e);\n" +
                "        }\n" +
                "    }\n" +
                "}\n";
        return before + '\n' + nextMoveCode + '\n' + after;
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);
        Supplier<Integer> botInterface = Reflect.compile(
                "com.kob.botrunningsystem.untils.Bot" + uid,
                addUid(getCode(bot.getBotCode()), uid)
        ).create().get();


        File file = new File("input.txt");
        try (PrintWriter fout = new PrintWriter(file)) {
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer direction = botInterface.get();
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", bot.getUserId().toString());
        data.add("bot_id", bot.getBotId().toString());
        data.add("direction", direction.toString());
        restTemplate.postForObject(receiveBotMoveUrl, data, String.class);
    }
}
