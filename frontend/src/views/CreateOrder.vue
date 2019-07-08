<template>
  <div class="container">
    <h4>Create new Order</h4>
    <div class="row">
      <main class="mainContainer">
        <div v-if="customer && customerType === 'private'">
          <label for="name">Customer name</label>
          <div
            class="form-control"
            id="name"
          >{{customer.customerFirstName + ' ' + customer.customerLastName}}</div>
        </div>
        <AddCustomerInfo
          @add-customer-successful="updateCustomerData"
          :message="message"
          style="margin-bottom: 2rem"
        />
        <label for="description">Order Description</label>
        <div class="input-group">
          <span class="input-group-addon">
            <i class="material-icons">description</i>
          </span>
          <textarea
            class="form-control"
            id="description"
            v-model="paymentRequest.description"
            placeholder="Order description"
            rows="3"
          ></textarea>
        </div>
        <label for="products">Products</label>
        <table v-if="paymentRequest.products.length > 0" class="table table-condensed">
          <thead>
            <tr>
              <th scope="col" style="text-align: center">Product Name</th>
              <th scope="col" style="text-align: center">Quantity</th>
              <th scope="col" style="text-align: center">Price</th>
              <th scope="col" style="text-align: center"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="prod in paymentRequest.products" :key="prod.productId">
              <td>{{prod.name}}</td>
              <td>
                <span @click="(prod.quantity > 0) ? prod.quantity -= 1 : ''" :style="{ float: 'left', color: '#2da944' }">
                  <i class="material-icons">remove_circle</i>
                </span>
                {{prod.quantity}}
                <span  @click="prod.quantity += 1" :style="{ float: 'right', color: '#2da944' }">
                  <i class="material-icons">add_circle</i>
                </span>
              </td>
              <td>{{(prod.amount * prod.quantity)/100}}</td>
              <td>
                <span  style="color: #2da944"
                  @click="paymentRequest.products = paymentRequest.products.filter((product) => product.productId != prod.productId)">
                  <i class="material-icons">delete</i>
                </span>
              </td>
            </tr>
            <tr>
              <td>Total</td>
              <td></td>
              <td>{{totalAmount/100}}</td>
            </tr>
          </tbody>
        </table>
        <div v-if="totalAmount > 0" class="checkbox">
          <input type="checkbox" id="copyAmount" v-model="copyTotalToAmount" />
          <label for="copyAmount">Copy total to amount?</label>
        </div>
        <div class="input-group">
          <span class="input-group-addon">
            <i class="material-icons">add_shopping_cart</i>
          </span>
          <select id="products" class="form-control" v-model="selectedProduct" @change="addProduct(selectedProduct)">
            <option disabled :value="{}">Products</option>
            <option v-for="prod in productList" :key="prod.productId" :value="prod"
            >{{prod.name}}</option>
          </select>
        </div>
        <div class="row">
          <label class="col" for="amount">Amount</label>
          <label class="col-md-2" for="operation">Currency</label>
        </div>
        <div class="input-group amountSize">
          <span class="input-group-addon">
            <i class="material-icons">monetization_on</i>
          </span>
          <input class="form-control order-input" id="amount" v-model="initAmount" />
          <span class="input-group-addon">,</span>
          <input class="form-control col-md-1 order-input" v-model="initAmountCents" />
          <span class="input-group-addon">
            <i class="material-icons">attach_money</i>
          </span>
          <select id="operation" class="form-control col-md-2" v-model="paymentRequest.currency">
            <option disabled value>Select one</option>
            <option>NOK</option>
            <option>SEK</option>
            <option>DKK</option>
            <option>EUR</option>
          </select>
        </div>
        <div class="form-group">
          <div class="row">
            <label class="col" for="merchant">Merchant</label>
            <label class="col" for="invoice_type">Payment method</label>
          </div>
          <div class="input-group">
            <span class="input-group-addon">
              <i class="material-icons">store_mall_directory</i>
            </span>
            <select id="merchant" class="form-control" v-model="paymentRequest.merchant">
              <option disabled :value="{}">Please select one</option>
              <option
                v-for="merchant in merchantList"
                :key="merchant.id"
                v-bind:value="merchant"
              >{{merchant.merchantName}}</option>
            </select>
            <select id="invoice-type" class="form-control" v-model="paymentRequest.paymentMethods">
              <option disabled value>Select one</option>
              <option value="ALL">All</option>
              <option value="INVOICE">Invoice</option>
              <option value="ONLINE">Online</option>
            </select>
          </div>
        </div>
        <div v-if="customer" class="checkbox">
          <input type="checkbox" id="shipping" v-model="copyShippingInfo" />
          <label for="shipping">Ship to billing address?</label>
        </div>
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
            v-model="paymentRequest.shippingInformation.shippingAddressee"
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
          <label for="shippingPostalCode" class="col-md-3">Postal Code</label>
          <label for="shippingCity" class="col">City</label>
          <label for="shippingCountryCode" class="col-md-3">Country</label>
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
        <button
          class="btn btn-primary"
          type="button"
          v-on:click="createOrder"
          style="margin-top: 3rem"
        >Create order</button>
      </main>
    </div>
  </div>
