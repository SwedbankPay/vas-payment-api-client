<template>
  <div class="container">
    <AddProduct/>
    <br>
    <div class="cards" v-for="item in items" :key="item.id">
      <div class="card card-default">
          <div class="highlight">
            <div class="media media-sm">
              <div class="media-img">
                <i class="material-icons text-muted">loyalty</i>
              </div>
              <div class="media-body">
                <h4 class="text-muted">{{item.name}}</h4>
                <p>
                  <small>Price: {{item.amount}}</small>
                </p>
              </div>
            </div>
          </div>
          <div class="card-body">
            <small>{{item.agreementId}}</small>
            <edit-product-dialog :product="item"></edit-product-dialog>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
import { multipayProductService } from './rest-resource'
import AddProduct from '@/components/AddProduct'
import EditProductDialog from "./EditProductDialog";
export default {
  name: 'ProductList',
  components: {EditProductDialog, AddProduct },
  data () {
    return {
      items: []
    }
  },
  created: function () {
    this.fetchItems()
  },
  mounted () {
    this.$root.$on('product-update-event', (merchant) => {
      console.log('got new "product-update-event" event')
      this.fetchItems()
    })
  },
  methods: {
    fetchItems () {
      multipayProductService.listProducts().then(res => { this.items = res.data })
    },
  }
}
</script>

<style>

</style>
