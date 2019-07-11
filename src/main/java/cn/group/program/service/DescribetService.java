package cn.group.program.service;

import cn.group.program.model.Describe;
import cn.group.program.model.Describet;

import java.util.List;

public interface DescribetService {
    List<Describet> findByOwn(Long own);
    void save(Describet describet);

}
