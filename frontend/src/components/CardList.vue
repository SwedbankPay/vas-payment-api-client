<template>
  <div class="container">
    <div class="cards" v-for="item in items" :key="item.id">
      <div class="card card-default">
        <router-link :to="{name: 'PaymentInstrument', params: { id: item.id }}">
          <div class="highlight">
            <div class="media media-sm">
              <div class="media-img">
                <i class="material-icons text-muted">payment</i>
              </div>
              <div class="media-body">
                <h4 class="text-muted">{{item.type}}</h4>
                <p>
                  <small>{{maskPan(item.pan)}}</small>
                </p>
              </div>
            </div>
          </div>
          <div class="card-body">
            <small>External ID: {{item.externalAccountId}}</small>
          </div>
        </router-link>
      </div>
    </div>
    <AddCardDialog></AddCardDialog>
  </div>
</template>

<script>
import { paymentInstrumentService } from './rest-resource'
import { maskPan } from '../utils/creditcard-util'
import AddCardDialog from './AddCardDialog'

export default {
  components: { AddCardDialog },
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
  },
  mounted () {
    this.$root.$on('add-card-successful', (card) => {
      console.log('got new "add-card-successful" event')
      this.fetchItems()
    })
  },
  methods: {
    fetchItems () {
      paymentInstrumentService.listPaymentInstruments().then(res => {
        this.items = res.data
      })
    },
    maskPan (pan) {
      return maskPan(pan)
    }
  }
}
</script>

<style scoped>

</style>
