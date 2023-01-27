<template>
  <input :value="modelValue" @input="updateInput" placeholder="SEARCH" class="custom-input">
</template>

<script>
export default {
  name: "SearchInput",
  props:{
    modelValue: [String, Number]
  },
  methods:{
    updateInput(event){
      this.$emit('update:modelValue', event.target.value)
    }
  }
}
</script>

<style scoped>
.custom-input{
  height: 40px;
  outline: none;
  border: 0px;
  border-bottom: 2px solid #42b983;
  width: 100%;
  font-size: 20px;
  padding: 0 10px;
  color: white;
  background: #343333;;
}
.custom-input:hover{

}

</style>