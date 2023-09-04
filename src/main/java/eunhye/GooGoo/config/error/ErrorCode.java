package eunhye.GooGoo.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}

