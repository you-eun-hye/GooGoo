package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.FlaskDTO;
import eunhye.GooGoo.entity.FlaskEntity;
import eunhye.GooGoo.repository.FlaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlaskService {

    private final FlaskRepository flaskRepository;

    // 구글 비번 저장
    public void save(FlaskDTO flaskDTO){
        FlaskEntity flaskEntity = FlaskEntity.toSaveEntity(flaskDTO);
        flaskRepository.save(flaskEntity);
    }

    // 구글 비번 조회
    public String getGooglePassword(){
        FlaskEntity flaskEntity = flaskRepository.findAll().get(0);
        return flaskEntity.getGooglePassword();
    }

    // 구글 비번 삭제
    public void delete(){
        flaskRepository.deleteById(1L);
    }

}
