package tusdigital.community.community.domain;


public class Question {

  private int id;
  private String title;
  private String descrpition;
  private long creatTime;
  private long modifiedTime;
  private int creator;
  private int commentCount;
  private int viewCount;
  private int likeCount;
  private String tag;
  private int status;

    public Integer getId() {
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
        return creatTime;
    }

    public void setCreat_time(long creat_time) {
        this.creatTime = creat_time;
    }

    public long getModified_time() {
        return modifiedTime;
    }

    public void setModified_time(long modified_time) {
        this.modifiedTime = modified_time;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getComment_count() {
        return commentCount;
    }

    public void setComment_count(int comment_count) {
        this.commentCount = comment_count;
    }

    public int getView_count() {
        return viewCount;
    }

    public void setView_count(int view_count) {
        this.viewCount = view_count;
    }

    public int getLike_count() {
        return likeCount;
    }

    public void setLike_count(int like_count) {
        this.likeCount = like_count;
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", descrpition='" + descrpition + '\'' +
                ", creat_time=" + creatTime +
                ", modified_time=" + modifiedTime +
                ", creator=" + creator +
                ", comment_count=" + commentCount +
                ", view_count=" + viewCount +
                ", like_count=" + likeCount +
                ", tag='" + tag + '\'' +
                ", status=" + status +
                '}';
    }
}
