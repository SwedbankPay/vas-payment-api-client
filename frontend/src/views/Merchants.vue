<template>
  <div class="container">
    <MerchantList :selectedApi="selectedApi"/>
  </div>
</template>

<script>
import MerchantList from '../components/MerchantList'
export default {
  name: 'Merchants',
  props: { selectedApi: String },
  components: { MerchantList }
}
</script>

<style scoped>

</style>
