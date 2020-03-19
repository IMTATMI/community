package tusdigital.community.community.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tusdigital.community.community.dao.QuestionDao;
import tusdigital.community.community.dao.UserDao;
import tusdigital.community.community.domain.Question;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.service.QuestionService;
import tusdigital.community.community.vo.PaginationVo;
import tusdigital.community.community.vo.QuestionVo;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserDao userDao;


    @Override
    public List<QuestionVo> findAllQuestion() {
        List<Question> questionList = questionDao.findAllQuestion();
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userDao.findById(question.getCreator());
            QuestionVo questionVo =  new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);
            questionVo.setUser(user);
            questionVoList.add(questionVo);
        }
        
        return questionVoList;
    }

    @Override
    public PaginationVo findAllPassQuestion(int page, int size) {
        //套娃 有点晕 看着就好 看不爽pagehelper
        //封装了 问题以及对应用户的列表 以及对应的分页
        PaginationVo paginationVo  = new PaginationVo();
        Integer totalPage;
        Integer totalCount = questionDao.findAll();

        // 取页数整数   可以用 math.ceil()来取值
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //防止用户乱操作 page=-1..
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        paginationVo.setPagination(totalPage,page);
        //第几页开始 第一页就是0 其余再说
        int offset = page < 1 ? 0 : size * (page - 1);
        // 封装了 问题列表
        List<Question> questionList = questionDao.findAllPassQuestion(offset,size);
        //封装了 问题以及对应用户的列表
        List<QuestionVo> questionVoList = new ArrayList<>();

        //对列表2 进行数据填充
        for (Question question : questionList) {
            User user = userDao.findById(question.getCreator());
            QuestionVo questionVo =  new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);
            questionVo.setUser(user);
            questionVoList.add(questionVo);
        }


        //对列表3进行数据填充
        paginationVo.setData(questionVoList);

        return paginationVo;
    }

    @Override
    public PaginationVo list(int id, Integer page, Integer size) {
        PaginationVo paginationVo  = new PaginationVo();
        Integer totalPage;
        Integer totalCount = questionDao.findAllById(id);

        // 取页数整数   可以用 math.ceil()来取值
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //防止用户乱操作 page=-1..
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        paginationVo.setPagination(totalPage,page);
        //第几页开始 第一页就是0 其余再说
        int offset = page < 1 ? 0 : size * (page - 1);
        // 封装了 问题列表
        List<Question> questionList = questionDao.findAllPassQuestionByPerson(id,offset,size);
        //封装了 问题以及对应用户的列表
        List<QuestionVo> questionVoList = new ArrayList<>();

        //对列表2 进行数据填充
        for (Question question : questionList) {
            User user = userDao.findById(question.getCreator());
            QuestionVo questionVo =  new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);
            questionVo.setUser(user);
            questionVoList.add(questionVo);
        }


        //对列表3进行数据填充
        paginationVo.setData(questionVoList);

        return paginationVo;
    }

    @Override
    public QuestionVo findOneById(int id) {
        Question question =  questionDao.findOneById(id);
        User user = userDao.findById(question.getCreator());
        QuestionVo questionVo = new QuestionVo();

        BeanUtils.copyProperties(question,questionVo);
        questionVo.setUser(user);


        return questionVo;
    }

    @Override
    public int updataQuestion(Question question) {
        return questionDao.updataQuestion(question);
    }

    @Override
    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setView_count(1);
        questionDao.incView(question);
    }


    @Override
    public int findAll() {
        return questionDao.findAll();
    }

    @Override
    public int addQuestion(Question question) {
        return questionDao.addQuestion(question);
    }
}
