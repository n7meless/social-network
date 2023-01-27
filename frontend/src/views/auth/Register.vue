<template>
  <div class="auth-body">
  <taurus></taurus>
  <div style="text-align: center">
    <auth-nav></auth-nav>
    <form @submit.prevent="submit">
      <my-input v-model="data.first_name" placeholder="FIRST NAME" type="text"/>
      <my-input v-model="data.last_name" placeholder="LAST NAME" type="text"/>
      <my-input v-model="data.email" placeholder="EMAIL" type="text"/>
      <my-input v-model="data.password" placeholder="PASSWORD" type="password"/>
      <my-input v-model="data.password_confirm" placeholder="PASSWORD CONFIRM" type="password"/>
      <my-button type="submit">
        SIGN IN
      </my-button>
    </form>
  </div>
  </div>
</template>

<script>
import axios from 'axios'
import AuthNav from "@/components/nav/AuthNav";
import MyInput from "@/components/UI/MyInput";
import MyButton from "@/components/UI/MyButton";
import Taurus from "@/components/img/Taurus";
import {reactive} from "vue";
import {useRouter} from "vue-router";

export default {
  name: "Register",
  components: {MyButton, MyInput, AuthNav, Taurus},
  setup() {
    const data = reactive({
      first_name: '',
      last_name: '',
      email: '',
      password: '',
      password_confirm: ''
    });
    const router = useRouter();
    const submit = async () => {
      await axios.post("http://localhost:4000/api/register", data);
      await router.push('/login')
    }
    return {data, submit};
  },
}
</script>

<style scoped>
</style>