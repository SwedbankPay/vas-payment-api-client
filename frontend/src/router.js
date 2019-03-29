import VueRouter from 'vue-router'
import Home from './views/Home.vue'

// import CreateAccount from './components/createAccount.vue'
// import EditAccount from './components/editAccount.vue'
import CardList from './views/CardList.vue'
import PaymentInstrument from './views/PaymentInstrument.vue'

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [

    // {
    //   name: 'CreateAccount',
    //   path: '/create',
    //   component: CreateAccount
    // },
    // {
    //   name: 'EditAccount',
    //   path: '/edit',
    //   component: EditAccount
    // },
    {
      name: 'CardList',
      path: '/cards',
      component: CardList,
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
    }
  ]
})
