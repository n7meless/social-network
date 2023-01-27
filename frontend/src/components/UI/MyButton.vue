<template>
  <button class="btn">
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: "MyButton"
}
</script>

<style scoped>
.btn{
  margin: 10px;
  outline: 0;
  font-size: 20px;
  padding: 0 5px;
  height: 45px;
  border: 2px solid black;

}
.btn:hover{
  background: #42b983;
  cursor: pointer;
}
</style>