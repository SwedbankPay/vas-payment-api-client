<template>
  <div>
    <section class="panel panel-brand">
      <header>
        <h2 class="panel-title">Payment instrument</h2>
        <p class="panel-sub-title">{{item.type}}</p>
      </header>
      <div class="panel-body">
        <div class="container">
          <div class="row">
            <div class="col">

              <div class="card card-light">
                <div class="highlight">
                  <div class="media media-sm">
                    <div class="media-img">
                      <i class="material-icons text-muted">payment</i>
                    </div>
                    <div class="media-body">
                      <h4 class="text-muted">{{item.type}}</h4>
                      <p>
                        <small>{{item.pan}}</small>
                      </p>
                    </div>
                  </div>
                </div>
                <div class="card-body">
                  <p>Card ID: {{item.id}}</p>
                </div>
              </div>

            </div>
            <div class="col text-left">
              <p>External ID: {{item.externalAccountId}}</p>
              <p>CVC: {{item.cvc}}</p>
              <p>Balance: {{formatNumber(item.balance / 100)}}<br>
                <small>last synced: {{formatDate(item.lastBalanceSync)}}</small></p>
            </div>

            <div class="col">
              <div class="row">
                <button class="btn btn-link " @click="updateItem(item)" disabled>
                  <i class="material-icons">build</i>
                  <span>Edit card</span>
                </button>
              </div>
              <div class="row">
                <DeleteCardDialog :payment-instrument="item"/>
              </div>
              <div class="row">
                <InvokeBalanceSheet :payment-instrument="item"/>
              </div>
              <div class="row">
                <InitiatePaymentSheet :payment-instrument="item"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { paymentInstrumentService } from './rest-resource'
import { formatDate, formatNumber } from '../utils/creditcard-util'
import InitiatePaymentSheet from './InitiatePaymentSheet'
import InvokeBalanceSheet from './InvokeBalanceSheet'
import DeleteCardDialog from './DeleteCardDialog'

export default {
  name: 'CardItem',
  components: { DeleteCardDialog, InvokeBalanceSheet, InitiatePaymentSheet },
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      item: {}
    }
  },
  created: function () {
    this.getItem()
  },
  mounted () {
    this.$root.$on('balance-successful', (res) => {
      this.item.balance = res.balance
      this.item.lastBalanceSync = new Date().toISOString()
    })
  },
  methods: {
    getItem () {
      paymentInstrumentService.getPaymentInstrument(this.$route.params.id).then(res => {
        this.item = res.data
      })
    },
    updateItem (item) {
      paymentInstrumentService.updatePaymentInstrument(item).then(res => {
        this.item = res.data
      })
    },
    formatNumber (number) {
      return formatNumber(number)
    },
    formatDate (date) {
      return formatDate(date)
    }
  }
}
</script>

<style scoped>

</style>
