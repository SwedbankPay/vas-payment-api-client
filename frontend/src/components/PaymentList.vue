<template>
  <section class="panel panel-brand">
    <table class="table table-hover">
      <thead>
      <tr>
        <td>ID</td>
        <td>Payment type</td>
        <td>Amount</td>
        <td>State</td>
        <td>Created</td>
        <td>Actions</td>
      </tr>
      </thead>

      <tbody>
      <tr v-for="item in items" :key="item.paymentId">
        <td>{{ item.paymentId }}</td>
        <td>{{ item.transactionType }}</td>
        <td>{{ formatNumber(item.amount) }}</td>
        <td>{{ item.state }}</td>
        <td>{{ item.created }}</td>
        <td>
          <div class="action-list anchor-bottom-left">
            <i class="material-icons">more_vert</i>
            <div class="action-menu">
              <a href="#"><i class="material-icons">bookmark</i>Add bookmark</a>
              <a href="#"><i class="material-icons">business_center</i>Add client</a>
              <a href="#"><i class="material-icons">add_circle</i>Add document</a>
              <a href="#"><i class="material-icons">person_add</i>Add user</a>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </section>
</template>

<script>
import { paymentOperationService } from './rest-resource'
import { maskPan, formatNumber } from '../utils/creditcard-util'

export default {
  name: 'PaymentList',
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
  },
  methods: {
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
    }
  }
}
</script>

<style scoped>

</style>
