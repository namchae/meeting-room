package me.namchae.meetingroom.booking.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ThirtyMinuteValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ThirtyMinute {
    String message() default "요청한 시간은 처리할 수 없습니다. 30분단위로 요청해주세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}