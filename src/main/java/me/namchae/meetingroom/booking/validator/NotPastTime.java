package me.namchae.meetingroom.booking.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotPastTimeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPastTime {
    String message() default "시작시간이 현재보다 과거입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
