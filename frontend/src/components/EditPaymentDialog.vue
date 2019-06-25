<template>
<div>
  <div class="dialog" :id="`edit-payment-dialog${payment.paymentId}`">
    <section>
      <header>
        <h5>Update {{payment.transactionType}} ({{payment.paymentId}})</h5>
        <a href="#" class="dialog-close">
          <i class="material-icons">close</i>
        </a>
      </header>
      <div class="dialog-body">
        <div class="form-group">
          <label for="input-id-3">Amount</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
            <input type="number" class="form-control" id="input-id-3"
                   :value="this.payment.amount / 100"
                   placeholder="Rounded to nearest $" disabled>
            <span class="input-group-addon">.00</span>
          </div>
        </div>
        <button class="btn btn-link" type="button" :disabled="hideCapture" v-on:click="capture">
          <i class="material-icons">check_circle</i>
          <span>Capture</span>
        </button>
        <button class="btn btn-link" type="button" :disabled="hideCancel" v-on:click="cancel">
          <i class="material-icons">stop</i>
          <span>Cancel</span>
        </button>
        <button class="btn btn-link" type="button" :disabled="hideReversal" v-on:click="reversal">
          <i class="material-icons">delete</i>
          <span>Reversal</span>
        </button>
      </div>
      <footer>
        <button class="btn btn-secondary" type="button" :data-dialog-close="`edit-payment-dialog${payment.paymentId}`">Cancel</button>
      </footer>
    </section>
  </div>
  <button class="btn btn-link" type="button" :data-dialog-open="`edit-payment-dialog${payment.paymentId}`">
    <i class="material-icons">build</i>
    <span>Edit</span>
  </button>
</div>
</template>

<script>
import { paymentOperationService } from './rest-resource'

import { toastError, formatNumber } from '../utils/creditcard-util'

export default {
  name: 'EditPaymentDialog',
  props: {
    payment: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  computed: {
    hideCancel () {
      return this.payment.state !== 'OK' || this.payment.transactionType !== 'Authorize'
    },
    hideCapture () {
      return this.payment.state !== 'OK' || this.payment.transactionType !== 'Authorize'
    },
    hideReversal () {
      return this.payment.state !== 'OK' ||
        (this.payment.transactionType === 'Reversal' ||
          this.payment.transactionType === 'Cancel' ||
          this.payment.transactionType === 'Authorize')
    }
  },
  methods: {
    capture () {
      paymentOperationService.capture(this.$route.params.id, this.payment.paymentId).then(res => {
        this.handleOkPayment(res.data)
      }).catch((error) => {
        this.errorHandler(error)
      })
    },
    cancel () {
      paymentOperationService.cancel(this.$route.params.id, this.payment.paymentId).then(res => {
        this.handleOkPayment(res.data)
      }).catch((error) => {
        this.errorHandler(error)
      })
    },
    reversal () {
      paymentOperationService.reversal(this.$route.params.id, this.payment.paymentId).then(res => {
        this.handleOkPayment(res.data)
      }).catch((error) => {
        this.errorHandler(error)
      })
    },
    formatNumber (number) {
      formatNumber(number)
    },
    errorHandler (error) {
      toastError(error)
    },
    handleOkPayment (result) {
      this.$root.$emit('payment-successful', result)
      px.dialog.close(`edit-payment-dialog${this.payment.paymentId}`)
    }
  }
}
</script>

<style scoped>

</style>
