package eunhye.GooGoo.dto;

import lombok.*;
import org.checkerframework.checker.units.qual.A;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FlaskDTO {
    private Long id;
    private String googlePassword;
}
