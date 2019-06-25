<template>
<div>
  <div class="dialog" id="add-card-dialog">
    <section>
      <header>
        <h5>Add new card</h5>
        <a href="#" class="dialog-close">
          <i class="material-icons">close</i>
        </a>
      </header>
        <div v-if="merchantList.length === 0" class="dialog-body">
          <p>You need at least one Merchant configured to add a new card, please <router-link to="/merchants">add a merchant first</router-link></p>
        </div>
        <div v-else>
          <div class="dialog-body">
          <div class="form-group"><label for="operation">Payment instrument type</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">label</i></span>
              <select id="operation" class="form-control" v-model="paymentInstrument.type">
                <option disabled value="">Please select one</option>
                <option>Prepaid card</option>
                <option>Credit card</option>
                <option disabled>Value code</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label for="pan">Card number</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">credit_card</i></span>
              <input type="text" class="form-control" id="pan"
                     v-model="paymentInstrument.pan"
                     placeholder="Enter a valid card number" value>
            </div>
            <div class="help-block">Enter a valid card number</div>
          </div>
          <div class="form-group">
            <label for="cvc">CVC</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">lock</i></span>
              <input type="text" class="form-control" id="cvc"
                     v-model="paymentInstrument.cvc"
                     placeholder="CVC" value>
            </div>
          </div>

          <div class="form-group"><label for="merchant">Merchant</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
              <select id="merchant" class="form-control" v-model="paymentInstrument.agreementId">
                <option disabled value="">Please select one</option>
                <option v-for="merchant in merchantList" :key="merchant.agreementId" v-bind:value="merchant.agreementId">
                  {{merchant.merchantName}}
                </option>
              </select>
            </div>
          </div>
      </div>
        <footer>
          <button class="btn btn-secondary" type="button" data-dialog-close="add-card-dialog">Cancel</button>
          <button class="btn btn-primary" type="button" v-on:click="addCard">Save</button>
        </footer>
        </div>
    </section>
  </div>
  <button class="btn btn-primary" type="button" data-dialog-open="add-card-dialog" v-on:click="listMerchants">
    <i class="material-icons">add</i>
    <span>Add new card</span>
  </button>
</div>
</template>

<script>
import { merchantService, paymentInstrumentService } from './rest-resource'
import { toastError } from '../utils/creditcard-util'

export default {
  name: 'AddCardDialog',
  data () {
    return {
      merchantList: [],
      paymentInstrument: {
        pan: '',
        type: '',
        cvc: '',
        agreementId: ''
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    listMerchants () {
      merchantService.listMerchants().then(res => {
        this.merchantList = res.data
      })
    },
    addCard () {
      paymentInstrumentService.addPaymentInstrument(this.paymentInstrument).then(res => {
        px.toast({ html: 'Successfully added new card!' })
        px.dialog.close('add-card-dialog')
        this.$root.$emit('add-card-successful', res.data)
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
