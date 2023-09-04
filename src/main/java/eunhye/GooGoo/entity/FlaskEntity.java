package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.FlaskDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "FLASK")
@NoArgsConstructor
@AllArgsConstructor
public class FlaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String googlePassword;

    public static FlaskEntity toSaveEntity(FlaskDTO flaskDTO){
        FlaskEntity flaskEntity = new FlaskEntity();
        flaskEntity.googlePassword = flaskDTO.getGooglePassword();
        return flaskEntity;
    }
}
