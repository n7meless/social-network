<template>
  <div class="main-search">
    <div class="search-panel">
      <search-input v-model="searchUser"></search-input>
      <div  v-for="user in findUserByName" :key="user.id">
        <div class="user-field">
          <div class="image" v-text="user.first_name.charAt(0)">
          </div>
          <div style="display: table; text-align: left; width: 100%">
            <div style="font-weight: bold">
              <a :href="'account/'+user.id" v-text="user.first_name+' '+user.last_name"></a>
            </div>
            <div class="options">
              <a style="margin-right: 10px" :href="'add/'">Add friend</a>
              <a :href="'message/'">Message</a>
            </div>
          </div>
        </div>
        <div class="line"></div>
      </div>
    </div>
    <div class="filter-panel"></div>
  </div>
</template>

<script>
import axios from "axios";
import MyInput from "@/components/UI/MyInput";
import SearchInput from "@/components/SearchInput";

export default {
  name: "SearchPage",
  components: {MyInput, SearchInput},

  data() {
    return {
      users: [],
      searchUser:''
    }
  },
  mounted() {
    this.getUsers()
  },
  computed:{
    findUserByName(){
      return this.users.filter(user=>(user.last_name+user.first_name).toLowerCase().includes(this.searchUser.toLowerCase()))
    }
  }
  ,
  methods: {
    async getUsers() {
      const response = await axios.get("http://localhost:4000/api/v1/accounts")
      this.users = response.data;
      console.log(this.users)
    }
  }

}
</script>

<style scoped>

.options a {
  color: #999999;
}

.line {
  margin: 5px 0;
  border-bottom: 1px solid white;
  width: 100%;
}

.image {
  height: 50px;
  width: 50px;
  background: #42b983;
  border-radius: 50px;
  text-align: center;
  margin-right: 10px;
}

.user-field {
  display: flex;
  height: 70px;
  padding: 10px;
  align-items: center;
}

.main-search {
  width: 100%;
  display: flex;
  text-transform: uppercase;
  padding: 10px;
}


.filter-panel {
  width: 400px;
  display: block;
  border: 2px solid white;
  height: 400px;
}

.search-panel {
  width: 400px;
  display: block;
  position: relative;
  overflow: auto;
}


</style>