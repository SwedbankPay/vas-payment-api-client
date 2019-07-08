<template>
  <div>
    <div class="sheet" :id="`view-order-details-dialog${order.orderId}`">
      <section>
        <header>
          <h5>View Order details</h5>
          <a href="#" class="sheet-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="sheet-body">
          <table class="table table-description">
            <thead>
              <tr>
                <th></th>
                <th>Customer Info</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Order ID:</td>
                <td>{{order.orderId}}</td>
              </tr>
              <tr v-if="customerType==='corporate'">
                <td>Company Name:</td>
                <td>{{order.corporateCustomerIdentifier.companyName}}</td>
              </tr>
              <tr>
                <td>Contact name:</td>
                <td>{{customerInfo.name}}</td>
              </tr>
              <tr>
                <td>Contact email:</td>
                <td>{{customerInfo.email}}</td>
              </tr>
              <tr>
                <td>Contact phone:</td>
                <td>{{customerInfo.phone}}</td>
              </tr>
              <tr>
                <td>Billing Address</td>
                <td>{{customerInfo.address.billingStreetAddress + ', ' + customerInfo.address.billingPostalCode + ', ' + customerInfo.address.billingCity}}</td>
              </tr>
            </tbody>
          </table>
          <table class="table table-description">
            <thead>
              <tr>
                <th></th>
                <th>Products</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="product in order.products" :key="product.productId">
                <td>{{product.name}}</td>
                <td>{{product.quantity}}</td>
                <td>{{product.amount/100}} {{order.currency}}</td>
              </tr>
              <tr>
                <td>Total: </td>
                <td></td>
                <td>{{order.amount/100}} {{order.currency}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
    <button class="btn btn-primary" type="button" :data-sheet-open="`view-order-details-dialog${order.orderId}`">
      <i class="material-icons">details</i>
      <span>Details</span>
    </button>
  </div>
</template>

<script>
export default {
  name: 'ViewOrderDetailsDialog',
  props: {
    order: Object
  },
  data () {
    return {
      customerInfo: {},
      customerType: ''
    }
  },
  created () {
    this.populateCustomerInfo()
  },
  methods: {
    populateCustomerInfo () {
      this.customerType = this.order.privateCustomerIdentifier ? 'private' : 'corporate'
      let prefix = this.customerType
      let cust = prefix === 'private' ? 'customer' : 'contact'
      this.customerInfo.name = this.order[prefix + 'CustomerIdentifier'][cust + 'FirstName'] + ' ' + this.order[prefix + 'CustomerIdentifier'][cust + 'LastName']
      this.customerInfo.email = this.order[prefix + 'CustomerIdentifier'].contactEmail
      this.customerInfo.phone = this.order[prefix + 'CustomerIdentifier'].contactPhoneCountryCode + ' ' + this.order[prefix + 'CustomerIdentifier'].contactPhone
      this.customerInfo.address = this.order[prefix + 'CustomerIdentifier'].address
    }
  }
}
</script>

<style>

</style>
