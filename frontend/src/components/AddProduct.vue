<template>
  <div>
    <div class="dialog" id="add-product-dialog">
      <section>
        <header>
          <h5>Add product info</h5>
          <a class="dialog-close" href="#">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="dialog-body">
          <form novalidate>
            <div class="form-group">
              <label for="productName">Product Name</label>
              <div class="input-group">
                <span class="input-group-addon">
                  <i class="material-icons">store</i>
                </span>
                <input
                  class="form-control"
                  data-validate
                  id="productName"
                  placeholder="Cake"
                  required
                  type="text"
                  v-model="product.name"
                />
              </div>
            </div>
            <div class="form-group">
              <label for="productDescription">Description</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">description</i></span>
                <textarea
                  class="form-control"
                  id="productDescription"
                  placeholder="product description"
                  v-model="product.description"
                ></textarea>
              </div>
            </div>
            <div class="form-group">
              <label for="productPrice">Amount</label>
              <div class="input-group quantity-size">
                <span class="input-group-addon">
                  <i class="material-icons">monetization_on</i>
                </span>
                <input
                  class="form-control"
                  data-validate
                  id="productPrice" placeholder="Price of product in cents" required
                  v-model="product.amount"/>
              </div>
            </div>
            <div class="form-group">
              <label for="QuantityUnit">Quantity/Unit</label>
              <div class="input-group quantity-size">
                <span class="input-group-addon">
                  <i class="material-icons">loyalty</i>
                </span>
                <input
                  class="form-control col-md-3"
                  data-validate
                  placeholder="0"
                  required
                  style="text-align:right"
                  type="text"
                  v-model="product.quantity"
                />
                <select class="form-control col-md-3" data-validate id="QuantityUnit" required
                        v-model="product.unitOfMeasure">
                  <option disabled value>Select</option>
                  <option value="L">Litre</option>
                  <option value="U">Unit</option>
                  <option value="g">Grams</option>
                  <option value="O">Present</option>
                </select>
              </div>
            </div>
          </form>
        </div>
        <footer>
          <button
            class="btn btn-secondary col"
            data-dialog-close="add-product-dialog"
            style="display: table-cell"
            type="button"
          >
            Cancel
          </button>
          <button
            class="btn btn-primary col"
            style="display: table-cell"
            type="button"
            v-on:click="addProduct"
          >
            Save
          </button>
        </footer>
      </section>
    </div>
    <button class="btn btn-primary" data-dialog-open="add-product-dialog" type="button">
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
        amount: null,
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
    px.validation.init()
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
