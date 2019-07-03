<template>
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
              <label for="terminal_id">terminalId</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">lock</i></span>
                <input type="text" class="form-control" id="terminal_id"
                       v-model="merchant.terminalId"
                       placeholder="Terminal ID" value>
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
      data() {
        return {
          merchant: {
            merchantName: '',
            terminalId: '',
            currencyIso: ''
          }
        }
      }
    }
  </script>

  <style scoped>

  </style>

</template>

<script>
    export default {
        name: "addMerchantMultipay"
    }
</script>

<style scoped>

</style>
