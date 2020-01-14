<template>
  <div>
    <div class="dialog" id="ping-dialog">
      <section>
        <header>
          <h5>Ping</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
          <div class="dialog-body">
            <div class="form-group">
              <label for="description">Description</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">mail</i></span>
                <input type="text" class="form-control" id="description"
                       v-model="pingRequest.description"
                       placeholder="Description" value>
              </div>
            </div>
            <div class="form-group"><label for="merchant">Merchant</label>
              <div class="input-group">
                <span class="input-group-addon"><i class="material-icons">store_mall_directory</i></span>
                <select id="merchant" class="form-control" v-model="pingRequest.agreementMerchantId">
                  <option disabled value="">Please select one</option>
                  <option v-for="merchant in merchantList" :key="merchant.agreementId"
                          v-bind:value="merchant.agreementId">
                    {{merchant.merchantName}}
                  </option>
                </select>
              </div>
            </div>
          </div>
          <footer>
            <button class="btn btn-secondary" type="button" data-dialog-close="ping-dialog">Cancel</button>
            <button class="btn btn-primary" type="button" v-on:click="ping">Ping</button>
          </footer>
      </section>
    </div>
    <button class="btn btn-primary" type="button" data-dialog-open="ping-dialog" v-on:click="listMerchants">
      <i class="material-icons">description</i>
      <span>Ping</span>
    </button>
  </div>
</template>

<script>
    import {pingService, merchantService} from './rest-resource'
    import {toastError} from '../utils/creditcard-util'

    export default {
        name: 'PingDialog',
        data() {
            return {
                merchantList:[],
                pingRequest: {
                  description: '',
                    agreementMerchantId: ''
                },
                pingResponse: {
                  description: ''
                },
                responseStatus : ''
            }
        },
        mounted() {
            px.dialog.init()
        },
        methods: {
            listMerchants() {
                merchantService.listMerchants().then(res => {
                    this.merchantList = res.data
                }).catch((error) => {
                    toastError(error)
                })
            },
            ping() {
                pingService.ping(this.pingRequest).then(res => {
                    this.pingResponse = res.data;
                    this.responseStart = res.data.state;
                    px.toast({html: 'status: ' + this.responseStart + ' Description ' + this.pingResponse.description });
                }).catch((error) => {
                    toastError(error)
                })
            }
        }
    }
</script>

<style scoped>

</style>
