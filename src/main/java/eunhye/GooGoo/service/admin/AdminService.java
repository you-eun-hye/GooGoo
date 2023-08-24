package eunhye.GooGoo.service.admin;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.entity.UserRole;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    // 회원 조회
    public List<UserDTO> findAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.USER){
                userDTOList.add(UserDTO.toUserDTO(userEntity));
            }
        }
        return userDTOList;
    }

    // 관리자 조회
    public List<UserDTO> findAdminAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getAuthority() == UserRole.ADMIN){
                userDTOList.add(UserDTO.toUserDTO(userEntity));
            }
        }
        return userDTOList;
    }
}
