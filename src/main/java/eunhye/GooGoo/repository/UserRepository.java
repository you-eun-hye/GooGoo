package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 이메일로 회원 정보 조회(select * from user where user_email=?)
    UserEntity findByUserEmail(String userEmail);

    // 아이디 찾기에 쓰이는 닉네임 조회
    UserEntity findByUserNickname(String userNickname);

    /* 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserEmail(String userEmail);
}