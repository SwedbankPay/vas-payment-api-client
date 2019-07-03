<template>
  <div class="home">
    <CardList/>
    <p> Selected api is: {{selectedApi}}</p>
  </div>
</template>

<script>
// @ is an alias to /src
import CardList from '@/components/CardList.vue'

export default {
  name: 'home',
  components: {
    CardList
  },
  props: {
    'selectedApi': String
  }
}
</script>
