<template>
  <div>
    <section class="panel panel-brand">
      <header>
        <h2 class="panel-title">Payment instrument</h2>
        <p class="panel-sub-title">{{item.type}}</p>
      </header>
      <div class="panel-body">
        <p>Card ID: {{item.id}}</p>
        <p>PAN: {{item.pan}}</p>
        <p>CVC: {{item.cvc}}</p>
        <button class="btn btn-primary" @click="updateItem(item)" disabled>Edit</button>
        <button class="btn btn-danger" @click="deleteItem(item.id)" disabled>Delete</button>
      </div>

      <div class="sheet" id="payment-operation-sheet">
        <section>
          <a href="#" class="sheet-close">
            <i class="material-icons">close</i>
          </a>
          <h2>Invoke a payment operation on card</h2>

          <hr/>
          <form v-on:submit="invokePaymentOperation()">
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
                  <option>Deposit</option>
                  <option>Authorize</option>
                  <option>Purchase</option>
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
      <button class="btn btn-primary" type="button" v-on:click="listMerchants"
              data-sheet-open="payment-operation-sheet">Initiate payment
      </button>
    </section>
  </div>
</template>

<script>
import { paymentInstrumentService, paymentOperationService, merchantService } from './rest-resource'
import { formatNumber } from '../utils/creditcard-util'

export default {
  name: 'CardItem',
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      item: {},
      merchantList: [],
      paymentRequest: {
        merchantId: '',
        amount: 1337,
        description: ''
      },
      selectedPaymentOperation: ''
    }
  },
  created: function () {
    this.getItem()
  },
  methods: {
    getItem () {
      paymentInstrumentService.getPaymentInstrument(this.$route.params.id).then(res => {
        this.item = res.data
      })
    },
    deleteItem (id) {
      paymentInstrumentService.deletePaymentInstrument(id).then(res => {
        this.$router.push({ name: 'CardList' })
      })
    },
    updateItem (item) {
      paymentInstrumentService.updatePaymentInstrument(item).then(res => {
        this.item = res.data
      })
    },
    listMerchants () {
      merchantService.listMerchants().then(res => {
        this.merchantList = res.data
        // TODO:: refresh transaction list
      })
    },
    invokePaymentOperation () {
      console.log('invokePaymentOperation invoked!')
      if (this.selectedPaymentOperation === 'Authorize') {
        paymentOperationService.authorize(this.$route.params.id, this.paymentRequest).then(res => {
          console.log(res.data)
        // TODO:: refresh transaction list
        })
      } else if (this.selectedPaymentOperation === 'Purchase') {
        paymentOperationService.purchase(this.$route.params.id, this.paymentRequest).then(res => {
          console.log(res.data)
          // TODO:: refresh transaction list
        })
      } else if (this.selectedPaymentOperation === 'Deposit') {
        paymentOperationService.deposit(this.$route.params.id, this.paymentRequest).then(res => {
          console.log(res.data)
          // TODO:: refresh transaction list
        })
      }
    },
    formatNumber (number) {
      return formatNumber(number)
    }
  }
}
</script>

<style scoped>

</style>
