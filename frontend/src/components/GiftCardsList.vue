<template>
  <div class="container">
    <GetGiftCardDialog @add="handleAdd"></GetGiftCardDialog>
    <div v-if="giftCardList.length > 0">
      <GiftCardsItem :giftCardsList="giftCardList"></GiftCardsItem>
    </div>
  </div>
</template>

<script>
    import GetGiftCardDialog from './GetGiftCardDialog'
    import GiftCardsItem from "./GiftCardsItem";

    export default {
        components: {GetGiftCardDialog, GiftCardsItem},
        data() {
            return {
                giftCardList: []
            }
        },
        methods: {
            handleAdd(data) {
                this.giftCardList.push(data);
                console.log(data);
                console.log(this.giftCardList)
            }
        }
    }
</script>

<style scoped>

</style>
