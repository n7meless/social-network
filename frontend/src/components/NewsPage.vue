<template>
  <h1>hello</h1>
  <div v-for="newsPost in news" :newsPost="newsPost" :key="newsPost.id">

  </div>

</template>


<script>
import axios from "axios";

export default {
  name: "NewsPage",
  data() {
    return {
      news: []
    }
  },
  mounted() {
    this.get();
  },
  methods: {
    async get() {
      const response = await axios.get("https://newsapi.org/v2/everything?q=tesla&from=2022-12-19&sortBy=publishedAt&apiKey=07b836b3f8904fb882e4e49673151519")

      this.news = response;
      console.log(this.news)
    }
  }
}
</script>

<style scoped>

</style>