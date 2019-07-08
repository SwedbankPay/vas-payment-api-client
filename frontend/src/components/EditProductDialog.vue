<template>
  <div>
    <div class="dialog" :id="`edit-product-dialog${product.productId}`">
      <section>
        <header>
          <h5>Update {{product.name}}</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="dialog-body">
          <div class="form-group">
            <label for="productName">Product Name</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">store</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="productName"
                v-model="product.name"
                placeholder="Cake"
              />
            </div>
            <label for="productDescription">Description</label>
            <div class="input-group">
              <span class="input-group-addon"><i class="material-icons">description</i></span>
              <textarea
                class="form-control"
                id="productDescription"
                v-model="product.description"
                placeholder="product description"
              ></textarea>
            </div>
            <label for="productPrice">Amount</label>
            <div class="input-group quantity-size">
          <span class="input-group-addon">
            <i class="material-icons">monetization_on</i>
          </span>
              <input class="form-control" id="productPrice" placeholder="Price of product" v-model="product.amount" />
            </div>
            <label for="QuantityUnit">Quantity/Unit</label>
            <div class="input-group quantity-size">
              <span class="input-group-addon">
                <i class="material-icons">loyalty</i>
              </span>
              <input
                type="text"
                class="form-control col-md-3"
                v-model="product.quantity"
                placeholder="0"
                style="text-align:right"
              />
              <select id="QuantityUnit" class="form-control col-md-3" v-model="product.unitOfMeasure">
                <option disabled value>Select</option>
                <option value="L">Litre</option>
                <option value="U">Unit</option>
                <option value="G">Grams</option>
                <option value="O">Present</option>
              </select>
            </div>
          </div>
        </div>
        <footer>
          <button class="btn btn-danger" type="button" v-on:click="deleteProduct">Delete</button>
          <button class="btn btn-secondary" type="button" :data-dialog-close="`edit-product-dialog${product.productId}`">Cancel</button>
          <button class="btn btn-primary" type="button" v-on:click="updateProduct">Save</button>
        </footer>
      </section>
    </div>
    <button class="btn btn-link" type="button" :data-dialog-open="`edit-product-dialog${product.productId}`">
      <i class="material-icons ">build</i>
      <span>Edit</span>
    </button>
  </div>
</template>

<script>
import { toastError } from '../utils/creditcard-util'
import { multipayProductService } from './rest-resource'

export default {
  name: 'EditProductDialog',
  props: {
    productMessage: String,
    product: Object
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    updateProduct () {
      multipayProductService.updateProduct(this.product).then(res => {
        px.toast({ html: 'Successfully updated "' + this.product.name + '"' })
        this.$root.$emit('product-update-event', res)
        px.dialog.close(`edit-product-dialog${this.product.productId}`)
      }).catch((error) => {
        toastError(error)
      })
    },
    deleteProduct () {
      multipayProductService.deleteProduct(this.product.productId).then(res => {
        px.toast({ html: 'Successfully deleted "' + this.product.name + '"' })
        this.$root.$emit('product-update-event', res)
        px.dialog.close(`edit-product-dialog${this.product.productId}`)
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style scoped>
  .quantity-size {
    margin: 0 auto;
  }
</style>
