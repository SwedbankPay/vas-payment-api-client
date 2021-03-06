import axios from 'axios/index'

export const baseUrl = 'http://localhost:8080';
const apiRoot = baseUrl + '/api/';

const paymentClientRepository = axios.create({
  baseURL: apiRoot,
  headers: {
    'Access-Control-Allow-Origin': baseUrl,
    'Content-Type': 'application/json'
  }
});

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
};

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
};

export const paymentOperationService = {

  listPayments: function (paymentInstrumentId) {
    return paymentClientRepository({
      method: 'get',
      url: `payment-instruments/${paymentInstrumentId}/payments`
    })
  },
  balance: function (paymentInstrumentId, agreementId) {
    return paymentClientRepository({
      method: 'get',
      url: `payment-instruments/${paymentInstrumentId}/balance`,
      params: {
        agreementId: agreementId
      }
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
};

export const paymentGiftCardService = {

  getGiftCard: function (giftCard) {
    return paymentClientRepository({
      method: 'post',
      url: 'gift-card',
      data: giftCard
    })
  },
  preDeposit: function (giftCard) {
    return paymentClientRepository({
      method: 'post',
      url: 'gift-card/pre-deposit',
      data: giftCard
    })
  }
};

export const pingService = {
  ping: function (pingRequest) {
    return paymentClientRepository({
      method: 'post',
      url: 'ping',
      data: pingRequest
    })
  }
};
