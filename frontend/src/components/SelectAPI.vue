<template>
  <form>
    <div class="radio">
      <input 
        type="radio"
        id="multi-pay-radio"
        value="multipay"
        v-model="api" 
        >
      <label for="multi-pay-radio">MultiPay API</label>
    </div>
    <div class="radio">
      <input 
        type="radio"
        id="public-pay-radio"
        value="payment-api"
        v-model="api"
        >
      <label for="public-pay-radio">Public payment API</label>
    </div>
  </form>
</template>

<script>
export default {
  name: 'selectAPI',
  data () {
    return {
      api: sessionStorage.getItem('api') ? sessionStorage.getItem('api') : 'payment-api'
    }
  },
  watch: {
    api: function () {
      this.$emit('change-api', this.api)
    }
  }
}
</script>

<style>

</style>
