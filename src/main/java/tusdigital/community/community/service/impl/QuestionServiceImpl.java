package tusdigital.community.community.service.impl;

import org.apache.commons.lang3.StringUtils;
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
import tusdigital.community.community.vo.QuestionQueryVo;
import tusdigital.community.community.vo.QuestionVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public PaginationVo findAllPassQuestion(String search, int page, int size) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        PaginationVo paginationVo =  new PaginationVo();
        QuestionQueryVo questionQueryVo = new QuestionQueryVo();

        questionQueryVo.setSearch(search);

        Integer totalCount = questionDao.countBySearch(questionQueryVo);
        Integer totalPage;

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

        questionQueryVo.setPage(offset);
        questionQueryVo.setSize(size);
        List<Question> questionList = questionDao.selectBySearch(questionQueryVo);
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
    public PaginationVo findAllPassQuestion(int page, int size) {
        //套娃  这里可以选择pagehelper
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
    public List<QuestionVo> selectRelated(QuestionVo questionvo) {
        if (StringUtils.isBlank(questionvo.getTag())){
            return new ArrayList<>();
        }
        // 分割
        String[] tags = StringUtils.split(questionvo.getTag(), ",");
        // 重新拼接
        String reptags = Arrays.stream(tags).collect(Collectors.joining("|"));

        Question question = new Question();
        question.setId(questionvo.getId());
        question.setTag(reptags);

        List<Question> questions = questionDao.selectRelated(question);
        //java8特性 stream 用于处理数据流  下面就是questions处理为questionvos
        //可以不这么做  之前也有很多集合复制进集合的办法 但是这样简洁
        List<QuestionVo> questionVos = questions.stream().map(q-> {
            QuestionVo qv = new QuestionVo();
            BeanUtils.copyProperties(q,qv);
            return qv;
        }).collect(Collectors.toList());

        return questionVos;
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
