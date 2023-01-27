<template>
  <input :value="modelValue" @input="updateInput" type="text" class="input">
</template>

<script>
export default {
  name: "MyInput",
  props: {modelValue: [String, Number]},
  methods:{
    updateInput(event){
      this.$emit('update:modelValue', event.target.value)
    }
  }
}
</script>

<style scoped>
.input {
  margin: 10px;
  outline: 0;
  font-size: 20px;
  padding: 0 5px;
  height: 45px;
  border: 2px solid black;
}
.input:hover{
  border-color: #42b983;
}
.input:focus{
  border-color: #42b983;

}
</style>