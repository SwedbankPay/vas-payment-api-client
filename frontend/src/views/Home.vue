<template>
  <div class="home">
    <CardList/>
  </div>
</template>

<script>
// @ is an alias to /src
import CardList from '@/components/CardList.vue'

export default {
  name: 'home',
  components: {
    CardList
  }
}
</script>
