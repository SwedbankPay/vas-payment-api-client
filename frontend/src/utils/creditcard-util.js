export const maskPan = function (clearPan) {
  if (!clearPan) { return clearPan } else { return clearPan.substr(0, 6) + '*********' + clearPan.substr(0, 4) }
}
export const formatNumber = function (number) {
  if (!number) return ''
  return new Intl.NumberFormat(window.document.documentElement.lang, { minimumFractionDigits: 2 }).format(number)
}
