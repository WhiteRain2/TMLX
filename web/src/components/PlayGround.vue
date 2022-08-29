<template>
    <div class="game-info">
        <div class="row" style="margin-top: 5px">
            <div class="col-5">
                <div class="a-ready-info read-info" v-if="$store.state.pk.a_id !== $store.state.pk.b_id">
                    <span v-if="$store.state.pk.a_id===parseInt($store.state.user.id)
                    && $store.state.pk.a_ready_info">我方已就绪</span>
                    <span v-else-if="$store.state.pk.a_id!==parseInt($store.state.user.id)
                    && $store.state.pk.a_ready_info">敌方已就绪</span>
                    <span v-else>准备中...</span>
                    <img src="@/assets/images/melogo.png" alt="" v-if="$store.state.pk.a_id===parseInt($store.state.user.id)" class="me-logo">
                    <img src="@/assets/images/botlogo.png" alt="" v-if="parseInt($store.state.pk.a_bot_id)!==-1" class="bot-logo">
                    <div class="a-read-info-logo"></div>
                </div>
            </div>
            <div class="col-2">
                <div class="count-down">
                    {{ $store.state.pk.countDown }}
                </div>
            </div>
            <div class="col-5">
                <div class="b-ready-info read-info" v-if="$store.state.pk.a_id !== $store.state.pk.b_id">
                    <div class="b-read-info-logo"></div>
                    <img src="@/assets/images/botlogo.png" alt="" v-if="parseInt($store.state.pk.b_bot_id)!==-1" class="bot-logo">
                    <img src="@/assets/images/melogo.png" alt="" v-if="$store.state.pk.b_id===parseInt($store.state.user.id)" class="me-logo">
                    <span v-if="$store.state.pk.b_id===parseInt($store.state.user.id)
                    && $store.state.pk.b_ready_info">我方已就绪</span>
                    <span v-else-if="$store.state.pk.b_id!==parseInt($store.state.user.id)
                    && $store.state.pk.b_ready_info">敌方已就绪</span>
                    <span v-else>准备中...</span>
                </div>
            </div>    
        </div>
    </div>
    <div class="playground">
        <GameMap></GameMap>        
    </div>
</template>

<script>
import GameMap from "./GameMap.vue";
import { onMounted } from "vue";
import { useStore } from 'vuex';

export default {
    components: {
        GameMap
    },
    setup() {
        const store = useStore();
        onMounted(() => {
            if (!store.state.record.is_record) {
                store.state.pk.socket.send(JSON.stringify({
                    event: "ui-ready",
                    success: true
                }));
            }
        });
    }
}
</script>

<style scoped>
div.row {
    width: 100%;
    height: 100%;
}
div.count-down {
    text-align: center;
    margin-top: 20px;
    color: red;
    font-size: 25px;
    font-weight: 600;
}
div.playground {
    width: 60vw;
    height: 70vh;
    margin: 20px auto;
}
div.a-ready-info {
    float: right;
}
div.a-read-info-logo {
    height: 3vh;
    width: 3vh;
    float: right;
    margin: 10px 5px;
    background-color: #4876EC;
}
div.b-read-info-logo {
    height: 3vh;
    width: 3vh;
    float: left;
    margin: 10px 5px;
    background-color: #F94848;
}
div.read-info {
    text-align: center;
    margin: 20px 5px;
    width: 15vw;
    font-size: 20px;
    font-weight: 600;
}
img {
    margin: 5px;
    height: 5vh;
    width: 4vh;
}
</style>