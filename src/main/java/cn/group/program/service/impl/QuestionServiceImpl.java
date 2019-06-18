package cn.group.program.service.impl;

import cn.group.program.model.Question;
import cn.group.program.repository.QuestionRepository;
import cn.group.program.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository repository;

    @Override
    public List<Question> findAll() {
        List<Question> result=new ArrayList<Question>();
        for (Question question:repository.findAll()){
            result.add(question);
        }
        return result;
    }

    @Override
    public Question findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Long save(Question question) {
        Question question1=repository.save(question);
        return question.getId();
    }

    @Override
    public long count() {
        return repository.count();
    }
}
