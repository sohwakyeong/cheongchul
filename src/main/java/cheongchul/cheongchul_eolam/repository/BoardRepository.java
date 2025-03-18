package cheongchul.cheongchul_eolam.repository;
import cheongchul.cheongchul_eolam.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findByCategory(String category, Pageable pageable);
    Page<Board> findByTitleContaining(String title,Pageable pageable);
    Page<Board> findByTitleContainingAndCategory(String title, String category, Pageable pageable);
    Page<Board> findAll(Pageable pageable);
}
