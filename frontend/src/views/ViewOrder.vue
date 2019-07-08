<template>
  <div class="container">
    <OrderList></OrderList>
  </div>
</template>

<script>
import OrderList from '@/components/OrderList'
export default {
  name: 'ViewOrder',
  components: { OrderList },
  data () {
    return {
      items: []
    }
  }
}
</script>

<style>

</style>
