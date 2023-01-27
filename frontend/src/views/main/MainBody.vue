<template>
  <div class="main-body">
    <div class="nav-panel">
      <nav class="navigation-panel-left">
        <div class="nav-section" style="background: #af5757;">
          <router-link to="/news"><img src="../../assets/navbar/news.svg" alt=""></router-link>
        </div>
        <div class="nav-section" style="background: orange;">
          <router-link to="/msg"><img src="../../assets/navbar/friends.svg" alt=""></router-link>
        </div>
        <div class="nav-section" style="background: #006600;">
          <router-link to="/msg"><img src="../../assets/navbar/messages.svg" alt=""></router-link>
        </div>
      </nav>
      <nav class="navigation-panel-right">
        <div class="nav-section">
          <router-link to="/search"><img src="../../assets/navbar/search.svg" alt=""></router-link>
        </div>
        <div class="nav-section">
          <router-link to="/login"><img src="../../assets/navbar/about.svg" alt=""></router-link>
        </div>
        <div class="nav-section">
          <router-link to="/login"><img src="../../assets/navbar/settings.svg" alt=""></router-link>
        </div>
      </nav>
      <span>terminal — taurus — 77x26</span>
    </div>
    <div class="main-panel">
    <router-view></router-view>
    </div>

  </div>
</template>

<script>
import NewsPage from "@/components/NewsPage";
export default {
  name: "MainBody",
  components: {NewsPage}

}
</script>

<style scoped>
.main-panel {
  width: 100%;
  display: flex;
  text-transform: uppercase;
  padding: 10px;
  height: 500px;
}
.navigation-panel-right{
  position: absolute;
  justify-content: left;
  align-content: end;
  right: 0;
  display: flex;
}
.navigation-panel-left {
  position: absolute;
  display: flex;
  justify-content: left;
  align-content: end;
}

.nav-section{
  width: 40px;
  height: 40px;
  margin: 0px 5px;
  justify-content: center;
  justify-items: center;
  padding: 5px;
  border: 2px solid black;
  box-shadow: inset 0px 5px 10px black;
  border-radius: 50px;
  transition: 0.5s;
}
.nav-section:hover{
  box-shadow: inset 0px -5px 10px black;
  transition: 0.5s;
}
img {
  height: 25px;
}

.nav-section a{
  height: 30px;
}

.main-body {
  width: 1000px;
  border: 2px solid black;
  position: relative;
  display: block;
  box-sizing: border-box;
  background: black;
}

nav {
  padding: 0px;
  font-size: 20px;
  text-align: center;
  display: inline-flex;
}
.nav-panel{
  border-bottom: 2px solid black;
  padding: 2px 0;
  background: #999999;
  height: 45px;
  text-align: center;
}
li:hover{
background: #42b983;
}
</style>