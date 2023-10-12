package eunhye.GooGoo.entity;

import eunhye.GooGoo.dto.FlaskDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "FLASK")
@NoArgsConstructor
@AllArgsConstructor
public class FlaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String googlePassword;

    public static FlaskEntity toSaveEntity(FlaskDTO flaskDTO){
        FlaskEntity flaskEntity = new FlaskEntity();
        flaskEntity.googlePassword = flaskDTO.getGooglePassword();
        return flaskEntity;
    }
}
