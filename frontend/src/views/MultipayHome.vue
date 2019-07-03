<template>
  <div class="multipayhome">

    <RequestForm/>
    <p> Selected api is: {{selectedApi}}</p>
  </div>
</template>

<script>
  // @ is an alias to /src
  import CardList from '@/components/CardList.vue'
  import RequestForm from '@/components/RequestForm'

  export default {
    name: 'multipayhome',
    components: {
      RequestForm,
      CardList,
    },
    props: ["selectedApi"]
  }
</script>
