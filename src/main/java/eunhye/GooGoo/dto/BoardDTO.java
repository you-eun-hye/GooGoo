package eunhye.GooGoo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime boardCreatedTime;
}
