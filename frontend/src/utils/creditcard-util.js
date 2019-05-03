import moment from 'moment'

export const maskPan = function (clearPan) {
  if (!clearPan) { return clearPan } else { return clearPan.substr(0, 6) + '*********' + clearPan.substr(clearPan.length - 4, 4) }
}
export const formatNumber = function (number) {
  if (!number) return ''
  return new Intl.NumberFormat(window.document.documentElement.lang, { minimumFractionDigits: 2 }).format(number)
}
export const formatDate = function (date) {
  if (date) { return moment(String(date)).format('DD-MM-YY HH:mm') }
}
export const toastError = function (error) {
  if (error.response.data) {
    console.log(error.response.data)
    const message = '<span><p>Status: ' + error.response.status + ' - ' + error.response.data.error + '</p></span>' +
      (error.response.data.message === 'No message available' ? '' : '<span><p>Message: ' + error.response.data.message + '</p></span>') +
      '<span><p>Path: ' + error.response.data.path + '</p></span>'

    px.toast({ html: message, type: 'danger' })
  } else {
    px.toast({ html: 'Oops, something went wrong.. ', type: 'danger' })
  }
}
