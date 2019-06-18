package cn.group.program.service;

import cn.group.program.model.Question;
import java.util.List;

public interface QuestionService {
    List<Question> findAll();
    Question findById(Long id);
    Long save(Question question);
    long count();
}
