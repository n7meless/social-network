<template>
  <div class="msg-panel">
    <div v-if="$route.params.id>0">
      <form class="msg-form">
        <div  v-for="item in received_messages" :key="item">
          <div>{{ item.text }}</div>
        </div>
      </form>
      <div class="send-panel">
        <input placeholder="MESSAGE" v-model="send_message" autofocus class="scroll">
        <button class="mybtn" type="submit" @click.prevent="send">
          SEND
        </button>
      </div>
    </div>
    <div class="select-message" v-else>
      <h2>SELECT USER TO START CHAT</h2>
      <img src="../assets/vintage.png" height="350" alt="123">
    </div>
  </div>
</template>

<script>
import SearchInput from "@/components/SearchInput";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import axios from "axios";
export default {
  name: "MessagePanel",
  components: {SearchInput},
  props:{
    connected:{
      type:Boolean,
      required:true,
    }
  },
  data() {
    return {
      msgArr: [],
      send_message: null,
      loaded:true,
      stompClient: '',
      received_messages:[],
      connected: false,
    }
  },
  mounted() {
    this.connect()
  },
  methods: {
    send() {
      console.log(this.received_messages)
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          // date: Date.now(),
          first_account: 1,
          second_account: 2,
          text: this.send_message
        };
        this.msgArr.push(this.send_message)
        this.stompClient.send("/app/chat", JSON.stringify(msg), {});
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:4000/chat");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            console.log(frame);
            this.stompClient.subscribe("/topic/messages", message => {
              this.received_messages.push(JSON.parse(message.body));
              console.log(this.received_messages)
            });
          },
          error => {
            console.log(error);
            this.connected = false;
          }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },
  }
}
</script>

<style scoped>
.select-message{
  justify-content: center;
  height: 100%;
  text-align: center;
}
*.scroll {
  -webkit-user-select: none;
  -moz-user-select: none

}

.msg-form {
  height:410px;
  align-content: end;
  width: 400px;
  border-bottom: 1px solid white;
  background: #343333;
}

.scroll {
  width: 100%;
  outline: none;
  background: #343333;
  color: white;
  height: 50px;
  font-size: 15px;
  padding: 0 10px;
  resize: none;
  border: none;
}

.send-panel {
  display: flex;
}

.mybtn {
  height: 50px;
  width: 100px;
  border: none;
  cursor: pointer;
  background: white;
  color: black;
  font-size: 25px;
  padding: 0 5px;
}

.mybtn:hover {
  background: #42b983;
}

.scroll::-webkit-scrollbar {
  display: none;
}

.msg-panel {
  box-sizing: border-box;
  height: 500px;
  width: 400px;
}
</style>