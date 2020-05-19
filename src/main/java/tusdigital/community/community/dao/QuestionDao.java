package tusdigital.community.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tusdigital.community.community.domain.Question;
import tusdigital.community.community.vo.QuestionQueryVo;

import java.util.List;

@Mapper
public interface QuestionDao {

    List<Question> findAllQuestion();
    int addQuestion(Question question);
    public int findAll();
    // 从第几开始 每页多少
    List<Question> findAllPassQuestion(@Param("offset")int offset,@Param("size") int size);
    List<Question> findAllPassQuestionByPerson(@Param("id")int id,@Param("offset") int offset,@Param("size") int size);
    Integer findAllById(int id);
    Question findOneById(int id);
    int updataQuestion(Question question);
    int incView(Question question);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
    Question selectById(Integer id);
    int updateTime(@Param("id")int id,@Param("modifiedTime") Long modifiedTime);

    int countBySearch(QuestionQueryVo questionQueryVo);
    List<Question> selectBySearch(QuestionQueryVo questionQueryVo);


}
