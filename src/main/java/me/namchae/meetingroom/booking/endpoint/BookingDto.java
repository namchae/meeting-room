package me.namchae.meetingroom.booking.endpoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.validator.NotReverseTime;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class BookingDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "BookingDto.CreateReq", description = "예약 요청 정보")
    public static class CreateReq {

        @NotBlank
        @ApiModelProperty(notes = "회의실_타입", required = true, example = "회의실A")
        private String roomType;

        @NotBlank
        @Size(min = 2, max = 10)
        @ApiModelProperty(notes = "예약자명", required = true, example = "남채희")
        private String booker;

        @NotNull
        @FutureOrPresent
        @ApiModelProperty(notes = "예약날짜[현재또는 미래]", required = true, example = "2018-09-08")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate useDate;

        @Valid
        @NotReverseTime
        @ApiModelProperty(notes = "예약 시간정보", required = true)
        private BookingTime bookingTime;

        @Positive
        @ApiModelProperty(notes = "반복횟수", required = true, example = "3")
        private int repetitionCount;

        @Builder
        public CreateReq(String roomType, String booker, LocalDate useDate, BookingTime bookingTime, int repetitionCount) {
            this.roomType = roomType;
            this.booker = booker;
            this.useDate = useDate;
            this.bookingTime = bookingTime;
            this.repetitionCount = repetitionCount;
        }

        public Booking toEntity() {
            Booking booking = Booking.builder().booker(this.booker)
                    .repetitionCount(this.repetitionCount)
                    .build();
            booking.addTimeLine(this);
            return booking;
        }

        public BookingTimeLine toTimeLine(Booking booking, LocalDateTime useDateTime) {
            return BookingTimeLine.builder()
                    .booking(booking)
                    .useDateTime(useDateTime)
                    .roomType(roomType)
                    .build();
        }

        public List<LocalDate> repetitionDate() {
            LocalDate inUseDate = this.useDate;

            List<LocalDate> repetitionDateList = Lists.newArrayList();
            repetitionDateList.add(inUseDate);
            for (int i = 1; i < this.repetitionCount; i++) {
                inUseDate = inUseDate.plusDays(7);
                repetitionDateList.add(inUseDate);
            }
            return repetitionDateList;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "BookingDto.ListReq", description = "예약 리스트 요청")
    public static class ListReq {

        @NotNull
        @ApiModelProperty(notes = "예약 날짜", required = true, example = "2018-09-03")
        private LocalDate useDate;

        @Builder
        public ListReq(LocalDate useDate) {
            this.useDate = useDate;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "BookingDto.Response", description = "예약 리스트 요청")
    public static class Response {

        @ApiModelProperty(notes = "예약자명", required = true, example = "남채희")
        private String booker;

        @ApiModelProperty(notes = "회의실_타입", required = true, example = "회의실A")
        private String roomType;

        @ApiModelProperty(notes = "예약 시간정보", required = true)
        private BookingTime bookingTime;

        @ApiModelProperty(notes = "반복횟수", required = true, example = "3")
        private int repetitionCount;

        @Builder
        public Response(Booking booking) {
            this.booker = booking.getBooker();

            List<BookingTimeLine> timeLines = booking.getBookingTimeLines();
            BookingTimeLine firstTimeLine = timeLines.get(0);
            BookingTimeLine finalTimeLine = timeLines.get(timeLines.size() - 1);

            this.roomType = firstTimeLine.getRoomType();

            this.bookingTime = BookingTime.builder()
                    .startTime(firstTimeLine.getUseDateTime().toLocalTime())
                    .endTime(finalTimeLine.getUseDateTime().toLocalTime().plusMinutes(30))
                    .build();

            this.repetitionCount = booking.getRepetitionCount();
        }
    }
}
