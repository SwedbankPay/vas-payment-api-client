<template>
  <div class="container">
    <div class="cards" v-for="item in items" :key="item.orderId">
      <div class="card card-default">
        <div class="highlight">
          <div class="media media-sm">
            <div class="media-img">
              <i class="material-icons text-muted">shopping_cart</i>
            </div>
            <div class="media-body">
              <h4 class="text-muted">Order ID: {{item.orderId}}</h4>
              <p>
                <small><strong>Customer Name:</strong> <br>{{getName(item)}} <br></small>
                <small><strong>Amount:</strong> {{item.amount/100}} {{item.currency}}</small>
              </p>
            </div>
          </div>
        </div>
        <div class="card-body">
          <small>{{item.description}}</small>
          <br>
          <small v-if="item.products.length > 0">Products:</small>
          <ul>
            <li v-for="product in item.products" :key="product.id"><small>{{product.name}}</small></li>
          </ul>
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
      items: [
        {
          orderId: 1,
          additionalData: '',
          amount: 4000,
          corporateCustomerIdentifier: {},
          currency: 'NOK',
          privateCustomerIdentifier: {
            contactEmail: 'test@test.com',
            contactPhone: '22334455',
            contactPhoneCountryCode: '47',
            customerFirstName: 'Ola',
            customerLastName: 'Nordmann',
            customerLanguagecode: 'NO',
            ssn: '01011979123456',
            address: {
              billingStreetAddress: '2028 Bancroft Way',
              billingPostalCode: '94704',
              billingCity: 'Berkeley'
            }
          },
          description: 'Test order',
          merchant: {},
          paymentContractId: '',
          paymentExpireDateTime: '',
          paymentMethods: '', // ALL, INVOICE, ONLINE
          paymentOrderRef: '',
          paymentTransactionRef: '',
          paymentTransmissionDateTime: '',
          preliminaryInvoiceFee: 0.0,
          preliminaryInvoiceTax: 0.0,
          products: [
            {
              name: 'Chocolate',
              amount: '4000',
              quantity: 1
            }
          ],
          repeat: false,
          shippingInformation: {},
          stan: ''
        }
      ]
    }
  },
  mounted () {
    this.$root.$on('order-cancel-event', (id) => {
      console.log('Got new "order-cancel-event" event')
      this.items = this.items.filter(item => item.orderId !== id) // remove order from list
    })
  },
  methods: {
    getName (order) {
      if (order.privateCustomerIdentifier) return order.privateCustomerIdentifier.customerFirstName + ' ' + order.privateCustomerIdentifier.customerLastName
      else return order.corporateCustomerIdentifer.companyName
    },
    fetchItem (id) {
      multipayService.getOrder('systemTest', id).then(res => {
        this.items.push(res.data)
      })
    }
  }
}
</script>

<style>

</style>
