<template>
  <div>
    <div class="dialog" id="add-customer-dialog">
      <section>
        <header>
          <h5>Add customer info</h5>
          <a href="#" class="dialog-close">
            <i class="material-icons">close</i>
          </a>
        </header>
        <div class="dialog-body">
          <div class="form-group">
            <label for="operation">Customer type</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">label</i>
              </span>
              <select id="operation" class="form-control" v-model="customerType">
                <option disabled value>Please select one</option>
                <option>Private</option>
                <option>Corporate</option>
              </select>
            </div>
          </div>
          <div v-if="customerType === 'Private'" class="form-group">
            <h6>Personal Information</h6>
            <label for="firstName">Name</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">account_box</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="firstName"
                v-model="privateCustomer.customerFirstName"
                placeholder="First name"
              />
              <input
                type="text"
                class="form-control"
                id="lastName"
                v-model="privateCustomer.customerLastName"
                placeholder="Last name"
              />
            </div>
            <label for="privateEmail">Email</label>
            <div class="input-group">
            <span class="input-group-addon">
                <i class="material-icons">email</i>
              </span>
            <input
              type="text"
              class="form-control"
              id="privateEmail"
              v-model="privateCustomer.contactEmail"
              placeholder="hello@world.com"
            />
            </div>
            <label for="privatePhone">Phone number</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">phone</i>
              </span>
              <input
                  type="text"
                  class="small"
                  v-model="privateCustomer.contactPhoneCountryCode"
                  placeholder="47"
                />
              <input
                type="text"
                class="form-control"
                id="privatePhone"
                v-model="privateCustomer.contactPhone"
                placeholder="987654321"
              />
            </div>
            <label for="privateSsn">SSN</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">fingerprint</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="privateSsn"
                v-model="privateCustomer.ssn"
                placeholder="ssn"
              />
            </div>
          </div>
          <div v-if="customerType === 'Corporate'" class="form-group">
            <h6>Company Information</h6>
            <label for="companyName">Company Name</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">work</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="companyName"
                v-model="corporateCustomer.companyName"
                placeholder="Company name"
              />
            </div>
            <label for="contactName">Contact Name</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">account_box</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="contactName"
                v-model="privateCustomer.contactFirstName"
                placeholder="First name"
              />
              <input
                type="text"
                class="form-control"
                id="lastName"
                v-model="privateCustomer.contactLastName"
                placeholder="Last name"
              />
            </div>
            <label for="corporateEmail">Contact Email</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">email</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="corporateEmail"
                v-model="corporateCustomer.contactEmail"
                placeholder="hello@world.com"
              />
            </div>
            
            <label for="corporatePhone">Contact Phone number</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">phone</i>
              </span>
              <input
                  type="text"
                  class="small"
                  v-model="corporateCustomer.contactPhoneCountryCode"
                  placeholder="47"
                />
              <input
                type="text"
                class="form-control"
                id="corporatePhone"
                v-model="corporateCustomer.contactPhone"
                placeholder="987654321"
              />
            </div>
            <label for="corporateSsn">Vat Registration Number</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">shopping_basket</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="corporateVat"
                v-model="corporateCustomer.vatRegistrationNumber"
                placeholder=""
              />
            </div>
          </div>
          <div>
            <h6>Address Information</h6>
            <label for="streetAddress">Street Address</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">place</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="streetAddress"
                v-model="address.streetAddress"
                placeholder="Drammensveien 1"
              />
            </div>
            <div class="row">
              <label for="postalCode" class="col">Postal Code</label>
              <label for="city" class="col">City</label>
              <label for="countryCode" class="col">Country</label>
            </div>
            <div class="input-group">
              
              <input
                type="text"
                class="form-control"
                id="postalCode"
                v-model="address.postalCode"
                placeholder="0271"
              />              
              <input
                type="text"
                class="form-control"
                id="city"
                v-model="address.city"
                placeholder="Oslo"
              />              
              <input
                type="text"
                class="form-control"
                id="countryCode"
                v-model="address.countryCode"
                placeholder="NO"
              />
            </div>
          </div>
          <div class="checkbox">
            <input
            type="checkbox"
            id="billing"
            v-model="copyBillingAddress"
            >
            <label for="billing"> Bill to same address?</label>
          </div>
           <div>
            <h6>Billing Address Information</h6>
            <label for="billingStreetAddress">Street Address</label>
            <div class="input-group">
              <span class="input-group-addon">
                <i class="material-icons">place</i>
              </span>
              <input
                type="text"
                class="form-control"
                id="billingStreetAddress"
                v-model="address.billingStreetAddress"
                placeholder="Drammensveien 1"
              />
            </div>
            <div class="row">
              <label for="billingPostalCode" class="col">Postal Code</label>
              <label for="billingCity" class="col">City</label>
              <label for="billingCountryCode" class="col">Country</label>
            </div>
            <div class="input-group">
              
              <input
                type="text"
                class="form-control"
                id="billingPostalCode"
                v-model="address.billingPostalCode"
                placeholder="0271"
              />
              <input
                type="text"
                class="form-control"
                id="billingCity"
                v-model="address.billingCity"
                placeholder="Oslo"
              />
              
              <input
                type="text"
                class="form-control"
                id="billingCountryCode"
                v-model="address.billingCountryCode"
                placeholder="NO"
              />
            </div>
          </div>
        </div>
        <footer>
          <button
              class="btn btn-secondary col"
              type="button"
              style="display: table-cell"
              data-dialog-close="add-customer-dialog"
            ><i class="material-icons">close</i></button>
            <button
             class="btn btn-primary col" 
             style="display: table-cell" 
             type="button" v-on:click="addCustomer"
             ><i class="material-icons">check</i></button>
        </footer>
      </section>
    </div>
    <button
      class="btn btn-primary"
      type="button"
      data-dialog-open="add-customer-dialog"
    >
      <i class="material-icons">add</i>
      <span>Add new customer</span>
    </button>
  </div>
