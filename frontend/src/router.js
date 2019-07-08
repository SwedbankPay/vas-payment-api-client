import VueRouter from 'vue-router'
import Home from './views/Home.vue'
import Card from './views/Card.vue'
import PaymentInstrument from './views/PaymentInstrument.vue'
import About from './views/About'
import AboutMultipay from './views/AboutMultipay'
import Merchants from './views/Merchants'
import Products from './views/Products'
import CreateOrder from './views/CreateOrder'
import ViewOrder from './views/ViewOrder'

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
      path: '/products',
      name: 'Product',
      component: Products
    },
    {
      path: '/order/create',
      name: 'CreateOrder',
      component: CreateOrder
    },
    {
      path: '/order/view',
      name: 'ViewOrder',
      component: ViewOrder
    }
  ]
})
