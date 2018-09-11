package me.namchae.meetingroom.booking.endpoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import me.namchae.meetingroom.booking.validator.ThirtyMinute;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@ApiModel(value = "BookingDto.CreateReq.NotReverseTime", description = "예약 시간 정보")
public class BookingTime {

    @NotNull
    @ThirtyMinute
    @ApiModelProperty(notes = "예약 시작시간", required = true, example = "11:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime startTime;

    @NotNull
    @ThirtyMinute
    @ApiModelProperty(notes = "예약 종료시간", required = true, example = "12:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime endTime;

    @Builder
    public BookingTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
