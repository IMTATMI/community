package tusdigital.community.community.vo;

public class CommentCreateVo {
    private Integer parentId;
    private String content;
    private Integer type;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommentCreateVo{" +
                "parentId=" + parentId +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}