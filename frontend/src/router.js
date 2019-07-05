import VueRouter from 'vue-router'
import Home from './views/Home.vue'
import Card from './views/Card.vue'

import PaymentInstrument from './views/PaymentInstrument.vue'
import About from './views/About'
import AboutMultipay from './views/AboutMultipay'
import Merchants from './views/Merchants'
import CreateOrder from './views/CreateOrder'
import Products from './views/Products'

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      name: 'CardList',
      path: '/cards',
      component: Card,
      props: true
    },
    {
      name: 'PaymentInstrument',
      path: '/cards/:id',
      component: PaymentInstrument,
      props: true
    },
    {
      name: 'Merchants',
      path: '/merchants',
      component: Merchants,
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
    },
    {
      path: '/aboutmultipay',
      name: 'aboutmultipay',
      component: AboutMultipay
    },
    {
      path: '/order',
      name: 'Order',
      component: CreateOrder
    },
    {
      path: '/products',
      name: 'Product',
      component: Products
    }
  ]
})
