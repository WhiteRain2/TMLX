<template>
    <div class="result-board"  v-if="$store.state.pk.a_id !== $store.state.pk.b_id">
        <div class="result-board-text" v-if="loser === 'all'">
            Draw
        </div>
        <div class="result-board-text" v-else-if="loser === me">
            Lose
        </div>
        <div class="result-board-text" v-else>
            Win
        </div>
        <div class="score-info">
            <div class="score-info-cur">{{ info_cur }}</div>
            <div class="score-info-total">当前总分: {{ info_total }}</div>
        </div>
        <div class="result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning btn-lg">再来!</button>
        </div>
    </div>
    <div class="result-board" v-else>
        <div class="result-board-text" style="line-height: 4">
            测试结束!
        </div>
    </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import { useStore } from 'vuex';
export default {
    setup() {
        const store = useStore();
        let me = ref("A");
        let loser = ref(store.state.pk.loser);
        if (store.state.pk.b_id === parseInt(store.state.user.id)) {
            me.value = "B";
        }
        const a_record = store.state.pk.a_record;        
        const b_record = store.state.pk.b_record;
        const restart = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png"
            });
        };
        let info_cur = ref("");
        let info_total = ref();
        if (loser.value === me.value) {
            info_cur.value = "天梯分-2";
        } else {
            info_cur.value = "天梯分+5";
        }
        if (me.value === "A") {
            info_total.value = a_record;
        } else if (me.value === "B") {
            info_total.value = b_record;
        }
        
        return {
            restart,
            me,
            loser,
            info_cur,
            info_total
        };
    }
}
</script>

<style scoped>
div.result-board {
    height: 50vh;
    width: 30vw;
    background-color: rgba(50, 50, 50, 0.5);
    position: absolute;
    top: 30vh;
    left: 35vw;
}
div.result-board-text {
    text-align: center;
    color: white;
    font-size: 60px;
    font-weight: 600;
    font-style: italic;
    padding-top: 5vh;
    padding-bottom: 2vh;
}
div.result-board-btn {
    padding-top: 7vh;
    text-align: center;
}
div.score-info {
    text-align: center;
    color: white;
    font-size: 25px;
    font-weight: 500;
}
div.score-info-total {
    padding-top: 1vh;
}
</style>