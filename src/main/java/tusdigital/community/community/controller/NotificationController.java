package tusdigital.community.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.enums.NotificationTypeEnum;
import tusdigital.community.community.service.NotificationService;
import tusdigital.community.community.vo.NotificationVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 2019/6/14.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") int id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        //更新已读
        NotificationVo notificationVo = notificationService.read(id, user);



        //跳转问题
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationVo.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationVo.getType()) {
            return "redirect:/question/" + notificationVo.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
