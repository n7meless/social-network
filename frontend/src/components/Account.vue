<template>
  <div class="account" v-bind="account">
    <h1>Create account</h1>
    <h3>Wait...</h3>
    <input type="text" v-model="account.username" placeholder="username">
    <input type="text" v-model="account.firstName" placeholder="firstName">
    <input type="text" v-model="account.lastName" placeholder="lastName">
    <input type="text" v-model="account.email" placeholder="email">
    <input type="password" v-model="account.password" placeholder="password">
    <input type="submit" @click="createNewUser()">Create User
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "account",
  data() {
    return {
      account: {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: ''
      }
    }
  },
  methods: {
    createNewUser() {
      console.log(this.account)
      axios.create({
            headers: {
              "Content-type": "application/json"
            }
          }
      ).post("/api/registration", this.account)
    }
  }
}
</script>

<style scoped>

</style>