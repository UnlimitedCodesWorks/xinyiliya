package xin.yiliya.pojo;

import org.springframework.stereotype.Component;

@Component
public class UserBean {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
