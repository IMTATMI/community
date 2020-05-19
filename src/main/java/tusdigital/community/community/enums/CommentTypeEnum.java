package tusdigital.community.community.enums;

/**
 * Created by codedrinker on 2019/5/31.
 * 枚举 comment 1是回复 2是二级回复
 * 这里完完全全可以用常量来表示（更简单）
 * 但是为了熟悉枚举（自定义错误那边枚举不太熟 这里再用一遍）
 * 是回复 还是楼中楼
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;


    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(int type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}

//public class CommentType{
//    public final static int QUESTION = 1;
//    public final static int COMMENT = 2;
//}
