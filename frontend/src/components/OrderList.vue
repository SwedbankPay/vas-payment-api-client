<template>
  <div class="container">
    <div class="row justify-content-md-center">
      <div class="col-md-3 form-group">
        <div class="input-group">
          <input
            class="form-control"
            type="text"
            v-model="searchId"
            placeholder="Order ID"
            @submit.prevent="fetchItem(searchId)"
            >
          <button class="input-group-addon btn" type="submit" @click.prevent="fetchItem(searchId)"><i class="material-icons">search</i></button>
          <button class="input-group-addon btn" type="button" @click.prevent="fetchItems()" data-tooltip="Client functionality only, gets locally stored orders" data-tooltip-position="right"><i class="material-icons">search</i> Get all</button>
        </div>
      </div>
    </div>
    <div class="cards" v-for="item in items" :key="item.orderId">
      <div class="card card-default">
        <div class="highlight">
          <div class="media media-sm">
            <div class="media-img">
              <i class="material-icons text-muted">shopping_cart</i>
            </div>
            <div class="media-body">
              <h4 class="text-muted">Order ID: {{item.orderId}}</h4>
            </div>
          </div>
        </div>
        <div class="card-body">
          <p>
            <small><strong>Created: </strong>{{new Date(item.paymentTransmissionDateTime).toLocaleDateString()}}</small> <br>
            <small><strong>Customer Name:</strong> <br>{{getName(item)}} <br></small>
            <small><strong>Amount:</strong> {{item.amount/100}} {{item.currency}}</small>
          </p>
          <div class="row">
            <div class="col">
              <view-order-details-dialog :order="item"></view-order-details-dialog>
            </div>
            <div class="col">
              <cancel-order-dialog :order="item"></cancel-order-dialog>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { multipayService } from './rest-resource'
import CancelOrderDialog from '@/components/CancelOrderDialog'
import ViewOrderDetailsDialog from '@/components/ViewOrderDetailsDialog'

export default {
  name: 'OrderList',
  components: {
    CancelOrderDialog,
    ViewOrderDetailsDialog
  },
  data () {
    return {
      searchId: '',
      items: []
    }
  },
  mounted () {
    this.$root.$on('order-cancel-event', (id) => {
      console.log('Got new "order-cancel-event" event')
      this.items = this.items.filter(item => item.orderId !== id) // remove order from list
    })
    px.sheet.init()
  },
  methods: {
    getName (order) {
      if (order.privateCustomerIdentifier) return order.privateCustomerIdentifier.customerFirstName + ' ' + order.privateCustomerIdentifier.customerLastName
      else return order.corporateCustomerIdentifier && order.corporateCustomerIdentifier.companyName
    },
    fetchItem (id) {
      console.log('trying to fetch item with id: ' + id)
      if (id === '') return
      multipayService.getOrder('Systemtest', id).then(res => {
        for (let item of this.items) { // Replace existing order if present
          if (item.orderId === id) {
            item = res.data
            return
          }
        }
        this.items.push(res.data)
      })
    },
    fetchItems () {
      console.log('fetching all local orders (CLIENT ONLY - NO API CALL)')
      multipayService.listOrders('Systemtest').then(res => {
        if (res.data.length > 0) {
          this.items = res.data
        }
      })
    }
  }
}
</script>

<style>

</style>
