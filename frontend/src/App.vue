<template>
  <div id="app">
    <div class="api">
      <SelectAPI @change-api="changeApi"/>
    </div>
    <div v-if="selectedApi === 'payment-api'" id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/cards">Cards</router-link> |
      <router-link to="/merchants">Merchants</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <div id="nav" v-if="selectedApi === 'multipay'">
      <router-link to="/requestForm">Home</router-link> |
      <router-link to="/order">Order</router-link> |
      <router-link to="/merchants">Merchants</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view :selectedApi="selectedApi"/>
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
      selectedApi: sessionStorage.getItem('api') ? sessionStorage.getItem() : 'payment-api'
    }
  },
  methods: {
    changeApi: function (api) {
      this.selectedApi = api
      sessionStorage.setItem('api', api)
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
