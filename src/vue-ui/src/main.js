import Vue from 'vue';
import App from './App.vue';
import store from './store';
import moment from 'moment'
import axios from 'axios'

Vue.prototype.axios = axios
Vue.prototype.moment = moment

export const eventBus = new Vue()
/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  render: h => h(App),
});
