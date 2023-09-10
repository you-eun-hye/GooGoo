package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 아이디로 회원 정보 조회
    UserEntity findById(UUID userId);

    // 이메일로 회원 정보 조회(select * from user where user_email=?)
    UserEntity findByUserEmail(String userEmail);

    // 닉네임으로 회원 정보 조회
    UserEntity findByUserNickname(String userNickname);

    /* 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    boolean existsByUserNickname(String userNickname); // 닉네임
    boolean existsByUserEmail(String userEmail); // 이메일

    // 회원 수
    @Query("SELECT COUNT(*) FROM UserEntity p WHERE p.authority = 'USER'")
    Long countUser();

    // 관리자 수
    @Query("SELECT COUNT(*) FROM UserEntity p WHERE p.authority = 'ADMIN'")
    Long countUAdmin();

    void deleteById(UUID userId);
}