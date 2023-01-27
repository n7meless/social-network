<template>
  <div class="auth-body">
    <taurus></taurus>
    <form @submit.prevent="submit" class="auth">
      <h2>RESET YOUR PASSWORD</h2>
      <div style="text-align: left">
        <ul>
          <li v-bind:class="data.password===data.password_confirm&&data.password.length>0?'success':''">passwords
            match
          </li>
          <li v-bind:class="/[0-9]/.test(data.password)&&/[A-Z]/.test(data.password)?'success':''">one capital letter
            and
            number
          </li>
          <li v-bind:class="data.password.length>=8?'success':''">password length > 8</li>
        </ul>
      </div>
      <my-input v-model="data.password" placeholder="PASSWORD" type="password"/>
      <my-input v-model="data.password_confirm" placeholder="PASSWORD CONFIRM" type="password"/>
      <my-button type="submit">
        SIGN IN
      </my-button>
    </form>
  </div>
</template>

<script>
import {reactive, ref} from "vue";
import MyInput from "@/components/UI/MyInput";
import Taurus from "@/components/img/Taurus";
import MyButton from "@/components/UI/MyButton";
import axios from "axios";
import {useRoute, useRouter} from "vue-router";

export default {
  name: "Reset",
  components: {MyButton, MyInput, Taurus},
  setup() {
    const route = useRoute();
    const router = useRouter();
    const data = reactive({
      password: '',
      password_confirm: ''
    })
    const submit = async () => {
      const error = await axios.post('reset', {
        ...data,
        token: route.params.token,
      })
      console.log(error)
      if (error.request.status!==400){
        await router.push("/login")
      }
    }
    return {
      submit, data, router
    }
  }
}
</script>

<style scoped>
h2 {
  color: black;
  text-align: center;
  vertical-align: top;
}

form {
  justify-content: end;
  align-content: end;
}

.success {
  color: #42b983;
}

.danger {
  color: red;
}

li {
  list-style-type: square;
}


</style>