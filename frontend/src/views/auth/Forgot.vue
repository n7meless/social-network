<template>
  <div class="auth-body">
    <taurus></taurus>
  <div v-if="notify.cls" :class="notify.cls">
    {{notify.message}}
  </div>
  <form class="auth" @submit.prevent="submit">
    <h2>enter your email</h2>
    <my-input v-model="email" placeholder="EMAIL" type="text"/>
    <my-button type="submit">
      SUBMIT
    </my-button>
  </form>
  </div>
</template>

<script>
import {reactive, ref} from "vue";
import MyInput from "@/components/UI/MyInput";
import MyButton from "@/components/UI/MyButton";
import Taurus from "@/components/img/Taurus";
import axios from "axios";

export default {
  name: "Forgot",
  components: {MyButton, MyInput, Taurus},
  setup() {
    const email = ref()
    const notify = reactive({
      cls: '',
      message: ''
    })
    const submit = async () => {
      try {
        await axios.post('forgot', {
          email: email.value
        })
        notify.cls = "success"
        notify.message = "email was sent!"
      } catch (e) {
          notify.cls = "danger";
          notify.message = "email does not exist!"
      }
    }
    return {
      email, submit, notify
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
</style>