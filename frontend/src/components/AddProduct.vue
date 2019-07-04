<template>
  <div>
    <div class="sheet" id="add-product-sheet">
      <section>
        <header>
          <h5>Add product info</h5>
          <a href="#" class="sheet-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="sheet-body">
          <div class="form-group">
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
              <select id="QuantityUnit" class="form-control col-md-3" v-model="measurement">
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
          <button
            class="btn btn-secondary col"
            type="button"
            style="display: table-cell"
            data-sheet-close="add-product-sheet"
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
    <button class="btn btn-primary" type="button" data-sheet-open="add-product-sheet">
      <i class="material-icons">person_add</i>
      <span>{{productMessage}}</span>
    </button>
  </div>
</template>

<script>
  export default {
    name: 'AddProduct',
    props: {
      productMessage: String
    },
    data() {
      return {
        product: {
          amount: 0,
          description: '',
          pdfcontent: '',
          productId: 0,
          productOrderId: 0,
          quantity: 0.0,
          unitOfMesure: '',
          vatAmount: 0,
          vatrate: 25
        },
        measurement: '',
      }
    },
    mounted() {
      px.sheet.init()
    },
    methods: {
      addProduct: function () {


        px.sheet.close('add-product-sheet')
        this.$emit('add-product-successful', product)
      }
    },

  }
</script>

<style>
  .quantity-size {
    margin: 0 auto;
  }
</style>
