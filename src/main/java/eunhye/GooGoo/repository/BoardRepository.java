package eunhye.GooGoo.repository;

import eunhye.GooGoo.entity.BoardEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
//    @Override
//    @EntityGraph(attributePaths = {"userEntity"})
//    Optional<BoardEntity> findById(Long postId);
}
