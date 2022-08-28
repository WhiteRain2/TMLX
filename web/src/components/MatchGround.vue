<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自出马</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">
                            {{ bot.title }}
                        </option>
                    </select>
                </div>
                <div class="matching-time">{{ matching_time_str }}</div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <div class="col-12" style="text-align: center; padding-top: 15vh;">
                <button type="button" class="btn btn-warning btn-lg" @click="click_match_btn">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from '@vue/reactivity'
import { useStore } from 'vuex';
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");
        let bots = ref([]);
        let select_bot = ref("-1");
        let matching_time = 0;
        let matching_time_str = ref("");

        const refresh_bots = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/user/bot/getlist/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    bots.value = resp;
                }
            });
        }

        refresh_bots();

        const  click_match_btn = () => {
            if (match_btn_info.value === "开始匹配") {
                matching_time = 0;
                matching_time_str.value = "0 : 0";
                match_btn_info.value = "取消匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                    bot_id: select_bot.value
                }));
            }
            else {
                match_btn_info.value = "开始匹配";
                matching_time = 0;
                matching_time_str.value = "";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
            requestAnimationFrame(updateTime);
        }

        let oneSecond = 0, last_timestamp;
        let start = false;
        const updateTime = (timedelta) => {
            if (!start) {
                start = true;
                last_timestamp = timedelta;
            } else {
                if (match_btn_info.value === "取消匹配") {
                    oneSecond += timedelta - last_timestamp;
                    if (oneSecond > 1000) {
                        matching_time ++;
                        oneSecond = 0;
                    }
                    if (matching_time < 60) {
                        matching_time_str.value = `0 : ${matching_time}`;
                    } else if (matching_time < 120) {
                        matching_time_str.value = `1 : ${matching_time - 60}`;
                    } else if (matching_time < 180) {
                        matching_time_str.value = `2 : ${matching_time - 120}`; 
                    } else {
                        store.state.pk.socket.send(JSON.stringify({
                            event: "stop-matching",
                        }));
                        match_btn_info.value = "开始匹配";
                        matching_time = 0;
                        matching_time_str.value = "匹配失败, 待会再试吧~";
                        return;
                    }
                }
            }
            last_timestamp = timedelta;
            requestAnimationFrame(updateTime);
        }
        
        return {
            match_btn_info,
            click_match_btn,
            bots,
            select_bot,
            matching_time_str
        }
    }
}
</script>

<style scoped>
div.matchground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(50, 50, 50, 0.5);
}
div.user-photo {
    text-align: center;
    padding-top: 10vh;
}
div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
}
div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.user-select-bot {
    padding-top: 20vh;
}
div.user-select-bot > select {
    width: 60%;
    margin: 0 auto;
}
div.matching-time {
    text-align: center;
    padding-top: 40px;
    font-size: 25px;
    font-weight: 600;
    color: wheat;
}
</style>