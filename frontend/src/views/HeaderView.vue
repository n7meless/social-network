<template>
  <div class="header">
    <div class="left-container">
      <div v-if="!auth">
        <router-link to="/login">
          Authenticate
        </router-link>
      </div>
      <div v-else>
        <router-link @click="logout" to="/">
          Logout
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import MyButton from "@/components/UI/MyButton";
import {computed} from "vue";
import axios from "axios";
import {useStore} from "vuex";

export default {
  name: "HeaderView",
  components: {MyButton},
  setup() {
    const store = useStore();
    const auth = computed(() => store.state.auth)

    const logout = async () => {
      await axios.post("logout", {},
          {withCredentials: true})
      axios.defaults.headers.common['Authorization'] = '';
      await store.dispatch('setAuth', false)
    }
    return {
      auth, logout
    }
  }
}
</script>

<style scoped>
.left-container {
  position: absolute;
  right: 10px;
  color: white;
  outline: none;
}

.header {
  height: 100px;
  width: 100%;
  background: black;
  padding: 0 20px;
  color: white;
  margin-bottom: 5%;
}
</style>