package tusdigital.community.community.cache;

import org.apache.commons.lang3.StringUtils;
import sun.security.krb5.internal.ccache.Tag;
import tusdigital.community.community.vo.TagVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagVo> getTag(){
        List<TagVo> tags =  new ArrayList<>();

        TagVo learn = new TagVo();
        learn.setCategoryName("学习");
        learn.setTags(Arrays.asList("经验交流","学习心得","我想提问"));
        tags.add(learn);

        TagVo live = new TagVo();
        live.setCategoryName("校园生活");
        live.setTags(Arrays.asList("启迪风貌","日常生活","XXXX"));
        tags.add(live);

        TagVo other = new TagVo();
        other.setCategoryName("其他");
        other.setTags(Arrays.asList("测试","灌水"));
        tags.add(other);

        return tags;
    }

    //判断标签是否合法
    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagVo> tagVos = getTag();

        //flatmap 筛选集合的详细属性
        //由于 tagsVo 为两层数组，即标签头 和标签  去第二个数组中找 即对应getTages 并封装
        List<String> tagList = tagVos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());

        //filter 不存在与集合里的
        //查找传入的tag种是否存在上方没有的  如果出现了 将其用,拼接且返回
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t ) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }




}
