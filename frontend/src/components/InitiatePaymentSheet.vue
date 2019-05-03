<template>
  <div>
    <div class="sheet" id="payment-operation-sheet">
      <section>
        <a href="#" class="sheet-close">
          <i class="material-icons">close</i>
        </a>
        <h2>Invoke a payment operation on card</h2>

        <hr/>
        <form v-on:submit.prevent="invokePaymentOperation()">
          <div class="form-group"><label for="merchant">Merchant</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
              <select id="merchant" class="form-control" v-model="paymentRequest.merchantId">
                <option disabled value="">Please select one</option>
                <option v-for="merchant in merchantList" :key="merchant.id" v-bind:value="merchant.id">
                  {{merchant.merchantName}}
                </option>
              </select>
            </div>
          </div>
          <div class="form-group"><label for="operation">Operation</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">label</i></span>
              <select id="operation" class="form-control" v-model="selectedPaymentOperation">
                <option disabled value="">Please select one</option>
                <option>Authorize</option>
                <option>Purchase</option>
                <option v-if="this.paymentInstrument.type === 'Credit card'">Credit</option>
                <option v-if="this.paymentInstrument.type === 'Prepaid card'">Deposit</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="input-id-3">Amount</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
              <input type="number" class="form-control" id="input-id-3"
                     v-model="paymentRequest.amount"
                     placeholder="Rounded to nearest $" value>
              <span class="input-group-addon">.00</span>
            </div>
          </div>
          <div class="form-group"><label for="description">Description</label>
            <div class="input-group"><input type="text" class="form-control" id="description"
                                            v-model="paymentRequest.description"
                                            placeholder="Description"/></div>
          </div>
          <div class="form-group">
          </div>
          <button class="btn btn-primary" type="submit">Send</button>
        </form>
      </section>
    </div>
    <button class="btn btn-link" type="button" v-on:click="listMerchants"
            data-sheet-open="payment-operation-sheet">
      <i class="material-icons">shopping_cart</i>
      <span>Initiate payment</span>
    </button>
  </div>
</template>

<script>

import { paymentOperationService, merchantService } from './rest-resource'
import { toastError } from '../utils/creditcard-util'

export default {
  name: 'InitiatePaymentSheet',
  props: {
    paymentInstrument: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data () {
    return {
      merchantList: [],
      paymentRequest: {
        merchantId: '',
        amount: 1337,
        description: ''
      },
      selectedPaymentOperation: ''
    }
  },
  mounted () {
    px.sheet.init()
  },
  methods: {
    listMerchants () {
      merchantService.listMerchants().then(res => {
        this.merchantList = res.data
      })
    },
    invokePaymentOperation () {
      console.log('invokePaymentOperation invoked with: ' + this.selectedPaymentOperation)
      if (this.selectedPaymentOperation === 'Authorize') {
        paymentOperationService.authorize(this.$route.params.id, this.paymentRequest).then(res => {
          this.handleOkPayment(res.data)
        }).catch((error) => {
          this.errorHandler(error)
        })
      } else if (this.selectedPaymentOperation === 'Purchase') {
        paymentOperationService.purchase(this.$route.params.id, this.paymentRequest).then(res => {
          this.handleOkPayment(res.data)
        }).catch((error) => {
          this.errorHandler(error)
        })
      } else if (this.selectedPaymentOperation === 'Deposit') {
        paymentOperationService.deposit(this.$route.params.id, this.paymentRequest).then(res => {
          this.handleOkPayment(res.data)
        }).catch((error) => {
          this.errorHandler(error)
        })
      } else if (this.selectedPaymentOperation === 'Credit') {
        paymentOperationService.credit(this.$route.params.id, this.paymentRequest).then(res => {
          this.handleOkPayment(res.data)
        }).catch((error) => {
          this.errorHandler(error)
        })
      }
    },
    errorHandler (error) {
      toastError(error)
    },
    handleOkPayment (result) {
      px.sheet.close('payment-operation-sheet')
      this.$root.$emit('payment-successful', result)
    }
  }
}
</script>

<style scoped>

</style>
