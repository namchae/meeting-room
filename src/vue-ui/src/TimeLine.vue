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
  import { eventBus } from './main.js'

  export default {
    name: 'TimeLine',
    methods: {
      DataBind() {
        let self = this
        return {
          setEvent(eventData) {
            self.EventVo.roomA = []
            self.EventVo.roomB = []
            self.EventVo.roomC = []
            self.EventVo.roomD = []
            self.EventVo.roomE = []
            self.EventVo.roomF = []
            self.EventVo.roomG = []
            for ( let i in eventData ) {
              let tmpEvent = {}
              tmpEvent.startTime = eventData[i].bookingTime.startTime.substring(0,5)
              tmpEvent.endTime = eventData[i].bookingTime.endTime.substring(0,5)
              tmpEvent.content = eventData[i].booker + '-반복[' + eventData[i].repetitionCount + ']'
              if ( eventData[i].roomType === "회의실A" ) {
                self.EventVo.roomA.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실B" ) {
                self.EventVo.roomB.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실C" ) {
                self.EventVo.roomC.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실D" ) {
                self.EventVo.roomD.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실E" ) {
                self.EventVo.roomE.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실F" ) {
                self.EventVo.roomF.push(tmpEvent)
              } else if ( eventData[i].roomType === "회의실G" ) {
                self.EventVo.roomG.push(tmpEvent)
              }
            }
          }
        }
      }
    },
    data() {
      return {
        EventVo: {
          roomA: [],
          roomB: [],
          roomC: [],
          roomD: [],
          roomE: [],
          roomF: [],
          roomG: [],
        }
      }
    },
    created() {
      let self = this
      eventBus.$on('DataBind.events', function (eventData) {
        self.DataBind().setEvent(eventData)
      })
    },
    components: {
      Event
    },
  };
</script>