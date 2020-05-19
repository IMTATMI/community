package tusdigital.community.community.vo;

public class NotificationVo {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerid;
    private Integer type;
    private Long createTime;
    private Integer status;
    private String notifierName;
    private String outerTitle;

    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotifier() {
        return notifier;
    }

    public void setNotifier(Integer notifier) {
        this.notifier = notifier;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getOuterid() {
        return outerid;
    }

    public void setOuterid(Integer outerid) {
        this.outerid = outerid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "NotificationVo{" +
                "id=" + id +
                ", notifier=" + notifier +
                ", receiver=" + receiver +
                ", outerid=" + outerid +
                ", type=" + type +
                ", createTime=" + createTime +
                ", status=" + status +
                ", notifierName='" + notifierName + '\'' +
                ", outerTitle='" + outerTitle + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
