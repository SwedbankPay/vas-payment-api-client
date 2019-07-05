import axios from 'axios/index'

export const baseUrl = 'http://localhost:8080'
const apiRoot = baseUrl + '/api/'

const paymentClientRepository = axios.create({
  baseURL: apiRoot,
  headers: {
    'Access-Control-Allow-Origin': baseUrl
  }
})

export const multipayService = {
  createOrder: function (request) {
    return paymentClientRepository({
      method: 'post',
      headers: {
        agreementId: request.merchant.agreementId
      },
      data: request,
      url: 'multipay/order'
    })
  },
  getOrder: function (agreementId, id) {
    return paymentClientRepository({
      method: 'get',
      headers: {
        agreementId: agreementId
      },
      url: `multipay/order/${id}`
    })
  },
  cancelOrder: function (agreementId, id) {
    return paymentClientRepository({
      method: 'post',
      headers: {
        agreementId: agreementId
      },
      url: `multipay/order/${id}/cancel`
    })
  }
}

export const merchantService = {
  listMerchants: function () {
    return paymentClientRepository({
      method: 'get',
      url: 'merchants/'
    })
  },
  addMerchant: function (merchant) {
    return paymentClientRepository({
      method: 'post',
      data: merchant,
      url: 'merchants'
    })
  },
  updateMerchant: function (merchant) {
    return paymentClientRepository({
      method: 'put',
      data: merchant,
      url: 'merchants'
    })
  },
  deleteMerchant: function (merchantId) {
    return paymentClientRepository({
      method: 'delete',
      url: `merchants/${merchantId}`
    })
  }
}

export const multipayProductService = {
  listProducts: function () {
    return paymentClientRepository({
      method: 'get',
      url: 'products/'
    })
  },
  addProduct: function (product) {
    return paymentClientRepository({
      method: 'post',
      data: product,
      url: 'products'
    })
  },
  updateProduct: function (product) {
    return paymentClientRepository({
      method: 'put',
      data: product,
      url: 'products'
    })
  },
  deleteProduct: function (productId) {
    return paymentClientRepository({
      method: 'delete',
      url: `products/${productId}`
    })
  }
}

export const paymentInstrumentService = {

  listPaymentInstruments: function () {
    return paymentClientRepository({
      method: 'get',
      url: 'payment-instruments/'
    })
  },
  getPaymentInstrument: function (paymentInstrumentId) {
    return paymentClientRepository({
      method: 'get',
      url: `payment-instruments/${paymentInstrumentId}`
    })
  },
  deletePaymentInstrument: function (paymentInstrumentId) {
    return paymentClientRepository({
      method: 'delete',
      url: `payment-instruments/${paymentInstrumentId}`
    })
  },
  addPaymentInstrument: function (paymentInstrument) {
    return paymentClientRepository({
      method: 'post',
      data: paymentInstrument,
      url: 'payment-instruments',
      params: {
        agreementId: paymentInstrument.agreementId
      }
    })
  },
  updatePaymentInstrument: function (paymentInstrument) {
    return paymentClientRepository({
      method: 'put',
      data: paymentInstrument,
      url: 'payment-instruments'
    })
  }
}

export const paymentOperationService = {

  listPayments: function (paymentInstrumentId) {
    return paymentClientRepository({
      method: 'get',
      url: `payment-instruments/${paymentInstrumentId}/payments`
    })
  },
  authorize: function (paymentInstrumentId, paymentRequest) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/authorize`,
      data: paymentRequest
    })
  },
  deposit: function (paymentInstrumentId, paymentRequest) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/deposit`,
      data: paymentRequest
    })
  },
  credit: function (paymentInstrumentId, paymentRequest) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/credit`,
      data: paymentRequest
    })
  },
  purchase: function (paymentInstrumentId, paymentRequest) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/purchase`,
      data: paymentRequest
    })
  },
  capture: function (paymentInstrumentId, paymentId) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/payments/${paymentId}/capture`
    })
  },
  cancel: function (paymentInstrumentId, paymentId) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/payments/${paymentId}/cancel`
    })
  },
  reversal: function (paymentInstrumentId, paymentId) {
    return paymentClientRepository({
      method: 'post',
      url: `payment-instruments/${paymentInstrumentId}/payments/${paymentId}/reversal`
    })
  }
}
