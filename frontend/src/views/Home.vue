<template>
  <div class="home">
    <CardList/>
    <AddCustomerInfo/>
    <p> Selected api is: {{selectedApi}}</p>
  </div>
</template>

<script>
// @ is an alias to /src
import CardList from '@/components/CardList.vue'
import AddCustomerInfo from '@/components/AddCustomerInfo.vue'

export default {
  name: 'home',
  components: {
    CardList,
    AddCustomerInfo
  },
  props: ["selectedApi"]
}
</script>
