import Vue from 'vue'
import Vuex from 'vuex'
import { getProfile } from '@/api/user' // 导入个人信息接口

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: localStorage.getItem('diningHallToken') || '',
        diningHallInfo: {} // 商家信息（含窗口位置）
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token
            localStorage.setItem('diningHallToken', token)
        },
        // 新增：保存商家信息
        SET_DINING_HALL_INFO(state, info) {
            state.diningHallInfo = info
        }
    },
    actions: {
        // 新增：获取商家个人信息
        getProfile({ commit }) {
            return new Promise((resolve, reject) => {
                getProfile().then(res => {
                    commit('SET_DINING_HALL_INFO', res)
                    resolve(res)
                }).catch(err => {
                    reject(err)
                })
            })
        }
    },
    getters: {
        // 新增：暴露商家信息 getter
        diningHallInfo: state => state.diningHallInfo
    },
    modules: {}
})