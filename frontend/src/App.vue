<template>
  <div id="app">
    <div class="api">
      <SelectAPI @change-api="changeApi"/>
    </div>
    <div class="container">
      <div class="row">
        <main class="col main-content border-left border-right">
          <div v-if="selectedApi === 'payment-api'" id="nav">
            <router-link to="/">Home</router-link>
            |
            <router-link to="/cards">Cards</router-link>
            |
            <router-link to="/merchants">Merchants</router-link>
            |
            <router-link to="/about">About</router-link>
          </div>
          <div id="nav" v-if="selectedApi === 'multipay'">
            <router-link to="/">Home</router-link>
            |
            <router-link to="/order/create">Order</router-link>
            |
            <router-link to="/order/view">View Order</router-link>
            |
            <router-link to="/merchants">Merchants</router-link>
            |
            <router-link to="/products">Products</router-link>
            |
            <router-link to="/aboutmultipay">About</router-link>
          </div>
          <router-view :selectedApi="selectedApi"/>
        </main>
      </div>
    </div>

  </div>
</template>

<script>
import SelectAPI from '@/components/SelectAPI.vue'

export default {
  name: 'App',
  components: {
    SelectAPI
  },
  data () {
    return {
      selectedApi: sessionStorage.getItem('api') ? sessionStorage.getItem('api') : 'payment-api'
    }
  },
  methods: {
    changeApi: function (api) {
      this.selectedApi = api
      sessionStorage.setItem('api', api)
      this.$router.push('/')
    }
  }
}

</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }

  .api {
    float: right;
  }
</style>
