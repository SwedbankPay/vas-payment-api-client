<template>
  <div>
    <div class="dialog" id="pre-deposit-dialog">
      <section>
        <form novalidate>
          <header>
            <h5>Predeposit</h5>
            <a href="#" class="dialog-close">
              <i class="material-icons">close</i>
            </a>
          </header>
          <div class="dialog-body">
            <form novalidate>
              <div class="form-group">
                <label for="amount">amount</label>
                <div class="input-group">
                  <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
                  <input type="number" class="form-control" id="amount"
                         v-model="preDepositRequest.amount"
                         placeholder="amount" value data-validate required>
                </div>
              </div>
              <div class="form-group">
                <label for="description">Description</label>
                <div class="input-group">
                  <span class="input-group-addon"><i class="material-icons">mail</i></span>
                  <input type="text" class="form-control" id="description"
                         v-model="preDepositRequest.description"
                         placeholder="Description" value>
                </div>
              </div>
            </form>
          </div>
          <footer>
            <button class="btn btn-secondary" type="button" @click="handleClose">Cancel</button>
            <button class="btn btn-primary" type="button" @click="preDeposit">Predeposit</button>
          </footer>
        </form>
      </section>
    </div>
  </div>
</template>

<script>
    import {paymentGiftCardService, merchantService} from './rest-resource'
    import {toastError} from '../utils/creditcard-util'
    const uuidv4 = require('uuid/v4');

    export default {
        name: 'PreDepositDialog',
        props: ['currentGiftcard'],
        data() {
            return {
                merchantList: Array,
                preDepositRequest: {
                    orderRef: String,
                    transactionRef: String,
                    currency: String,
                    description: '',
                    amount: Number,
                    simpleAccountIdentifier: Object,
                    agreementMerchantId: String
                }
            }
        },
        mounted() {
            px.dialog.init();
            px.validation.init();
            this.listMerchants();
        },
        methods: {
            listMerchants() {
                merchantService.listMerchants().then(res => {
                    this.merchantList = res.data;
                    console.log(this.merchantList)
                })
            },
            preDeposit() {
                const orderRef = uuidv4();
                const transactionRef = uuidv4();
                this.preDepositRequest.orderRef = orderRef;
                this.preDepositRequest.transactionRef = transactionRef;
                this.preDepositRequest.currency = this.currentGiftcard.currencyIso;
                this.preDepositRequest.simpleAccountIdentifier = this.currentGiftcard.accountSummaryGiftCard;
                this.preDepositRequest.agreementMerchantId = this.currentGiftcard.agreementMerchantId;
                console.log(this.currentGiftcard);
                console.log(this.preDepositRequest.simpleAccountIdentifier.expiryDate);
                paymentGiftCardService.preDeposit(this.preDepositRequest).then(res => {
                    this.$emit('deposit', res.data);
                    px.dialog.close("pre-deposit-dialog");
                }).catch((error) => {
                    toastError(error)
                })
            },
            handleClose(event) {
                event.stopPropagation();
                px.dialog.close("pre-deposit-dialog");
            }
        }
    }
</script>

<style scoped>

</style>
