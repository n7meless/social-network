<template>
  <nav>
    <li>
      <router-link to="/login">SIGN IN</router-link>
    </li>
    <li>
      <router-link to="/registration">SIGN UP</router-link>
    </li>
  </nav>
</template>

<script>
export default {
  name: "AuthNav"
}
</script>

<style scoped>
nav li + li:before{
  content: " | ";
  padding: 0 10px;
}
nav{
  font-size: 20px;
  text-align: center;
  display: inline-flex;
}
nav li{
  list-style-type: none;
}
</style>