# kakaopay-meeting-room
> 2018 카카오페이 경력 서버 과제
- 회의실과 날짜, 사용 시간을 입력하여 회의실을 예약하는 회의실 예약 애플리케이션 개발.

## Implementation contents
1. Backend
  - 예약 API : 회의실 예약, 일자별 회의실 리스트
   
2. Front
  - 예약하기
  - 일자별 회의실 확인

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
config
    server {
       listen       80;
       # vue-ui
       location / {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8080;
       }
       # api
       location /products {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/products;
       }
       # api
       location /members {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/members;
       }
       # api
       location /orders {
           proxy_set_header   X-Real-IP $remote_addr;
           proxy_set_header   Host      $http_host;
           proxy_pass         http://127.0.0.1:8081/orders;
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
# 터미널 환경.
./gradlew bootRun

# 인텔리J
1. Sync gradle
2. Run IxOrderApplication
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
    │      └──MeetingRoomApplication---> * BootApplication.
    └── test                 -------> # testCase

```
----
## API Specifications
URL : http://localhost:8081:swagger-ui.html
* 각 API Request | Response Example Value 및 Model 참고.
----