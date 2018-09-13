package me.namchae.meetingroom.booking.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@ExtendWith(SpringExtension.class)
public class BookingEndPointTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private BookingDto.CreateReq createReq1;

    private BookingDto.CreateReq createReq2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        createReq1 = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(LocalDate.now().plusDays(3))
                .bookingTime(BookingTime.builder()
                                        .startTime(LocalTime.of(11, 0))
                                        .endTime(LocalTime.of(12, 0)).build())
                .repetitionCount(2)
                .build();

        createReq2 = BookingDto.CreateReq.builder().booker("남채희")
                .roomType("회의실F")
                .useDate(LocalDate.now().plusDays(3))
                .bookingTime(BookingTime.builder()
                                        .startTime(LocalTime.of(12, 0))
                                        .endTime(LocalTime.of(13, 0)).build())
                .repetitionCount(2)
                .build();
    }

    private String objectToJson(Object inObject) throws JsonProcessingException {
        return objectMapper.writeValueAsString(inObject);
    }

    @Test
    public void 회의실_예약_요청_성공() throws Exception {
        // given (before)

        // when then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent1));
    }


    private String expectContent1 = "" +
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
        // given (before)

        // when
        LocalDate useDate = LocalDate.now().plusDays(3);

        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent1));

        // then
        mockMvc.perform(get("/bookings/" + useDate).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json("[" + expectContent1 + "]"));
    }

    private String expectContent2 = "" +
            "{" +
            "\"booker\":\"남채희\"," +
            "\"roomType\":\"회의실F\"," +
            "\"bookingTime\":" +
            "{\"startTime\":\"12:00:00\"," +
            "\"endTime\":\"13:00:00\"}," +
            "\"repetitionCount\":2" +
            "}";


    @Test
    public void 종료시간과_시작시간이_겹치는_예약_테스트() throws Exception {
        // given (before)

        // when
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent1));

        // then
        mockMvc.perform(post("/bookings").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createReq2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(expectContent2));
    }
}