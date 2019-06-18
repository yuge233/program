package cn.group.program.repository;

import cn.group.program.model.Describe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescribeRepository extends JpaRepository<Describe,Long> {
    List<Describe> findByOwn(Long own);
}
