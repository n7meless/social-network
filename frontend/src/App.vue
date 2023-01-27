<template>
  <header-view></header-view>
  <router-view></router-view>
</template>

<script>
import AboutProject from "@/components/AboutProject";
import HeaderView from "@/views/HeaderView";

export default {
  components: {HeaderView, AboutProject}
}
</script>

<style>

.auth-body{
  display: flex;
  position: relative;
  height: 500px;
  width: 750px;
  border: 2px solid black;
  text-transform: uppercase;

}
* {
  /*font-family: 'Silkscreen', cursive;*/

  font-family: 'Changa', sans-serif;

  box-sizing: border-box;
  justify-content: center;
  margin: 0 auto;
}
/*a {*/
/*  outline: none;*/
/*  text-decoration: none;*/
/*  color: black;*/
/*}*/
/*a:hover {*/
/*  color: #42b983;*/
/*}*/

::-webkit-scrollbar-thumb {
  background-color: #42b983;
  box-shadow: inset 1px 1px 10px #f3faf7;
}

::-webkit-scrollbar {
  width: 10px; /* ширина для вертикального скролла */
  background-color: black;
  border: 1px solid #42b983;
}

::-webkit-scrollbar-thumb:hover {
  background-color: #999999;
}

::-webkit-scrollbar-button:vertical:start:decrement {
  background-color: #f6f8f4;
}

::-webkit-scrollbar-button:vertical:end:increment {
  background-color: #f6f8f4;
}

a{
  color: white;
  outline: 0;
  text-decoration: none;
}
a:hover{
  color: #42b983;
  text-decoration: underline;
}
.auth {
  text-align: center;
}
form{
  display: grid;
}
nav {
  padding: 30px;
  text-align: center;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
