package tusdigital.community.community.service;

import tusdigital.community.community.domain.User;
import tusdigital.community.community.vo.NotificationVo;
import tusdigital.community.community.vo.PaginationVo;

public interface NotificationService {
    PaginationVo list(int id, Integer page, Integer size);

    NotificationVo read(int id, User user);

    public Integer unreadCount(Integer userId);
}
