<template>
  <div>
    <div class="dialog" id="get-gift-card-dialog">
      <section>
        <header>
          <h5>Get new gift card</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div v-show="!merchantList" class="dialog-body">
          <p>You need at least one Merchant configured to add a new card, please
            <router-link to="/merchants">add a merchant first</router-link>
          </p>
        </div>
        <div v-show="merchantList">
          <div class="dialog-body">
            <form novalidate>
            <div class="form-group">
              <label for="productId">Product ID</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">credit_card</i></span>
                <input type="text" class="form-control" id="productId"
                       v-model="giftCardRequest.productId"
                       placeholder="Enter a product ID" value>
              </div>
            </div>
            <div class="form-group">
              <label for="email">email</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">mail</i></span>
                <input type="text" class="form-control" id="email"
                       v-model="giftCardRequest.email"
                       placeholder="Email@test.com" value>
              </div>
            </div>
            <div class="form-group">
              <label for="msisdn">msisdn</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">phone</i></span>
                <input type="text" class="form-control" id="msisdn"
                       v-model="giftCardRequest.msisdn"
                       placeholder="CC+NDC+SN" value>
              </div>
            </div>
            <div class="form-group">
              <label for="amount">amount</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
                <input type="number" class="form-control" id="amount"
                       v-model.number="giftCardRequest.amount"
                       placeholder="amount" value>
              </div>
            </div>
            <div class="form-group"><label for="merchant">Merchant</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
                <select id="merchant" class="form-control" v-model="currentMerchant" data-validate required>
                  <option disabled :value="defaultObject"> Please select one</option>
                  <option v-for="merchant in merchantList" :key="merchant.agreementId"
                          v-bind:value="merchant">
                    {{merchant.merchantName}}
                  </option>
                </select>
              </div>
            </div>
            </form>
          </div>
          <footer>
            <button class="btn btn-secondary" type="button" data-dialog-close="get-gift-card-dialog">Cancel</button>
            <button class="btn btn-primary" type="submit" v-on:click="getGiftCard">Get gift card</button>
          </footer>
        </div>
      </section>
    </div>
    <button class="btn btn-primary" type="button" data-dialog-open="get-gift-card-dialog" v-on:click="listMerchants">
      <i class="material-icons">add</i>
      <span>Get new gift card</span>
    </button>
  </div>
</template>

<script>
    import {merchantService, paymentGiftCardService} from './rest-resource'
    import {toastError} from '../utils/creditcard-util'

    export default {
        name: 'GetGiftCardDialog',
        data() {
            return {
                defaultObject: null,
                response: Object,
                currentMerchant: null,
                merchantList: [],
                giftCardRequest: {
                    amount: '',
                    email: '',
                    msisdn: '',
                    productId: '',
                    agreementMerchantId: ''
                },
            }
        },
        mounted() {
            px.dialog.init();
            px.validation.init();
        },
        watch: {
          currentMerchant(newValue) {
              console.log('value now: ', newValue)
              this.currentMerchant = newValue;
              this.giftCardRequest.agreementMerchantId = this.currentMerchant.agreementId;
              console.log('agreement: ', this.currentMerchant.agreementId)
          }
        },
        methods: {
            listMerchants() {
                merchantService.listMerchants().then(res => {
                    this.merchantList = res.data;
                }).catch((err) =>{
                    toastError(err)
                })
            },
            getGiftCard() {
                paymentGiftCardService.getGiftCard(this.giftCardRequest).then(res => {
                    px.dialog.close('get-gift-card-dialog');
                    this.response = res.data;
                    this.response.currencyIso = this.currentMerchant.currencyIso;
                    this.response.agreementMerchantId = this.currentMerchant.agreementId;
                    px.toast({html: 'Successful'});
                    console.log(this.response);
                    this.$emit('add', this.response);
                }).catch((error) => {
                    toastError(error)
                })
            }
        }
    }
</script>

<style scoped>

</style>
