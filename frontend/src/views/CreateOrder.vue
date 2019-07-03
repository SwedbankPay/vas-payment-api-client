<template>
  <div class="container">
    <h4>Create new Order</h4>
    <div v-if="customer && customerType === 'private'">
      <label for="name">Customer name</label>
      <div class="form-control" id="name">{{customer.customerFirstName + ' ' + customer.customerLastName}}</div>
    </div>
    <AddCustomerInfo
      @add-customer-successful="updateCustomerData"
      :message="message"
      style="margin-bottom: 2rem"
      />
    <label for="description">Order Description</label>
    <div class="input-group">
      <span class="input-group-addon"><i class="material-icons">description</i></span>
      <input
        class="form-control"
        id="description"
        v-model="paymentRequest.description"
        placeholder="Order description"
        >
    </div>
    <label for="amount">Amount</label>
    <div class="input-group">
      <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
      <input
        class="form-control order-input"
        id="amount"
        v-model="initAmount"
        >
        <span class="input-group-addon">,</span>
      <input
        class="form-control col-md-1 order-input"
        v-model="initAmountCents"
        >
    </div>
    <div class="form-group"><label for="merchant">Merchant</label>
      <div class="input-group">
        <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
        <select id="merchant" class="form-control" v-model="paymentRequest.merchant">
          <option disabled value>Please select one</option>
          <option v-for="merchant in merchantList" :key="merchant.agreementId" v-bind:value="merchant">
            {{merchant.merchantName}}
          </option>
        </select>
      </div>
    </div>
    <div v-if="customer" class="checkbox">
      <input type="checkbox" id="shipping" v-model="copyShippingInfo" />
      <label for="shipping">Ship to billing address?</label>
    </div>
    <div>
      <h6 style="margin-top: 2rem">Shipping Address Information</h6>
      <div class="row">
        <label class="col" for="shippingStreetAddressee">Street Addressee</label>
        <label class="col" for="shippingStreetAddress">Street Address</label>
      </div>
      <div class="input-group">
        <span class="input-group-addon">
          <i class="material-icons">account_box</i>
        </span>
        <input
          type="text"
          class="form-control"
          id="shippingStreetAddressee"
          v-model="paymentRequest.shippingInformation.shippingStreetAddressee"
          placeholder="John Doe"
        />
        <div class="col-md-1"></div>
        <span class="input-group-addon">
          <i class="material-icons">place</i>
        </span>
        <input
          type="text"
          class="form-control"
          id="shippingStreetAddress"
          v-model="paymentRequest.shippingInformation.shippingStreetAddress"
          placeholder="Drammensveien 1"
        />
      </div>
      <div class="row">
        <label for="shippingPostalCode" class="col-md-2">Postal Code</label>
        <label for="shippingCity" class="col">City</label>
        <label for="shippingCountryCode" class="col-md-2">Country</label>
      </div>
      <div class="input-group">
        <span class="input-group-addon">
          <i class="material-icons">location_city</i>
        </span>
        <input
          type="text"
          class="form-control col-md-2"
          id="shippingPostalCode"
          v-model="paymentRequest.shippingInformation.shippingPostalCode"
          placeholder="0271"
        />
        <input
          type="text"
          class="form-control"
          id="shippingCity"
          v-model="paymentRequest.shippingInformation.shippingCity"
          placeholder="Oslo"
        />

        <input
          type="text"
          class="form-control col-md-2"
          id="shippingCountryCode"
          v-model="paymentRequest.shippingInformation.shippingCountryCode"
          placeholder="NO"
        />
      </div>
    </div>
    <button
      class="btn btn-primary"
      type="button"
      v-on:click="createOrder"
      style="margin-top: 3rem"
      >Create order</button>
  </div>
</template>

<script>
import AddCustomerInfo from '@/components/AddCustomerInfo.vue'
import { merchantService } from '@/components/rest-resource.js'
const uuidV4 = require('uuid/v4')

export default {
  name: 'CreateOrder',
  components: {
    AddCustomerInfo
  },
  data () {
    return {
      paymentRequest: {
        additionalData: '',
        amount: 0,
        corporateCustomerIdentifier: {},
        privateCustomerIdentifier: {},
        description: '',
        merchant: {},
        paymentContractId: '',
        paymentExpireDateTime: '',
        paymentMethods: '', // ALL, INVOICE, ONLINE
        paymentOrderRef: '',
        paymentTransactionRef: '',
        paymentTransmissionDateTime: '',
        preliminaryInvoiceFee: 0.0,
        preliminaryInvoiceTax: 0.0,
        products: [],
        repeat: false,
        shippingInformation: {},
        stan: ''
      },
      customerType: '',
      customer: null,
      message: 'Add customer info',
      initAmount: 0,
      initAmountCents: 0,
      copyShippingInfo: false,
      merchantList: [],
      agreementId: ''
    }
  },
  mounted () {
    this.listMerchants();
  },
  methods: {
    updateCustomerData (customer, type) {
      if (type === 'corporate') {
        this.paymentRequest.corporateCustomerIdentifier = customer
      } else this.paymentRequest.privateCustomerIdentifier = customer
      this.customerType = type
      this.customer = customer
      this.message = 'Edit customer info'
    },
    listMerchants () {
      merchantService.listMerchants().then(res => {
        this.merchantList = res.data
      })
    },
    createOrder () {
      this.paymentRequest.paymentContractId = uuidV4()
      this.paymentRequest.paymentOrderRef = uuidV4()
      this.paymentRequest.paymentTransmissionDateTime = new Date().toLocaleString('en-US', { hour12: false })

      //send to backend
    }
  },
  watch: {
    initAmount () {
      this.paymentRequest.amount = parseInt(this.initAmount) * 100 + parseInt(this.initAmountCents)
    },
    initAmountCents () {
      this.paymentRequest.amount = parseInt(this.initAmount) * 100 + parseInt(this.initAmountCents)
    },
    copyShippingInfo () {
      this.paymentRequest.shippingInformation.shippingAddressee     = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingAddressee
      this.paymentRequest.shippingInformation.shippingCity          = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCity
      this.paymentRequest.shippingInformation.shippingCoAddress     = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCoAddress
      this.paymentRequest.shippingInformation.shippingCountryCode   = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCountryCode
      this.paymentRequest.shippingInformation.shippingPostalCode    = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingPostalCode
      this.paymentRequest.shippingInformation.shippingStreetAddress = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingStreetAddress
    }
  }

}
</script>

<style>
.order-input {
  text-align: right;
}
</style>
