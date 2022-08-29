<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'"/>
    <MatchGround v-if="$store.state.pk.status === 'matching'"/>
    <ResultBoard v-if="$store.state.pk.loser !== 'none'"/>
</template>

<script>
import { onMounted, onUnmounted } from '@vue/runtime-core';
import { useStore } from 'vuex'
import ResultBoard from '@/components/ResultBoard.vue'
import PlayGround from '@/components/PlayGround.vue'
import MatchGround from '@/components/MatchGround.vue'
import $ from 'jquery'

export default {
    components: {
        PlayGround,
        MatchGround,
        ResultBoard
    },
    setup() {
        console.log("setup");
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;
        store.commit("updateLoser", "none");
        store.commit("updateIsRecord", false);

        const sendTest = () => {
            const bot = store.state.pk.test_info.bot;
            const oppont = store.state.pk.test_info.oppont;
            let oppontId = -1;
            if (oppont != null) {
                oppontId = oppont.id;
            }
            const user_id = store.state.user.id;
            $.ajax({
                url: "http://127.0.0.1:3000/pk/start/game/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    a_id: user_id,
                    a_bot_id: oppontId,
                    b_id: user_id,
                    b_bot_id: bot.id
                },
                success() {
                }
            });
        }

        let socket = null;
        onMounted(() => {
            if (store.state.pk.status === "test") {
                sendTest();
            }
            console.log("onmouted");
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png"
            });
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                console.log("onmessage");
                const data = JSON.parse(msg.data);
                if (data.event === "start-matching") {
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo
                    });
                    if (store.state.pk.status !== "test") {
                        setTimeout(() => {
                            store.commit("updateStatus", "playing");                 
                        }, 2000);
                    } else {
                        store.commit("updateStatus", "playing");
                    }
                    store.commit("updateGame", data.game);
                } else if (data.event === "move") {
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                } else if (data.event === "result") {
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    const record = {
                        a_record: data.a_record,
                        b_record: data.b_record
                    }
                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit("updateLoser", data.loser);
                    store.commit("updateRecord", record);
                } else if (data.event === "count_down") {
                    const countDown = data.count_down;
                    store.commit("updateCountDown", countDown);
                } else if (data.event === "ready_info") {
                    const readyInfo = {
                        a_ready_info: data.a_ready_info,
                        b_ready_info: data.b_ready_info
                    };
                    store.commit("updateReadyInfo", readyInfo);
                }
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }
        });

        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus", "matching");

        });
    }
}
</script>

<style scoped>    
</style>