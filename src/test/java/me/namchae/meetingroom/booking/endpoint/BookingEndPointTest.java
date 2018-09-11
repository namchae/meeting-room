package me.namchae.meetingroom.booking.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest

@RunWith(SpringJUnit4ClassRunner.class)
public class BookingEndPointTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private BookingTime correctBookingTime;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        correctBookingTime = BookingTime.builder()
                .startTime(LocalTime.of(11, 0))
                .endTime(LocalTime.of(12, 0)).build();
    }

    private String objectToJson(Object inObject) throws JsonProcessingException {
        return objectMapper.writeValueAsString(inObject);
    }

    @Test
    public void 요청검증_시작시간_10분일때_실패() throws Exception {
        // given
        LocalTime startTime = LocalTime.of(11, 10);
        LocalTime endTime = LocalTime.of(12, 0);

        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(LocalDate.now())
                .bookingTime(BookingTime.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .build()
                )
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_시작시간_종료시간_반대일_경우_실패() throws Exception {
        // given
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(LocalDate.now())
                .bookingTime(BookingTime.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .build()
                )
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_과거날짜_요청시_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now().minusDays(1);
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_회의실이름_없을경우_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_예약자명_1자리_경우_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_예약자명_10자리_넘을경우_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희희희희희희희희희")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_반복횟수_음수일_경우_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(-2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 요청검증_반복횟수_0일_경우_실패() throws Exception {
        // given
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(0)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회의실_예약_요청_성공() throws Exception {
        // given
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(LocalDate.now().plusDays(3))
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent));
    }


    private String expectContent = "" +
            "{" +
                "\"booker\":\"남채희\"," +
                "\"roomType\":\"회의실F\"," +
                "\"bookingTime\":" +
                    "{\"startTime\":\"11:00:00\"," +
                    "\"endTime\":\"12:00:00\"}," +
                "\"repetitionCount\":2" +
            "}";

    @Test
    public void 당일_회의실_리스트_성공() throws Exception {
        // given
        LocalDate useDate = LocalDate.now().plusDays(3);
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(useDate)
                .bookingTime(correctBookingTime)
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent));

        // when then
        mockMvc.perform(get("/bookings/" + useDate).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[" + expectContent + "]"));
    }
}