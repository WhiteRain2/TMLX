<template>
  <div>{{ name }}</div>
  <div>{{ rating }}</div>
  <router-view></router-view>
</template>

<script>
import $ from 'jquery';
import { ref } from '@vue/reactivity';

export default {
  name: "App",
  setup: () => {
    let name = ref("");
    let rating = ref("");

    $.ajax({
      url: "http://127.0.0.1:3000/pk/getbotinfo/",
      type: "get",
      success: resp => {
        name.value = resp.name;
        rating.value = resp.rating;
      }
    });
    return {
      name, 
      rating
    }
  }
}
</script>

<style>
</style>
