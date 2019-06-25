<template>
  <div class="container">
    <div class="cards" v-for="item in items" :key="item.id">
      <div class="card card-default">
          <div class="highlight">
            <div class="media media-sm">
              <div class="media-img">
                <i class="material-icons text-muted">house</i>
              </div>
              <div class="media-body">
                <h4 class="text-muted">{{item.merchantName}}</h4>
                <p>
                  <small>Currency: {{item.currencyIso}}</small>
                </p>
              </div>
            </div>
          </div>
          <div class="card-body">
            <small>{{item.agreementId}}</small>
            <edit-merchant-dialog :merchant="item"></edit-merchant-dialog>
          </div>
      </div>
    </div>
    <AddMerchantDialog></AddMerchantDialog>
  </div>
</template>

<script>
import { merchantService } from './rest-resource'
import AddMerchantDialog from './AddMerchantDialog'
import EditMerchantDialog from './EditMerchantDialog'

export default {
  name: 'MerchantList',
  components: { EditMerchantDialog, AddMerchantDialog },
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
  },
  mounted () {
    this.$root.$on('merchant-update-event', (merchant) => {
      console.log('got new "merchant-update-event" event')
      this.fetchItems()
    })
  },
  methods: {
    fetchItems () {
      merchantService.listMerchants().then(res => {
        this.items = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>
