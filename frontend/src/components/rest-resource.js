import axios from 'axios/index'

export const baseUrl = 'http://localhost:8080'
const apiRoot = baseUrl + '/api/'

const paymentClientRepository = axios.create({
  baseURL: apiRoot,
  headers: {
    'Access-Control-Allow-Origin': baseUrl
  }
})

export const merchantService = {
  listMerchants: function () {
    return paymentClientRepository({
      method: 'get',
      url: 'merchants/'
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
      url: 'payment-instruments'
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
