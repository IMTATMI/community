package tusdigital.community.community.vo;

import tusdigital.community.community.domain.User;

/**
 * Created by codedrinker on 2019/6/2.
 */

public class CommentVo {
    private Integer id;
    private Integer parentid;
    private Integer type;
    private Integer commentator;
    private long creattime;
    private long modifiedtime;
    private Integer likecount;
    private Integer status;
    private Integer commentcount;
    private String content;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCommentator() {
        return commentator;
    }

    public void setCommentator(Integer commentator) {
        this.commentator = commentator;
    }

    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }

    public long getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "id=" + id +
                ", parentid=" + parentid +
                ", type=" + type +
                ", commentator=" + commentator +
                ", creattime=" + creattime +
                ", modifiedtime=" + modifiedtime +
                ", likecount=" + likecount +
                ", status=" + status +
                ", commentcount=" + commentcount +
                ", content='" + content + '\'' +
                ", user=" + user +
                '}';
    }
}