</template>

<script>
import AddCustomerInfo from '@/components/AddCustomerInfo.vue'
import {
  merchantService,
  multipayService
} from '@/components/rest-resource.js'
import { toastError } from '@/utils/creditcard-util'
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
        currency: '',
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
      productMessage: 'Add product',
      initAmount: 0,
      initAmountCents: 0,
      copyShippingInfo: false,
      copyTotalToAmount: false,
      merchantList: [],
      productList: [{
        productOrderId: 0,
        name: 'test product',
        amount: 66666,
        description: 'test product',
        productId: 1,
        quantity: 1,
        unitOfMeasure: 'L',
        vatAmount: 6666 * 0.25,
        vatRate: 25
      }],
      selectedProduct: {}
    }
  },
  mounted () {
    this.listMerchants()
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
      this.paymentRequest.paymentTransmissionDateTime = new Date()

      // send to backend
      multipayService
        .createOrder(this.paymentRequest)
        .then(res => {
          px.toast({ html: 'Successfully created new order!' })
          this.$root.$emit('order-create-event', res.data)
        })
        .catch(error => {
          toastError(error)
        })
    },
    addProduct (prod) {
      if (this.paymentRequest.products.includes(prod)) this.paymentRequest.products.find((product) => product.productId === prod.productId).quantity += 1
      else {
        prod.quantity = 1
        this.paymentRequest.products.push(prod)
      }

      // this.initAmount = parseInt(this.initAmount) + parseInt(prod.amount / 100)
      // this.initAmountCents = parseInt(this.initAmountCents) + (prod.amount % 100)
      this.selectedProduct = {}
    }
  },
  watch: {
    initAmount () {
      this.paymentRequest.amount =
        parseInt(this.initAmount) * 100 + parseInt(this.initAmountCents)
    },
    initAmountCents () {
      this.paymentRequest.amount =
        parseInt(this.initAmount) * 100 + parseInt(this.initAmountCents)
    },
    copyShippingInfo () {
      if (this.copyShippingInfo === true) {
        this.paymentRequest.shippingInformation.shippingAddressee = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingStreetAddressee
        this.paymentRequest.shippingInformation.shippingCity = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCity
        this.paymentRequest.shippingInformation.shippingCoAddress = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCoAddress
        this.paymentRequest.shippingInformation.shippingCountryCode = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingCountryCode
        this.paymentRequest.shippingInformation.shippingPostalCode = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingPostalCode
        this.paymentRequest.shippingInformation.shippingStreetAddress = this.paymentRequest[this.customerType + 'CustomerIdentifier'].address.billingStreetAddress
      } else {
        this.paymentRequest.shippingInformation.shippingAddressee = ''
        this.paymentRequest.shippingInformation.shippingCity = ''
        this.paymentRequest.shippingInformation.shippingCoAddress = ''
        this.paymentRequest.shippingInformation.shippingCountryCode = ''
        this.paymentRequest.shippingInformation.shippingPostalCode = ''
        this.paymentRequest.shippingInformation.shippingStreetAddress = ''
      }
    },
    copyTotalToAmount () {
      if (this.copyTotalToAmount === true) {
        this.initAmount = this.totalAmount / 100
        this.initAmountCents = this.totalAmount % 100
      }
    }
  },
  computed: {
    totalAmount () {
      let total = 0
      for (let prod of this.paymentRequest.products) {
        total += prod.amount * prod.quantity
      }
      return total
    }
  }
}
</script>

<style>
.mainContainer {
  margin: 0 auto;
}

.order-input {
  text-align: right;
}
</style>
