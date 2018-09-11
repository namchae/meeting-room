<template>
    <li class="single-event" data-event="event-1" v-bind:style="{top: eventTop + 'px', height: eventHeigth + 'px'}">
        <a nohref>
            <span class="event-date">{{startTime}} - {{endTime}}</span>
            <em class="event-name">{{content}}</em>
        </a>
    </li>
</template>
<script>
  export default {
    name: 'Event',
    props: ['startTime', 'endTime', 'content'],
    computed: {
      eventTop() {
        let baseTime = this.moment('2000-01-01 09:00', 'YYYY-MM-DD HH:mm' )
        let inTime = this.moment('2000-01-01 ' + this.startTime, 'YYYY-MM-DD HH:mm' )
        let term = this.moment.duration(inTime.diff(baseTime)).asMinutes()
        return term / 30 * 50 - 1
      },
      eventHeigth() {
        let minTime = this.moment('2000-01-01 ' + this.startTime, 'YYYY-MM-DD HH:mm')
        let maxTime = this.moment('2000-01-01 ' + this.endTime, 'YYYY-MM-DD HH:mm')
        let term = this.moment.duration(maxTime.diff(minTime)).asMinutes()
        return term / 30 * 50 + 1
      }
    },
  };
</script>