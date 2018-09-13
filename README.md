# kakaopay-meeting-room
> 2018 카카오페이 경력 서버 과제
- 회의실과 날짜, 사용 시간을 입력하여 회의실을 예약하는 회의실 예약 애플리케이션 개발.

## Implementation contents
1. Backend
  - 예약 API : 회의실 예약, 일자별 회의실 리스트
   
2. Front
  - 예약하기
  - 일자별 회의실 확인
## Requirement
* 웹 어플리케이션으로 개발 
* 웹어플리케이션 개발언어는 Java, Scala, Golang 중 선택을 권장함 (단, 다른 언어에 특별히 자신있는 경우 선택에 제한을 두지 않음)
* 서버는 REST API로 구현
* 프론트엔드 구현방법은 제약 없음
* 데이타베이스는 사용에 제약 없음 (가능하면 In-memory db 사용)
* 단위테스트 필수, 통합테스트는 선택
* README.md 파일에 문제해결 전략 및 프로젝트 빌드, 실행 방법 명시

## Solution Strategies
- 데이터 유효성 체크.
    - Front-end : 예약 등록 (submit) 전에 확인.
    - Back-end 
      - EndPoint에서 javax.validation 및 Custom Validator를 이용한 유효성 체크.
      - 예외발생 시, @ControllerAdvice에서 ErrorResponse 리턴.

- 예약 시간 관리.
    - 예약 시작시간 기준으로 30분단위 범위로 TimeLine을 생성. (ex: 1시간이면, TimeLine 2개, 2시간이면, TimeLine 4개.)
    - 예약 시작시간(use_date_Time)+회의실타입(room_type)을 Unique 제약조건을 적용하여, 중첩예약 발생 안하도록 적용.
     
### Stack
- Back-End Framework : Spring Boot
- Front-End Framework : Vue.js
- DBMS : H2

### Prerequisites
- NPM
- Java 1.8.x
- Lombok plugin

### Run in development
#### Run Proxy server [port:80]
- nginx 설정 수정.
```
server {
    listen       80;
    server_name  localhost;
        
    location / {
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   Host      $http_host;
        proxy_pass         http://127.0.0.1:8080;
    }
        
    location /bookings {
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   Host      $http_host;
        proxy_pass         http://127.0.0.1:8081/bookings;
    }
}
```
#### Run front server [port: 8080]
``` bash
# Change to frontend root directory
cd /src/main/vue-ui

# Serve in dev mode, with hot reload at localhost:8080
npm run dev
```

#### Run backend server [port: 8081]
``` bash
# Terminal
./gradlew bootRun

# IntelliJ
1. Sync gradle
2. Run MeetingRoomApplication
```

### Project package structure
``` bash
meeting-room
└── src
    ├── main
    │   └──me.namchae.meetingroom
    │      ├──booking        ----------> # 예약 API 정의
    │      ├──config         ----------> # 공통 스프링 환경설정 정의
    │      ├──exception      ----------> # global 예외 정의
    │      └──MeetingRoomApplication --> * BootApplication.
    ├── test                 ----------> # testCase
    └── vue-ui               ----------> # Front-end

```
----
## API Specifications
URL : http://localhost:8081:swagger-ui.html
* 각 API Request | Response Example Value 및 Model 참고.
----