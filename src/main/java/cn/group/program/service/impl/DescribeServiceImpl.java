package cn.group.program.service.impl;

import cn.group.program.model.Describe;
import cn.group.program.repository.DescribeRepository;
import cn.group.program.service.DescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescribeServiceImpl implements DescribeService {
    @Autowired
    private DescribeRepository repository;

    public List<Describe> findByOwn(Long own){
        return repository.findByOwn(own);
    }

    @Override
    public void save(Describe describe) {
        Describe describe1=repository.save(describe);
        System.out.println(describe.getId());
    }
}
