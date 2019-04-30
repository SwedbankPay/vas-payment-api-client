<template>
  <div class="container">
    <CardItem/>
    <PaymentList/>
  </div>
</template>

<script>
import CardItem from '../components/CardItem'
import PaymentList from '../components/PaymentList'
export default {
  name: 'PaymentInstrument',
  components: { PaymentList, CardItem }
}
</script>

<style scoped>

</style>
