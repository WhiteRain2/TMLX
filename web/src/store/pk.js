export default {
  state: {
    status: "matching", //playing test
    test_info: {
      bot: null,
      opponent: null,
    },
    socket: null,
    opponent_username: "",
    opponent_photo: "",
    gamemap: null,
    a_id: 0,
    a_sx: 0,
    a_sy: 0,
    a_ready_info: false,
    a_bot_id: -1, //-1表示人玩
    b_id: 0,
    b_sx: 0,
    b_sy: 0,
    b_bot_id: -1,
    b_ready_info: false,
    gameObject: null,
    loser: "none", //all/a/b
    count_down: 0
  },
  getters: {
  },
  mutations: {
    updateSocket(state, socket) {
        state.socket = socket;
    },
    updateOpponent(state, opponent) {
        state.opponent_username = opponent.username;
        state.opponent_photo = opponent.photo;
    },
    updateStatus(state, status) {
        state.status = status;
    },
    updateTestInfo(state, test_info) {
      state.test_info = test_info;
    },
    updateGame(state, game) {
        state.gamemap = game.map;
        state.a_id = game.a_id;
        state.a_sx = game.a_sx;
        state.a_sy = game.a_sy;
        state.a_bot_id = game.a_bot_id;
        state.b_id = game.b_id;
        state.b_sx = game.b_sx;
        state.b_sy = game.b_sy;
        state.b_bot_id = game.b_bot_id;
    },
    updateGameObject(state, gameObject) {
      state.gameObject = gameObject;
    },
    updateLoser(state, loser) {
      state.loser = loser;
    },
    updateCountDown(state, countDown) {
      state.countDown = countDown;
    },
    updateReadyInfo(state, readyInfo) {
      state.a_ready_info = readyInfo.a_ready_info;
      state.b_ready_info = readyInfo.b_ready_info;
    }
  },
  actions: {
  },
  modules: {
  }
}
