import VueRouter from 'vue-router'
import Home from './views/Home.vue'

import PaymentInstrument from './views/PaymentInstrument.vue'
import About from './views/About'
import Merchants from './views/Merchants'
import GiftCards from "./views/GiftCards";
import Ping from "./views/Ping";

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
      name: 'Merchants',
      path: '/merchants',
      component: Merchants,
      props: true
    },
    {
      name: 'GiftCards',
      path: '/giftcards',
      component: GiftCards,
      props: true
    },
    {
      name: 'Ping',
      path: '/ping',
      component: Ping,
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
