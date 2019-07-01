<template>
  <div class="container">
    <div class="row">
      <div class="col-sm">One of three columns</div>
      <div class="col-sm"><div class="form-group">
        <label for="input-id-3">Amount</label>
        <div class="input-group">
          <span class="input-group-addon"><i class="material-icons">attach_money</i></span>
          <input type="number" class="form-control" id="input-id-3" placeholder="Rounded to nearest $" />
          <span class="input-group-addon">.00</span>
        </div>
      </div></div>
      <div class="col-sm">One of three columns</div>
    </div>
    <AddCustomerInfo/>
  </div>
</template>

<script>
    import AddCustomerInfo from "./AddCustomerInfo";
    export default {
        name: "RequestFrom.vue",
      components: {AddCustomerInfo}
    }
</script>

<style scoped>

</style>
