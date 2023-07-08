package eunhye.GooGoo.service;

import eunhye.GooGoo.dto.UserDTO;
import eunhye.GooGoo.entity.UserEntity;
import eunhye.GooGoo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;

    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

//    public UserDTO login(UserDTO userDTO) {
//        /*
//            1. 회원이 입력한 이메일로 DB에서 조회
//            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
//        */
//        Optional<UserEntity> byUserEmail = userRepository.findByUserEmail(userDTO.getUserEmail());
//        if(byUserEmail.isPresent()){
//            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
//            UserEntity userEntity = byUserEmail.get();
//            if(userEntity.getUserPassword().equals(userDTO.getUserPassword())){
//                // 비밀번호 일치
//                // Entity -> DTO 변환 후 리턴
//                UserDTO dto = UserDTO.toUserDTO(userEntity);
//                return dto;
//            }else{
//                // 비밀번호 불일치
//                return null;
//            }
//        }else{
//            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
//            return null;
//        }
//    }

    public UserDTO selectUser(Long id){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        return UserDTO.toUserDTO((optionalUserEntity.get()));
    }

    public void edit(UserDTO userDTO) {

        userRepository.save(UserEntity.toEditUserEntity(userDTO));
    }

    public void deleteById(Long id) {

        userRepository.deleteById(id);
    }
}
