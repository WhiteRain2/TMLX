<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%">我的Bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#add-bot-btn">
                            创建Bot
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="add-bot-btn" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">创建Bot</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="add-bot-title" class="form-label">名称</label>
                                        <input v-model="botadd.title" type="text" class="form-control" id="add-bot-title" placeholder="请输入Bot名称">
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-description" class="form-label">简介</label>
                                        <textarea v-model="botadd.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                    </div>
                                     <div class="mb-3">
                                        <label for="add-bot-code" class="form-label">代码</label>
                                        <VAceEditor
                                            v-model:value="botadd.content"
                                            @init="editorInit"
                                            lang="java"
                                            theme="textmate"
                                            style="height: 300px" />
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="error-message">{{ botadd.error_message }}</div>
                                    <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 10px;" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-'+bot.id">修改</button>
                                        <button type="button" class="btn btn-danger" @click="remove_bot(bot)" style="margin-right: 10px;">删除</button>
                                        <button type="button" class="btn btn-success" data-bs-toggle="modal" :data-bs-target="'#test-bot-modal-'+bot.id">测试</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-bot-modal-'+bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">创建Bot</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="mb-3">
                                                        <label for="add-bot-title" class="form-label">名称</label>
                                                        <input v-model="bot.title" type="text" class="form-control" id="add-bot-title" placeholder="请输入Bot名称">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-description" class="form-label">简介</label>
                                                        <textarea v-model="bot.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-code" class="form-label">代码</label>
                                                        <VAceEditor
                                                            v-model:value="bot.content"
                                                            @init="editorInit"
                                                            lang="java"
                                                            theme="textmate"
                                                            style="height: 300px" />
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="error-message">{{ bot.error_message }}</div>
                                                    <button type="button" class="btn btn-primary" @click="update_bot(bot)">保存修改</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'test-bot-modal-'+bot.id" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">请选择它的对手吧~</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body bot-test-body">
                                                    <button type="button" class="btn btn-light btn-lg" @click="test_bot(bot, null)" style="margin-right: 10px;">亲自出马</button>
                                                    <button type="button" class="btn btn-light btn-lg" @click="test_bot(bot, _bot)" v-for="_bot in bots" :key="_bot.id" style="margin-right: 10px;">{{ _bot.title }}</button>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">返回</button>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import { ref, reactive } from 'vue';
import { Modal } from 'bootstrap/dist/js/bootstrap';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import router from '@/router';

export default {
    components: {
        VAceEditor
    },
    setup() {
        ace.config.set(
        "basePath", 
        "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
        const store = useStore();
        let bots = ref([]);
        const bot_content_init = 
`public Integer nextMove(int[][] map, int meSx, int meSy, String meSteps, int youSx, int youSy, String youSteps) {
    // your code
    return 0;
}`;
        const botadd = reactive({
            title: "",
            description: "",
            content: bot_content_init,
            error_message: ""
        });
        const refresh_bots = () => {
            $.ajax({
                url: "http://172.16.0.3:3000/api/user/bot/getlist/",
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
        
        const add_bot = () => {
            botadd.error_message = "";
            $.ajax({
                url: "http://172.16.0.3:3000/api/user/bot/add/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        botadd.title = "";
                        botadd.description = "";
                        botadd.content = "";
                        Modal.getInstance("#add-bot-btn").hide();
                        refresh_bots();
                    }
                    else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }

        const update_bot = (bot) => {
            botadd.error_message = "";
            $.ajax({
                url: "http://172.16.0.3:3000/api/user/bot/update/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance('#update-bot-modal-'+bot.id).hide();
                        refresh_bots();
                    }
                    else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "http://172.16.0.3:3000/api/user/bot/remove/",
                type: "POST",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                data: {
                    bot_id: bot.id
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_bots();
                    }
                }
            });
        }

        const test_bot = (bot, oppont) => {
            Modal.getInstance('#test-bot-modal-'+bot.id).hide();
            store.commit("updateStatus", "test");
            store.commit("updateTestInfo", {
                bot: bot,
                oppont: oppont
            });
            router.push({name: 'pk_index'});
        }

        return {
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot,
            test_bot,
            bot_content_init
        }
    }
}
</script>

<style scoped>    
div.error-message {
    color: red;
}
</style>