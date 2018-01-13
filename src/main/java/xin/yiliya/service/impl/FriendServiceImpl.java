package xin.yiliya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xin.yiliya.pojo.Friends;
import xin.yiliya.service.FriendService;

@Component
public class FriendServiceImpl implements FriendService {

    private final String URL = "http://101.132.171.77:80/experiment/";

    @Autowired
    private RestTemplate rest;

    public Boolean addFriend(Friends friends) {
        return rest.postForObject(URL+"user/addFriend",friends,Boolean.class);
    }

    public Boolean sureFriend(Friends friends) {
        return rest.postForObject(URL+"user/sureFriend",friends,Boolean.class);
    }

    public Boolean refuseFriend(Friends friends) {
        return rest.postForObject(URL+"user/refuseFriend",friends,Boolean.class);
    }

    public Boolean deleteFriend(Friends friends) {
        return rest.postForObject(URL+"user/deleteFriend",friends,Boolean.class);
    }
}
