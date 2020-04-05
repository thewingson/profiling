package kz.almat.profiling.repo;

import kz.almat.profiling.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Almat on 27.03.2020
 */

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
}
