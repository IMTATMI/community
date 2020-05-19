package tusdigital.community.community.service;

import tusdigital.community.community.domain.Question;
import tusdigital.community.community.vo.PaginationVo;
import tusdigital.community.community.vo.QuestionVo;

import java.util.List;

public interface QuestionService {
    List<QuestionVo> findAllQuestion();
    PaginationVo findAllPassQuestion(int page, int size);
    int findAll();
    int addQuestion(Question question);
    PaginationVo list(int id, Integer page, Integer size);
    QuestionVo findOneById(int id);

    int updataQuestion(Question question);
    void incView(Integer id);

    List<QuestionVo> selectRelated(QuestionVo questionvo);

    PaginationVo findAllPassQuestion(String search, int page, int size);
//    void createOrUpdate(Question question);
}
