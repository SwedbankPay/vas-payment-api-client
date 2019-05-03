import VueRouter from 'vue-router'
import Home from './views/Home.vue'

import PaymentInstrument from './views/PaymentInstrument.vue'
import About from './views/About'

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      name: 'CardList',
      path: '/cards',
      component: Home,
      props: true
    },
    {
      name: 'PaymentInstrument',
      path: '/cards/:id',
      component: PaymentInstrument,
      props: true
    },
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      component: About
    }
  ]
})
