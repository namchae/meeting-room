<template>
    <div class="cd-schedule js-full">
        <div class="timeline">
            <ul>
                <li><span>09:00</span></li>
                <li><span>09:30</span></li>
                <li><span>10:00</span></li>
                <li><span>10:30</span></li>
                <li><span>11:00</span></li>
                <li><span>11:30</span></li>
                <li><span>12:00</span></li>
                <li><span>12:30</span></li>
                <li><span>13:00</span></li>
                <li><span>13:30</span></li>
                <li><span>14:00</span></li>
                <li><span>14:30</span></li>
                <li><span>15:00</span></li>
                <li><span>15:30</span></li>
                <li><span>16:00</span></li>
                <li><span>16:30</span></li>
                <li><span>17:00</span></li>
                <li><span>17:30</span></li>
                <li><span>18:00</span></li>
            </ul>
        </div>
        <div class="events">
            <ul>
                <li class="events-group">
                    <div class="top-info"><span>회의실A</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomA"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실B</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomB"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실C</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomC"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실D</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomD"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실E</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomE"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실F</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomF"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
                <li class="events-group">
                    <div class="top-info"><span>회의실G</span></div>
                    <ul>
                        <Event
                                v-for="event in EventVo.roomG"
                                :key="event.startTime"
                                :startTime="event.startTime"
                                :endTime="event.endTime"
                                :content="event.content" />
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
  import Event from './Event.vue'

  export default {
    name: 'TimeLine',
    data() {
      return {
        EventVo: {
          roomA: [
            {
              startTime: '09:30',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomB: [
            {
              startTime: '10:00',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomC: [
            {
              startTime: '10:30',
              endTime: '11:00',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomD: [
            {
              startTime: '09:30',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomE: [
            {
              startTime: '09:30',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomF: [
            {
              startTime: '09:30',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
          roomG: [
            {
              startTime: '09:30',
              endTime: '10:30',
              content: '예약1'
            },
            {
              startTime: '11:30',
              endTime: '12:30',
              content: '예약2'
            },
          ],
        }
      }
    },
    computed: {
//    forSale() { return this.$store.getters.forSale; },
    },
    components: {
      Event
    },
  };
</script>