<template>
  <div class="home">
    <p> Selected api is: {{selectedApi}}</p>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'home',
  props: {
    'selectedApi': String
  }
}
</script>
