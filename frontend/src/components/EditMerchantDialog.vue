<template>
<div>
  <div class="dialog" :id="`edit-merchant-dialog${merchant.id}`">
    <section>
      <header>
        <h5>Update {{merchant.merchantName}}</h5>
        <a href="#" class="dialog-close">
          <i class="material-icons">close</i>
        </a>
      </header>
      <div class="dialog-body">

        <div class="form-group">
          <label for="pan">Merchant name</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
            <input type="text" class="form-control" id="pan"
                   v-model="merchant.merchantName"
                   placeholder="Merchant name" value>
          </div>
        </div>
        <div class="form-group">
          <label for="cvc">Agreement ID</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">lock</i></span>
            <input type="text" class="form-control" id="cvc"
                   v-model="merchant.agreementId"
                   placeholder="Agreement ID" value>
          </div>
        </div>
        <div v-if="selectedApi === 'multipay'" class="form-group">
          <label for="seller-name">Seller name</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">shop</i></span>
            <input
              type="text"
              class="form-control"
              id="seller-name"
              v-model="merchant.sellerName"
              placeholder="Optional"
            >
          </div>
          <label for="seller-id">Seller ID</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">store</i></span>
            <input
              type="text"
              class="form-control"
              id="seller-id"
              v-model="merchant.sellerId"
              placeholder="Optional"
            >
          </div>
          <label for="terminal-id">Terminal ID</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">keyboard</i></span>
            <input
              type="text"
              class="form-control"
              id="terminal-id"
              v-model="merchant.terminalId"
              placeholder="Optional"
            >
          </div>
        </div>
        <div class="form-group"><label for="operation">Currency</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
            <select id="operation" class="form-control" v-model="merchant.currencyIso">
              <option disabled value="">Please select one</option>
              <option>NOK</option>
              <option>SEK</option>
              <option>DKK</option>
              <option>EUR</option>
            </select>
          </div>
        </div>
      </div>
      <footer>
        <button class="btn btn-danger" type="button" v-on:click="deleteMerchant">Delete</button>
        <button class="btn btn-secondary" type="button" :data-dialog-close="`edit-merchant-dialog${merchant.id}`">Cancel</button>
        <button class="btn btn-primary" type="button" v-on:click="updateMerchant">Save</button>
      </footer>
    </section>
  </div>
  <button class="btn btn-link" type="button" :data-dialog-open="`edit-merchant-dialog${merchant.id}`">
    <i class="material-icons ">build</i>
    <span>Edit</span>
  </button>
</div>
</template>

<script>
import { merchantService } from './rest-resource'

import { toastError } from '../utils/creditcard-util'

export default {
  name: 'EditMerchantDialog',
  props: {
    merchant: {
      type: Object,
      default: function () {
        return {}
      }
    },
    selectedApi: {
      type: String,
      default: "payment-api"
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    updateMerchant () {
      merchantService.updateMerchant(this.merchant).then(res => {
        px.toast({ html: 'Successfully updated "' + this.merchant.merchantName + '"' })
        this.$root.$emit('merchant-update-event', res)
        px.dialog.close(`edit-merchant-dialog${this.merchant.id}`)
      }).catch((error) => {
        toastError(error)
      })
    },
    deleteMerchant () {
      merchantService.deleteMerchant(this.merchant.id).then(res => {
        px.toast({ html: 'Successfully deleted "' + this.merchant.merchantName + '"' })
        this.$root.$emit('merchant-update-event', res)
        px.dialog.close(`edit-merchant-dialog${this.merchant.id}`)
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
