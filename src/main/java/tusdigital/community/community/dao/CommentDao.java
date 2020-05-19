package tusdigital.community.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tusdigital.community.community.domain.Comment;

import java.util.List;

@Mapper
public interface CommentDao {

    int findByCount();
    Comment findById(int id);

//    莫名其妙的mybatis bug只能这样解决了（好SB的解决方式 但是没办法）
    int addComment(@Param("parentid") int parentid,
                   @Param("type") int type,
                   @Param("commentator") int commentator,
                   @Param("createtime") long createtime,
                   @Param("modifiedtime") long modifiedtime,
                   @Param("likecount") int likecount,
                   @Param("status") int status,
                   @Param("content") String content,
                   @Param("commentcount") int commentcount
                   );

    int insertComment(Comment comment);

    List<Comment> findByOneQuestion(@Param("parentid")int parentid,@Param("type")int type);


    int incCommentCount(@Param("id") int id,
                        @Param("commentCount") int commentCount);


}
