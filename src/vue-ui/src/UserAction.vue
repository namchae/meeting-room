<template>
    <div class="cd-schedule js-full">
        <span>{{BaseVo.useDay}}</span>
        <button class="btn btn-primary">이전일</button>
        <button class="btn btn-primary">다음일</button>
        <button class="btn btn-primary" v-on:click="Controller().openRegModal()">등록</button>
        <div class="modal d-block" v-if="ViewStatusVo.reg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Login</h5>
                    </div>
                    <div class="modal-body">
                        <p class="text-danger" v-if="ErrorVo.hasError">{{ErrorVo.message}}</p>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th>ID</th>
                                <td><input type="text" v-model="RegModalVo.id"></td>
                            </tr>
                            <tr>
                                <th>예약일</th>
                                <td><input type="text" v-model="RegModalVo.useDay"></td>
                            </tr>
                            <tr>
                                <th>시작시간</th>
                                <td><input type="text" v-model="RegModalVo.startTime"></td>
                            </tr>
                            <tr>
                                <th>종료시간</th>
                                <td><input type="text" v-model="RegModalVo.endTime"></td>
                            </tr>
                            <tr>
                                <th>회의실</th>
                                <td>
                                    <select v-model="RegModalVo.roomType">
                                        <option value="roomA">회의실A</option>
                                        <option value="roomB">회의실B</option>
                                        <option value="roomC">회의실C</option>
                                        <option value="roomD">회의실D</option>
                                        <option value="roomE">회의실E</option>
                                        <option value="roomF">회의실F</option>
                                        <option value="roomG">회의실G</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>반복횟수</th>
                                <td><input type="text" v-model="RegModalVo.repeatCnt"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" v-on:click="Controller().submitReg()">등록</button>
                        <button class="btn btn-primary" v-on:click="Controller().closeRegModal()">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
  export default {
    name: 'UserAction',
    data() {
      return {
        BaseVo: {
          useDay: ''
        },
        ViewStatusVo: {
          reg: false
        },
        RegModalVo: {
          id: '',
          roomType: 'roomA',
          useDay: '',
          startTime: '',
          endTime: '',
          repeatCnt: 1
        },
        ErrorVo: {
          hasError: false,
          message: ''
        }
      }
    },
    mounted: function () {
        this.DataBind().setUseDay(this.moment().format('YYYY-MM-DD'))
    },
    methods: {
      Controller() {
        let self = this
        return {
          closeRegModal() {
            self.DataBind().initRegModal()
            self.ViewStatusVo.reg = false
          },
          openRegModal() {
            self.DataBind().initRegModal()
            self.ViewStatusVo.reg = true
          },
          submitReg() {
            self.ErrorVo.hasError = false
            if ( self.RegModalVo.id === '' ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = 'ID가 비어 있습니다.'
            } else if ( !self.moment(self.RegModalVo.useDay, 'YYYY-MM-DD', true).isValid() ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '예약일은 YYYY-MM-DD 형식이 어야 합니다.'
            } else if ( !self.moment('2000-01-01 ' + self.RegModalVo.startTime, 'YYYY-MM-DD HH:mm', true).isValid() ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '시작 시간은 HH:mm 형식이 어야 합니다.'
            } else if ( !self.moment('2000-01-01 ' + self.RegModalVo.endTime, 'YYYY-MM-DD HH:mm', true).isValid() ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '시작 시간은 HH:mm 형식이 어야 합니다.'
            } else if ( isNaN(self.RegModalVo.repeatCnt) ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '반복 횟수는 숫자만 허용 합니다.'
            } else if ( self.RegModalVo.repeatCnt < 1 ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '반복 횟수는 1번 이상 입니다.'
            }
            let startIn = self.moment('2000-01-01 ' + self.RegModalVo.startTime, 'YYYY-MM-DD HH:mm' )
            let startCheck = startIn.format('mm')
            let endIn = self.moment('2000-01-01 ' + self.RegModalVo.endTime, 'YYYY-MM-DD HH:mm' )
            let endCheck = endIn.format('mm')
            if ( !(startCheck === '00' || startCheck === '30') ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '시작 시간은 매시 정각 또는 30분 입니다.'
            } else if ( !(endCheck === '00' || endCheck === '30') ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '종료 시간은 매시 정각 또는 30분 입니다.'
            } else if ( !startIn.isBefore(endIn) ) {
              self.ErrorVo.hasError = true
              self.ErrorVo.message = '시작 시간은 종료 시간이전 이어야 합니다.'
            }
            console.log('check', startIn.isBefore(endIn), startIn, endIn, startCheck, endCheck)
            if ( !self.ErrorVo.hasError ) {
              self.Dao().reg.excute()
            }
          }
        }
      },
      Dao () {
        let self = this
        return {
          reg: {
            excute() {
              console.log('등록하러가자')
/*
              let sendObj = self.LoginVo
              self.axios.post('/members/login', sendObj, null)
                .then(response => {
                  self.Dao().reg.complate(response)
                })
                .catch(error => {
                  self.Dao().reg.error(error)
                })
*/
            },
            error(error) {

            },
            complate(responseData) {

            }
          },
          list: {

          }
        }
      },
      DataBind () {
        let self = this
        return {
          initRegModal() {
            self.RegModalVo.id = '',
            self.RegModalVo.roomType = 'roomA'
            self.RegModalVo.useDay = ''
            self.RegModalVo.startTime = ''
            self.RegModalVo.endTime = ''
            self.RegModalVo.repeatCnt = 1
          },
          setUseDay(inDay) {
            self.BaseVo.useDay = inDay
          }
        }
      }
    }
  }
</script>