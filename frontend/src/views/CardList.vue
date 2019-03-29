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
            <p>Card text</p>
            <small>Small card text</small>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { paymentInstrumentService } from '../components/rest-resource'
import { maskPan } from '../utils/creditcard-util'

export default {
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
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
