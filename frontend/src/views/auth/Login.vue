<template>
  <div class="auth-body">
  <taurus></taurus>
  <div class="auth">
    <auth-nav></auth-nav>
    <form @submit.prevent="submit">
      <my-input v-model="data.email" placeholder="EMAIL" type="text"/>
      <my-input v-model="data.password" placeholder="PASSWORD" type="password"/>
      <my-button type="submit">
        SIGN IN
      </my-button>
      <router-link to="/forgot">Forgot password?</router-link>
    </form>
  </div>
  </div>
</template>

<script type="text/javascript">
import axios from 'axios'
import MyInput from "@/components/UI/MyInput";
import MyButton from "@/components/UI/MyButton";
import AuthNav from "@/components/nav/AuthNav";
import Taurus from "@/components/img/Taurus";
import {reactive} from "vue";
import {useRouter} from "vue-router";

export default {
  name: "Login",
  components: {MyButton, MyInput, AuthNav, Taurus},
  setup() {
    const data = reactive({
      email: '',
      password: ''
    })

    const router = useRouter();

    const submit = async () => {
      const response = await axios.post("login", data, {
        withCredentials: true
      });
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token
      await router.push('/')
    }
    return {data, submit}
  },
}
</script>

<style scoped>
</style>