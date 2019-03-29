<template>
  <div>
    <section class="panel panel-brand">
    <form novalidate data-validate>
      <div class="form-group">
        <label for="validation-name-1">Name</label>
        <div class="input-group">
          <input type="text" class="form-control" id="validation-name-1" placeholder="Enter your name" />
        </div>
        <div class="help-block">This one should be easy</div>
      </div>
      <div class="form-group">
        <label for="validation-email-1">Email</label>
        <div class="input-group">
          <input type="email" class="form-control" id="validation-email-1" placeholder="Enter your email" />
        </div>
        <div class="help-block">This one should be pretty easy too</div>
      </div>
      <button class="btn btn-primary" type="submit">Submit</button>
    </form>
    </section>
  </div>
</template>

<script>
import { paymentOperationService } from './rest-resource'

export default {
  name: 'PaymentOperation',
  data () {
    return {
      paymentRequest: {
        merchantId: '',
        amount: 0,
        description: ''
      }
    }
  },
  methods: {
    auth () {
      paymentOperationService.authorize(this.$route.params.id, this.paymentRequest).then(res => {
        this.item = res.data
        //TODO:: refresh transaction list
      })
    },
    purchase (id) {
      paymentOperationService.purchase(this.$route.params.id, this.paymentRequest).then(res => {
        this.item = res.data
        //TODO:: refresh transaction list
      })
    },
    deposit (id) {
      paymentOperationService.deposit(this.$route.params.id, this.paymentRequest).then(res => {
        this.item = res.data
        //TODO:: refresh transaction list
      })
    }
  }
}
</script>

<style scoped>

</style>
