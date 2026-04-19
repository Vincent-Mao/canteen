import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('diningHallToken') || '',
        diningHallInfo: {}
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token
            localStorage.setItem('diningHallToken', token)
        },
        SET_DINING_HALL_INFO(state, info) {
            state.diningHallInfo = info
        }
    },
    actions: {
        getProfile({ commit }) {
            return new Promise((resolve) => {
                // 强制模拟数据，保证页面不白屏
                const mockInfo = { name: '测试商家', windowPosition: '一楼101' }
                commit('SET_DINING_HALL_INFO', mockInfo)
                resolve(mockInfo)
            })
        }
    },
    getters: {
        diningHallInfo: state => state.diningHallInfo
    },
    modules: {}
})