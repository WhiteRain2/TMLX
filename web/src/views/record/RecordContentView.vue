<template>
    <PlayGround/>
    <div class="record-info">空格继续, q键退出</div>
</template>

<script>
import PlayGround from '@/components/PlayGround.vue'

export default {
    components: {
        PlayGround,
    },
    setup() {
    }
}
</script>

<style scoped>
div.record-info {
    text-align: center;
    font-weight: 600;
    
}
</style>