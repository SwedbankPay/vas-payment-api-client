<template>
  <div>
    <div class="dialog" id="add-product-dialog">
      <section>
        <header>
          <h5>Add product info</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="dialog-body">
          <div class="form-group">
            <!-- Should maybe be set automatically by DB and backend
            <label for="productId">Product Id</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="m4gic-googl">#</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="productId"
                v-model="product.productId"
                placeholder="0"
              />
            </div>
            <label for="productOrderId">Product Order Id</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="m4gic-googl">#</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="productOrderId"
                v-model="product.productOrderId"
                placeholder="0"
              />
            </div> -->
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
                <option value="g">Grams</option>
                <option value="O">Present</option>
              </select>
            </div>
          </div>
        </div>
        <footer>
          <button
            class="btn btn-secondary col"
            type="button"
            style="display: table-cell"
            data-dialog-close="add-product-dialog"
          >
            <i class="material-icons">close</i>
          </button>
          <button
            class="btn btn-primary col"
            style="display: table-cell"
            type="button"
            v-on:click="addProduct"
          >
            <i class="material-icons">check</i>
          </button>
        </footer>
      </section>
    </div>
    <button class="btn btn-primary" type="button" data-dialog-open="add-product-dialog">
      <i class="material-icons">add</i>
      <span>Add products</span>
    </button>
  </div>
</template>

<script>
import { toastError } from '../utils/creditcard-util'
import { multipayProductService } from './rest-resource'

export default {
  name: 'AddProduct',
  props: {
    productMessage: String
  },
  data () {
    return {
      product: {
        amount: 0,
        description: '',
        name: '',
        productId: null,
        productOrderId: 0,
        quantity: 0.0,
        unitOfMeasure: '',
        vatAmount: 123,
        vatRate: 25
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    addProduct: function () {
      this.product.vatAmount = this.product.amount * this.product.vatRate / 100
      multipayProductService.addProduct((this.product)).then(res => {
        px.toast({ html: 'Successfully added new product!' })
        px.dialog.close('add-product-dialog')
        this.$root.$emit('product-update-event', res.data)
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style>
  .quantity-size {
    margin: 0 auto;
  }
</style>
