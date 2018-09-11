package me.namchae.meetingroom.booking.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.BookingService;
import me.namchae.meetingroom.booking.domain.Booking;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api("예약 API")
@RequestMapping("bookings")
@RestController
@RequiredArgsConstructor
public class BookingEndPoint {

    private final BookingService bookingService;

    @ApiOperation(value = "회의실 예약 요청", notes = "요청된 예약 정보를 등록")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto.Response bookingExecute(
            @ApiParam(value = "예약 요청 정보", required = true) @Valid @RequestBody BookingDto.CreateReq createReq) {
        log.info("request : {}", createReq);
        return new BookingDto.Response(bookingService.execute(createReq));
    }

    @ApiOperation(value = "회의실 예약 리스트 요청", notes = "예약 정보 리스트 반환")
    @GetMapping("/{useDate}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto.Response> bookingList(
            @ApiParam(value = "리스트 기준일", required = true, example = "2018-09-11") @PathVariable("useDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate useDate) {
        List<Booking> bookings = bookingService.list(useDate);
        log.info("[{}] list : {}", useDate, bookings);
        return bookings.stream()
                .map(BookingDto.Response::new)
                .collect(Collectors.toList());
    }
}
