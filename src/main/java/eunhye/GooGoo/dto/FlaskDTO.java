package eunhye.GooGoo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlaskDTO { // 구글 자동 로그인을 위한 구글 비밀번호
    private Long id;
    private String googlePassword;
}
