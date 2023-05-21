package eunhye.GooGoo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String userEmail;
    private String userPassword;
}
