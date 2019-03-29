import Vue from 'vue'
import VueRouter from 'vue-router'
import router from './router'

// import BootstrapVue from 'bootstrap-vue'
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'

import App from './App.vue'

Vue.use(VueRouter)
// Vue.use(BootstrapVue)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
