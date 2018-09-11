package me.namchae.meetingroom.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    private String requestParseString(BookingDto.CreateReq createReq) throws JsonProcessingException {
        return objectMapper.writeValueAsString(createReq);
    }

    @Test
    public void 시작시간_10분으로_요청_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 10))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 시작시간_종료시간_반대일_경우_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(14, 00))
                .endTime(LocalTime.of(13, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 과거날짜_요청시_실패() throws Exception {
        LocalDate useDate = LocalDate.now().minusDays(1);
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회의실명_없을경우_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 예약자명_1자리_경우_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 예약자명_10자리_넘을경우_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희희희희희희희희희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 반복횟수_음수일_경우_실패() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희희희희희희희희희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(-2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 정상요청_성공() throws Exception {
        LocalDate useDate = LocalDate.now();
        BookingDto.CreateReq createReq = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실B")
                .useDate(useDate)
                .startTime(LocalTime.of(13, 00))
                .endTime(LocalTime.of(14, 00))
                .repetitionCount(2)
                .build();

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(requestParseString(createReq)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}