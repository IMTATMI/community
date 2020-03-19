package tusdigital.community.community.vo;

import tusdigital.community.community.domain.User;

public class QuestionVo {
    private int id;
    private String title;
    private String descrpition;
    private long creat_time;
    private long modified_time;
    private int creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private String tag;
    private int status;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    public long getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(long creat_time) {
        this.creat_time = creat_time;
    }

    public long getModified_time() {
        return modified_time;
    }

    public void setModified_time(long modified_time) {
        this.modified_time = modified_time;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "QuestionVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", descrpition='" + descrpition + '\'' +
                ", creat_time=" + creat_time +
                ", modified_time=" + modified_time +
                ", creator=" + creator +
                ", comment_count=" + comment_count +
                ", view_count=" + view_count +
                ", like_count=" + like_count +
                ", tag='" + tag + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
