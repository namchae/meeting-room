package me.namchae.meetingroom.booking.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotReverseTimeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotReverseTime {
    String message() default "시작시간이 종료시간보다 앞서 있습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}