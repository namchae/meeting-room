<template>
    <div id="app" class="col-md-12">
        <UserAction />
        <TimeLine />
    </div>
</template>

<script>
import TimeLine from './TimeLine.vue'
import UserAction from './UserAction.vue'

export default {
  name: 'app',
  computed: {
//    forSale() { return this.$store.getters.forSale; },
  },
  components: {
    TimeLine,
    UserAction
  },
};
</script>
