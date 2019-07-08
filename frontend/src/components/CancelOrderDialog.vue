<template>
  <div>
    <div class="dialog" :id="`cancel-order-dialog${order.orderId}`">
      <section>
        <header>
          <h5>Cancel order ID: {{order.orderId}}?</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="dialog-body">
          <p>Are you sure?</p>
        </div>
        <footer>
          <button class="btn btn-secondary" type="button" :data-dialog-close="`cancel-order-dialog${order.orderId}`">Abort</button>
          <button class="btn btn-danger" type="button" v-on:click="cancelOrder" ><i class="material-icons ">block</i> Cancel Order</button>
        </footer>
      </section>
    </div>
    <button class="btn btn-danger" type="button" :data-dialog-open="`cancel-order-dialog${order.orderId}`">
      <i class="material-icons" >block</i>
      <span>Cancel</span>
    </button>
  </div>
</template>

<script>
import { multipayService } from './rest-resource'
import { toastError } from '@/utils/creditcard-util'
export default {
  name: 'CancelOrderDialog',
  props: {
    order: Object
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    cancelOrder () {
      multipayService.cancelOrder('Systemtest', this.order.orderId).then(res => {
        px.toast({ html: 'Successfully cancelled order with ID: ' + this.order.orderId })
        this.$root.$emit('order-cancel-event', this.order.orderId)
        px.dialog.close(`cancel-order-dialog${this.order.orderId}`)
      }).catch(error => {
        toastError(error)
      })
    }
  }
}
</script>

<style>

</style>
