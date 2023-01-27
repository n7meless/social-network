import {createStore, ActionContext} from 'vuex'

export default createStore({
  state: {
    auth: false
  },
  getters: {
  },
  mutations: {
    setAuth(state, auth){
      state.auth = auth
    }
  },
  actions: {
    setAuth(context, auth){
      context.commit('setAuth', auth)
    }
  },
  modules: {
  }
})
