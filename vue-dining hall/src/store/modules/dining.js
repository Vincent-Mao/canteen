import { login, getInfo } from '@/api/dining'
import { setToken, getToken, removeToken } from '@/utils/auth'

const state = {
    token: getToken() || '',
    userInfo: {}
}

const mutations = {
    SET_TOKEN(state, token) {
        state.token = token
        setToken(token)
    },
    SET_USER(state, data) {
        state.userInfo = data
    }
}

const actions = {
    async login({ commit }, data) {
        const res = await login(data)
        commit('SET_TOKEN', res.token)
    },
    async getInfo({ commit }) {
        const res = await getInfo()
        commit('SET_USER', res)
        return res
    },
    logout({ commit }) {
        removeToken()
        commit('SET_TOKEN', '')
        commit('SET_USER', {})
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}