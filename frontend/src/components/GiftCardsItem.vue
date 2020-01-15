<template>
  <section class="panel panel-brand">
    <table class="table table-hover table-striped">
      <thead>
      <tr>
        <td>ID</td>
        <td>PAN</td>
        <td>CVC</td>
        <td>Expiry Date</td>
        <td>Balance</td>
        <td v-if="depositResponse.balance">Predeposit Balance</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(card, index) in giftCardsList" :key="card.accountSummaryGiftCard.accountKey">
        <td>{{ card.paymentId }}</td>
        <td>{{ maskPan(card.accountSummaryGiftCard.accountKey) }}</td>
        <td>{{ card.accountSummaryGiftCard.cvc }}</td>
        <td>{{ formatDate(card.accountSummaryGiftCard.expiryDate) }}</td>
        <td>{{ formatNumber(card.accountSummaryGiftCard.balance / 100) }}</td>
        <td v-if="depositResponse.balance">{{ formatNumber(depositResponse.balance / 100) }}</td>
        <td>
          <div>
            <Button class="btn btn-primary" @click="buttonNumber(index)">Predeposit</Button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <PreDepositDialog :currentGiftcard="giftCard" @deposit="preDepositResponse"></PreDepositDialog>
  </section>
</template>

<script>
    import {maskPan, formatNumber, formatDate} from '../utils/creditcard-util'
    import PreDepositDialog from "./PreDepositDialog";

    export default {
        components: {PreDepositDialog},
        name: 'GiftCardsItem',
        props: ['giftCardsList'],
        data() {
            return {
                giftCard: Object,
                depositResponse: {
                    balance: 0,
                }
            }
        },

        methods: {
            buttonNumber(index) {
                this.giftCard = this.giftCardsList[index];
                console.log('current giftcard', this.giftCard);
                px.dialog.init();
                px.dialog.open("pre-deposit-dialog")
            },
            preDepositResponse(response){
                console.log('preDeposutResponse', response);
                this.depositResponse = response;
                px.toast({html: "successful Predeposit"});
            }
        },
        computed: {
            maskPan(pan) {
                return maskPan(pan)
            },
            formatNumber(number) {
                return formatNumber(number)
            },
            formatDate(date) {
                return formatDate(date)
            },
        }
    }
</script>

<style scoped>
</style>
