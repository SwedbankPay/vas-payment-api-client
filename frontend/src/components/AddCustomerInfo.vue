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
            <label for="firstName">Name</label>
            <div class="input-group">
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
            <input
              type="text"
              class="form-control"
              id="privateEmail"
              v-model="privateCustomer.contactEmail"
              placeholder="hello@world.com"
            />
            <label for="privatePhone">Phone number</label>
            <div class="input-group">
              <span class="input-group-addon">
                #
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
            <input
              type="text"
              class="form-control"
              id="privateSsn"
              v-model="privateCustomer.ssn"
              placeholder="ssn"
            />
          </div>
          <div v-if="customerType === 'Corporate'" class="form-group">
            <label for="companyName">Company Name</label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                id="companyName"
                v-model="corporateCustomer.companyName"
                placeholder="First name"
              />
            </div>
            <label for="contactName">Contact Name</label>
            <div class="input-group">
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
            <input
              type="text"
              class="form-control"
              id="corporateEmail"
              v-model="corporateCustomer.contactEmail"
              placeholder="hello@world.com"
            />
            <label for="corporatePhone">Contact Phone number</label>
            <div class="input-group">
              <span class="input-group-addon">
                #
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
            <input
              type="text"
              class="form-control"
              id="corporateVat"
              v-model="corporateCustomer.vatRegistrationNumber"
              placeholder=""
            />
          </div>
        </div>
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
  data() {
    return {
      customerType: '',
      privateCustomer: {
        contactEmail: '',
        contactPhone: '',
        contactPhoneCountryCode: '47',
        customerFirstName: '',
        customerLastName: '',
        customerLanguagecode: 'NO',
        ssn: ''
      },

      corporateCustomer: {
        companyName: '',
        contactEmail: '',
        contactFirstName: '',
        contactLastName: '',
        contactPhone: '',
        contactPhoneCountryCode: '',
        languageCode: '',
        vatRegistrationNumber: ''
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
    };
  },
  mounted() {
    px.dialog.init();
  }
};
</script>

<style>

.small {
  width: 10%;
  text-align: right;
}
</style>
