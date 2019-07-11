package cn.group.program.repository;

import cn.group.program.model.Describe;
import cn.group.program.model.Describet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescribetRepository extends JpaRepository<Describet,Long> {
    List<Describet> findByOwn(Long own);
}
