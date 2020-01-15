<template>
  <section class="panel panel-brand">
    <table class="table table-hover table-striped">
      <thead>
      <tr>
        <td>ID</td>
        <td>Payment type</td>
        <td>Amount</td>
        <td>Description</td>
        <td>State</td>
        <td>Created</td>
        <td>Actions</td>
      </tr>
      </thead>

      <tbody>
      <tr v-for="item in items" :key="item.paymentId">
        <td>{{ item.paymentId }}</td>
        <td v-html="formatTransactionType(item.transactionType)"></td>
        <td>{{ formatNumber(item.amount / 100) }} {{getExternalResponseAsJson(item).currency}}</td>
        <td>{{getExternalResponseAsJson(item).description}}</td>
        <td>{{item.state}}</td>
        <td>{{ formatDate(item.created) }}</td>
        <td>
          <edit-payment-dialog :payment="item"></edit-payment-dialog>
        </td>
      </tr>
      </tbody>
    </table>
  </section>
</template>

<script>
import { paymentOperationService } from './rest-resource'
import { maskPan, formatNumber, formatDate } from '../utils/creditcard-util'
import EditPaymentDialog from './EditPaymentDialog'

export default {
  components: { EditPaymentDialog },
  name: 'PaymentList',
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
  },
  mounted () {
    this.$root.$on('payment-successful', () => {
      this.fetchItems()
    })
  },
  methods: {
    formatTransactionType (txType) {
      let txTypeString = String(txType)
      switch (txTypeString) {
        case 'Authorize': return '<span class="badge badge-default">' + txTypeString + '</span>'
        case 'Capture': return '<span class="badge badge-blue">' + txTypeString + '</span>'
        case 'Purchase': return '<span class="badge badge-blue">' + txTypeString + '</span>'
        case 'Deposit': return '<span class="badge badge-brand">' + txTypeString + '</span>'
        case 'Cancel': return '<span class="badge badge-yellow">' + txTypeString + '</span>'
        case 'Reversal': return '<span class="badge badge-yellow">' + txTypeString + '</span>'
        default: return '<span class="badge badge-blue">' + txTypeString + '</span>'
      }
    },
    fetchItems () {
      paymentOperationService.listPayments(this.$route.params.id).then(res => {
        this.items = res.data
      })
    },
    maskPan (pan) {
      return maskPan(pan)
    },
    formatNumber (number) {
      return formatNumber(number)
    },
    formatDate (date) {
      return formatDate(date)
    },
    getExternalResponseAsJson (payment) {
      return JSON.parse(payment.externalResponse)
    }
  }
}
</script>

<style scoped>

</style>
