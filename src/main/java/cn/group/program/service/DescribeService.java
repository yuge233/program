package cn.group.program.service;

import cn.group.program.model.Describe;

import java.util.List;

public interface DescribeService {
    List<Describe> findByOwn(Long own);
    void save(Describe describe);

}
