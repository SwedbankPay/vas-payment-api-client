<template>
  <div class="container">
    <h4>Create new Order</h4>
    <div class="row">
      <main class="mainContainer">
        <form novalidate>
          <div
            style="margin-bottom:0.5rem"
            class="form-group"
          >
            <label for="name">Name</label>
            <div class="input-group">
              <span
                class="form-control-text"
                id="name"
              >{{contactName}}</span>
            </div>
            <div class="help-block">Add a customer via the button below</div>
          </div>
          <AddCustomerInfo
            @add-customer-successful="updateCustomerData"
            :message="message"
            style="margin-bottom: 2rem"
          />
          <div class="form-group">
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
          </div>
          <div class="form-group">
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
                    <button
                      class="no-background"
                      @click.prevent="(prod.quantity > 0) ? decrementProduct(prod) : ''"
                      :style="{ float: 'left' }"
                    >
                      <i class="material-icons">remove_circle</i>
                    </button>
                    {{prod.quantity}}
                    <button
                      class="no-background"
                      @click.prevent="incrementProduct(prod)"
                      :style="{ float: 'right' }"
                    >
                      <i class="material-icons">add_circle</i>
                    </button>
                  </td>
                  <td>{{(prod.amount * prod.quantity)/100}}</td>
                  <td>
                    <button class="no-background" @click="removeProduct(prod)">
                      <i class="material-icons">delete</i>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div v-if="productList.length === 0">
              <p style="color: red">Please add at least one product before creating an order</p>
            </div>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">add_shopping_cart</i>
              </span>
              <select
                id="products"
                class="form-control"
                v-model="selectedProduct"
                @change="addProduct(selectedProduct)"
              >
                <option disabled :value="{}">Products</option>
                <option
                  v-for="prod in productList"
                  :key="prod.productId"
                  :value="prod"
                >{{prod.name}}</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="col form-group">
              <label class="col" for="amount">Amount</label>
              <div class="input-group amountSize">
                <span class="input-group-addon">
                  <i class="material-icons">monetization_on</i>
                </span>
                <div class="form-control order-input" id="amount">{{paymentRequest.amount/100}}</div>
              </div>
            </div>
            <div class="col-md-4 form-group">
              <label class="col-md-4" for="operation">Currency</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">attach_money</i>
                </span>
                <select
                  data-validate
                  required
                  id="operation"
                  class="form-control"
                  v-model="paymentRequest.currency"
                >
                  <option disabled value>Select one</option>
                  <option>NOK</option>
                  <option>SEK</option>
                  <option>DKK</option>
                  <option>EUR</option>
                </select>
              </div>
            </div>
          </div>
          <div v-if="merchantList.length === 0">
            <p style="color: red">Please add at least one merchant before creating an order</p>
          </div>
          <div class="row">
            <div class="col form-group">
              <label class="col" for="merchant">Merchant</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">store_mall_directory</i>
                </span>
                <select
                  data-validate
                  required
                  id="merchant"
                  class="form-control"
                  v-model="paymentRequest.merchant"
                >
                  <option disabled :value="null">Please select one</option>
                  <option
                    v-for="merchant in merchantList"
                    :key="merchant.id"
                    v-bind:value="merchant"
                  >{{merchant.merchantName}}</option>
                </select>
              </div>
            </div>
            <div class="col form-group">
              <label class="col" for="invoice-type">Payment method</label>
              <div class="input-group">
                <select
                  data-validate
                  required
                  id="invoice-type"
                  class="form-control"
                  v-model="paymentRequest.paymentMethod"
                >
                  <option disabled value>Select one</option>
                  <option value="ALL">All</option>
                  <option value="INVOICE">Invoice</option>
                  <option value="ONLINE">Online</option>
                </select>
              </div>
            </div>
          </div>
          <div v-if="customer" class="checkbox">
            <input type="checkbox" id="shipping" v-model="copyShippingInfo" />
            <label for="shipping">Ship to billing address?</label>
          </div>
          <h6 style="margin-top: 2rem">Shipping Address Information</h6>
          <div class="row">
            <div class="col form-group">
              <label class="col" for="shippingStreetAddressee">Street Addressee</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">account_box</i>
                </span>
                <input
                  data-validate
                  required
                  type="text"
                  class="form-control"
                  id="shippingStreetAddressee"
                  v-model="paymentRequest.shippingInformation.shippingAddressee"
                  placeholder="John Doe"
                />
              </div>
            </div>
            <div class="col form-group">
              <label class="col" for="shippingStreetAddress">Street Address</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">place</i>
                </span>
                <input
                  data-validate
                  required
                  type="text"
                  class="form-control"
                  id="shippingStreetAddress"
                  v-model="paymentRequest.shippingInformation.shippingStreetAddress"
                  placeholder="Drammensveien 1"
                />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col form-group">
              <label for="shippingPostalCode">Postal Code</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">location_city</i>
                </span>
                <input
                  data-validate
                  required
                  type="text"
                  class="form-control"
                  id="shippingCity"
                  v-model="paymentRequest.shippingInformation.shippingCity"
                  placeholder="Oslo"
                />
              </div>
            </div>
            <div class="col form-group">
              <label for="shippingCity">City</label>
              <div class="input-group">
                <input
                  data-validate
                  required
                  type="text"
                  class="form-control"
                  id="shippingCity"
                  v-model="paymentRequest.shippingInformation.shippingCity"
                  placeholder="Oslo"
                />
              </div>
            </div>
            <div class="col form-group">
              <label for="shippingCountryCode">Country</label>
              <div class="input-group">
                <input
                  data-validate
                  required
                  type="text"
                  class="form-control"
                  id="shippingCountryCode"
                  v-model="paymentRequest.shippingInformation.shippingCountryCode"
                  placeholder="NO"
                />
              </div>
            </div>
          </div>
          <button
            class="btn btn-primary"
            type="submit"
            v-on:click.prevent="createOrder"
            style="margin-top: 3rem"
          >Create order</button>
        </form>
      </main>
    </div>
  </div>
