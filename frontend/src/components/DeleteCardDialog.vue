<template>
  <div>
    <div class="dialog" id="delete-card-dialog">
      <section>
        <header>
          <h5>Delete card {{this.paymentInstrument.id}}?</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div>
          <div class="dialog-body">
            <p>Are you sure you want to delete the item <i>{{this.paymentInstrument.type}} ({{this.paymentInstrument.id}})?</i></p>
          </div>
          <footer>
            <button class="btn btn-secondary" type="button" data-dialog-close="delete-card-dialog">Cancel</button>
            <button class="btn btn-danger" type="button" v-on:click="deleteCard">Delete</button>
          </footer>
        </div>
      </section>
    </div>
    <button class="btn btn-link" type="button" data-dialog-open="delete-card-dialog">
      <i class="material-icons">delete</i>
      <span>Delete card</span>
    </button>
  </div>
</template>

<script>
import { paymentInstrumentService } from './rest-resource'
import { toastError } from '../utils/creditcard-util'

export default {
  name: 'DeleteCardDialog',
  props: {
    paymentInstrument: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    deleteCard () {
      paymentInstrumentService.deletePaymentInstrument(this.$route.params.id).then(res => {
        px.toast({ html: 'Successfully deleted card!' })
        px.dialog.close('delete-card-dialog')
        this.$router.push({ path: '/cards' })
      }).catch((error) => {
        toastError(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
