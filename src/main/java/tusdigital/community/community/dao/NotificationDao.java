package tusdigital.community.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tusdigital.community.community.domain.Notification;

import java.util.List;

@Mapper
public interface NotificationDao {
    int insert(Notification notification);
    int findAll(int receiver);
    int findResCount(int receiver);
    List<Notification> findAllNotificationByPerson(@Param("receiver")int receiver, @Param("offset") int offset, @Param("size") int size);

    Notification selectById(int id);

    void updateRead(int id);
}
