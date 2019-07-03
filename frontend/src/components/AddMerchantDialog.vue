<template>
<div>
  <div class="dialog" id="add-merchant-dialog">
    <section>
      <header>
        <h5>Add new merchant</h5>
        <a href="#" class="dialog-close">
          <i class="material-icons">close</i>
        </a>
      </header>
      <div class="dialog-body">

        <div class="form-group">
          <label for="merchant_name">Merchant name</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
            <input type="text" class="form-control" id="merchant_name"
                   v-model="merchant.merchantName"
                   placeholder="Merchant name" value>
          </div>
        </div>
        <div class="form-group">
          <label for="agreement_id">Agreement ID</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="material-icons">lock</i></span>
            <input type="text" class="form-control" id="agreement_id"
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
              placeholder="optional"
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
              placeholder="optional"
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
              placeholder="optional"
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
        <button class="btn btn-secondary" type="button" data-dialog-close="add-merchant-dialog">Cancel</button>
        <button class="btn btn-primary" type="button" v-on:click="addMerchant">Save</button>
      </footer>
    </section>
  </div>
  <button class="btn btn-primary" type="button" data-dialog-open="add-merchant-dialog">
    <i class="material-icons">add</i>
    <span>Add new merchant</span>
  </button>
</div>
</template>

<script>
import { merchantService } from './rest-resource'
import { toastError } from '../utils/creditcard-util'

export default {
  name: 'AddMerchantDialog',
  props: { selectedApi: String },
  data () {
    return {
      merchant: {
        merchantName: '',
        agreementId: '',
        currencyIso: '',
        sellerName: '',
        sellerId: '',
        terminalId: ''
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    addMerchant () {
      merchantService.addMerchant(this.merchant).then(res => {
        px.toast({ html: 'Successfully added new merchant!' })
        px.dialog.close('add-merchant-dialog')
        this.$root.$emit('merchant-update-event', res.data)
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
