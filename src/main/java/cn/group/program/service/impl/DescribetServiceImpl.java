package cn.group.program.service.impl;

import cn.group.program.model.Describe;
import cn.group.program.model.Describet;
import cn.group.program.repository.DescribeRepository;
import cn.group.program.repository.DescribetRepository;
import cn.group.program.service.DescribeService;
import cn.group.program.service.DescribetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescribetServiceImpl implements DescribetService {
    @Autowired
    private DescribetRepository repository;

    public List<Describet> findByOwn(Long own){
        return repository.findByOwn(own);
    }

    public void save(Describet describet) {
        Describet describe1=repository.save(describet);
        System.out.println(describet.getId());
    }
}
