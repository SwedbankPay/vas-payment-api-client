<template>
  <div>
    <div class="sheet" id="balance-sheet">
      <section>
        <a href="#" class="sheet-close">
          <i class="material-icons">close</i>
        </a>
        <h2>Sync balance on card</h2>

        <hr/>
        <form v-on:submit.prevent="invokeBalance()">
          <div class="form-group"><label for="merchant">Merchant</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
              <select id="merchant" class="form-control" v-model="selectedAgreementId" required>
                <option disabled value="">Please select one</option>
                <option v-for="merchant in merchantList" :key="merchant.id" v-bind:value="merchant.agreementId">
                  {{merchant.merchantName}}
                </option>
              </select>
            </div>
          </div>
          <button class="btn btn-primary" type="submit"
                  :disabled="!!isSaving">Send request</button>
        </form>
      </section>
    </div>
    <button class="btn btn-link" type="button" v-on:click="listMerchants"
            data-sheet-open="balance-sheet">
      <i class="material-icons">sync</i>
      <span>Check balance</span>
    </button>
  </div>
</template>

<script>

import { paymentOperationService, merchantService } from './rest-resource'
import { toastError } from '../utils/creditcard-util'

export default {
  name: 'InvokeBalanceSheet',
  props: {
    paymentInstrument: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data () {
    return {
      merchantList: [],
      selectedAgreementId: '',
      isSaving: false
    }
  },
  mounted () {
    px.sheet.init()
  },
  methods: {
    listMerchants () {
      merchantService.listMerchants().then(res => {
        this.merchantList = res.data
      })
    },
    invokeBalance () {
      this.isSaving = true
      console.log('invokeBalance invoked with agreementId: ' + this.selectedAgreementId)
      paymentOperationService.balance(this.$route.params.id, this.selectedAgreementId).then(res => {
        this.handleOkBalance(res.data)
      }).catch((error) => {
        this.errorHandler(error)
      })
    },
    errorHandler (error) {
      this.isSaving = false
      toastError(error)
    },
    handleOkBalance (result) {
      this.isSaving = false
      px.sheet.close('balance-sheet')
      this.$root.$emit('balance-successful', result)
    }
  }
}
</script>

<style scoped>

</style>
