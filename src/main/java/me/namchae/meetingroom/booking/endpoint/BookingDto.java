package me.namchae.meetingroom.booking.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

        @FutureOrPresent
        @ApiModelProperty(notes = "예약날짜[현재또는 미래]", required = true, example = "2018-09-08")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate useDate;

        @NotNull
        @ApiModelProperty(notes = "예약_시작시간", required = true, example = "11:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        private LocalTime startTime;

        @NotNull
        @ApiModelProperty(notes = "예약_종료시간", required = true, example = "12:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        private LocalTime endTime;

        @Positive
        @ApiModelProperty(notes = "반복횟수", required = true, example = "3")
        private int repetitionCount;

        @Builder
        public CreateReq(String roomType, String booker, LocalDate useDate, LocalTime startTime, LocalTime endTime, int repetitionCount) {
            this.roomType = roomType;
            this.booker = booker;
            this.useDate = useDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.repetitionCount = repetitionCount;
        }

        public Booking toEntity() {
            return Booking.builder().booker(this.booker)
                    .repetitionCount(this.repetitionCount)
                    .build();
        }

        BookingTimeLine toTimeLine(Booking booking, LocalDateTime useDateTime, LocalTime time) {
            return BookingTimeLine.builder()
                    .booking(booking)
                    .useDateTime(useDateTime)
                    .roomType(roomType)
                    .build();
        }

        List<LocalDate> getRepetitionDate() {
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
    @ApiModel(value = "BookingDto.Response", description = "예약 리스트 요청")
    public static class Response {

        @ApiModelProperty(notes = "예약자명", required = true, example = "남채희")
        private String booker;

        @ApiModelProperty(notes = "회의실_타입", required = true, example = "회의실A")
        private String roomType;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(notes = "예약_시작시간", required = true, example = "11:00:00")
        private LocalTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(notes = "예약_종료시간", required = true, example = "12:00:00")
        private LocalTime endTime;

//        @ApiModelProperty(notes = "예약_타임리스트", required = true)
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
//        private List<LocalDateTime> bookingDateList = Lists.newArrayList();

        private int repetitionCount;

        public Response(Booking booking) {
            this.booker = booking.getBooker();

            List<BookingTimeLine> timeLines = booking.getBookingTimeLines();
            BookingTimeLine firstTimeLine = timeLines.get(0);
            BookingTimeLine finalTimeLine = timeLines.get(timeLines.size() - 1);

            this.roomType = firstTimeLine.getRoomType();

            this.startTime = firstTimeLine.getUseDateTime().toLocalTime();
            this.endTime = finalTimeLine.getUseDateTime().toLocalTime().plusMinutes(30);

            this.repetitionCount = booking.getRepetitionCount();

//            this.bookingDateList = timeLines.stream()
//                    .map(BookingTimeLine::getUseDateTime)
//                    .distinct()
//                    .collect(Collectors.toList());
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
}