</template>

<script>
export default {
  name: 'AddCustomerInfo',
  props: [],
  data () {
    return {
      copyBillingAddress: false,
      customerType: '',
      privateCustomer: {
        contactEmail: '',
        contactPhone: '',
        contactPhoneCountryCode: '47',
        customerFirstName: '',
        customerLastName: '',
        customerLanguagecode: 'NO',
        ssn: '',
        address: {},
      },

      corporateCustomer: {
        companyName: '',
        contactEmail: '',
        contactFirstName: '',
        contactLastName: '',
        contactPhone: '',
        contactPhoneCountryCode: '',
        languageCode: '',
        vatRegistrationNumber: '',
        address: {},
      },
      address: {
        billingCity: '',
        billingCoAddress: '',
        billingCountryCode: '',
        billingPostalCode: '',
        billingStreetAddress: '',
        billingStreetAddressee: '',
        city: '',
        coAddress: '',
        countryCode: 'NO',
        postalCode: '',
        streetAddress: ''
      }
    }
  },
  mounted () {
    px.dialog.init()
  },
  methods: {
    addCustomer: function () {
      let customer = this.customerType === 'Corporate' ? this.corporateCustomer : this.privateCustomer
      customer.address = this.address

      px.dialog.close('add-customer-dialog')
      this.$root.$emit('add-customer-successful', customer)
    }
  },
  watch: {
    copyBillingAddress () {
      if (this.copyBillingAddress === true) {
        this.address.billingCity = this.address.city
        this.address.billingStreetAddress = this.address.streetAddress
        this.address.billingStreetAddressee = 'what is this?'
        this.address.billingCoAddress = this.address.coAddress
        this.address.billingPostalCode = this.address.postalCode
        this.address.billingCountryCode = this.address.countryCode
      }
    }
  }
}
</script>

<style>

.small {
  width: 10%;
  text-align: right;
}
</style>
