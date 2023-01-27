<template>
  <description></description>
  <div class="viewer">
    <router-view/>
  </div>
</template>

<script>
import Description from "@/views/Description";
export default {
  name: "AboutProject",
  components:{Description}
}
</script>

<style scoped>
.viewer{
  justify-content: center;
  text-align: center;
}
form{
  text-transform: uppercase;
  display: grid;
}
.success{
  color: green;
}
.danger{
  color: red;
}
</style>