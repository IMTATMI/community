package tusdigital.community.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tusdigital.community.community.dao.NotificationDao;
import tusdigital.community.community.domain.Notification;
import tusdigital.community.community.domain.User;
import tusdigital.community.community.enums.NotificationStatusEnum;
import tusdigital.community.community.enums.NotificationTypeEnum;
import tusdigital.community.community.exception.CustomizeErrorCode;
import tusdigital.community.community.exception.CustomizeException;
import tusdigital.community.community.service.NotificationService;
import tusdigital.community.community.vo.NotificationVo;
import tusdigital.community.community.vo.PaginationVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    NotificationDao notificationDao;

    @Override
    public PaginationVo list(int id, Integer page, Integer size) {
        PaginationVo<NotificationVo> paginationVo  = new PaginationVo<NotificationVo>();
        Integer totalPage;
        Integer totalCount = notificationDao.findAll(id);

        // 取页数整数   可以用 math.ceil()来取值
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //防止用户乱操作 page=-1..
        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        paginationVo.setPagination(totalPage,page);
        //第几页开始 第一页就是0 其余再说
        int offset = page < 1 ? 0 : size * (page - 1);
        // 封装了 问题列表
        List<Notification> notifications = notificationDao.findAllNotificationByPerson(id,offset,size);

//        System.out.println(notifications);
        if (notifications.size() == 0){
            return paginationVo;
        }


        //封装了 问题以及对应用户的列表
        List<NotificationVo> notificationVos = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationVo notificationVo = new NotificationVo();
            BeanUtils.copyProperties(notification, notificationVo);
            notificationVo.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationVos.add(notificationVo);
        }


        //对列表3进行数据填充
        paginationVo.setData(notificationVos);

//        System.out.println(paginationVo);
        return paginationVo;
    }


    @Override
    public NotificationVo read(int id, User user) {
        //选出当前要阅读的未读信息
        Notification notification = notificationDao.selectById(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationDao.updateRead(id);



        NotificationVo notificationVo = new NotificationVo();
        BeanUtils.copyProperties(notification, notificationVo);
        notificationVo.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationVo;
    }

    @Override
    public Integer unreadCount(Integer userId) {
        return notificationDao.findResCount(userId);
    }



}
