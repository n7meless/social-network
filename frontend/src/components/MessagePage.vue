<template>
  <div class="main-message">
    <div class="search-panel">
      <search-input v-model="searchUser"></search-input>
      <div v-for="user in findUserByName" :key="user.id">
        <div class="user-field">
          <router-link :to="'/msg/'+user.id" class="user-btn">
            <button @click="connect" :value="connected" class="user-btn">{{user.first_name +' ' +user.last_name}}</button>
          </router-link>
        </div>
      </div>
    </div>
    <message-panel>
    </message-panel>
  </div>
</template>

<script>
import SearchInput from "@/components/SearchInput";
import MessagePanel from "@/components/MessagePanel";
import axios from "axios";

export default {
  name: "MessagePage",
  components: {SearchInput, MessagePanel},

  data() {
    return {
      connected: false,
      socket: '',
      stompClient: '',
      users: [],
      searchUser: '',
    };
  },
  methods: {
    async getUsers() {
      const response = await axios.get("http://localhost:4000/api/v1/accounts")
      this.users = response.data;
    },
    connect(){
      this.connected = true;
    }
  },
  mounted() {
    this.getUsers();
  },
  computed: {
    findUserByName() {
      return this.users.filter(user => (user.last_name + user.first_name).toLowerCase().includes(this.searchUser.toLowerCase()))
    }
  }
};
</script>

<style scoped>
.user-btn{
  font-size: 25px;
  border: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}
.user-btn:hover{
  background-color: #42b983;
}
.image {
  height: 50px;
  width: 50px;
  background: #42b983;
  border-radius: 50px;
  text-align: center;
  margin-right: 10px;
}

.main-message {
  width: 100%;
  display: flex;
  text-transform: uppercase;
  padding: 10px;
}

.search-panel {
  width: 400px;
  display: block;
  position: relative;
  overflow: auto;
}

.line {
  margin: 5px 0;
  border-bottom: 1px solid white;
  width: 100%;
}

.user-field {
  display: flex;
  height: 70px;
  padding: 10px;
  align-items: center;
}
</style>