</template>

<script>
import AddCustomerInfo from '@/components/AddCustomerInfo.vue'
import { multipayProductService } from '@/components/rest-resource'
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
        corporateCustomerIdentifier: null,
        currency: '',
        privateCustomerIdentifier: null,
        description: '',
        merchant: null,
        paymentContractId: '',
        paymentExpireDateTime: '',
        paymentMethod: '', // ALL, INVOICE, ONLINE
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
      productList: [],
      selectedProduct: {},
      errors: []
    }
  },
  mounted () {
    this.listMerchants()
    this.listProducts()
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
    listProducts () {
      multipayProductService.listProducts().then(res => {
        this.productList = res.data
      })
    },
    createOrder () {
      if (!this.validInput()) return

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
      if (this.paymentRequest.products.includes(prod)) {
        this.paymentRequest.products.find(
          product => product.productId === prod.productId
        ).quantity += 1
      } else {
        this.paymentRequest.products.push(prod)
      }
      this.selectedProduct = {}
      this.paymentRequest.amount += prod.amount * prod.quantity
    },
    incrementProduct (prod) {
      prod.quantity += 1
      this.paymentRequest.amount += prod.amount
    },
    decrementProduct (prod) {
      prod.quantity -= 1
      this.paymentRequest.amount -= prod.amount
    },
    removeProduct (prod) {
      this.paymentRequest.products = this.paymentRequest.products.filter(
        product => product.productId !== prod.productId
      )
      this.paymentRequest.amount -= prod.quantity * prod.amount
    },
    validInput () {
      return this.customer != null
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
    }
  },
  computed: {
    totalAmount () {
      let total = 0
      for (let prod of this.paymentRequest.products) {
        total += prod.amount * prod.quantity
      }
      return total
    },
    contactName () {
      if (!this.customer) return 'No customer added'
      let prefix = this.customerType === 'private' ? 'customer' : 'contact'
      return this.customer[prefix + 'FirstName'] + ' ' + this.customer[prefix + 'LastName']
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

.no-background {
  border: 0;
  background-color: #f7f7f7;
  color: #2da944;
}

.no-background:hover {
  cursor: pointer;
  color: #32dd52;
}
</style>
