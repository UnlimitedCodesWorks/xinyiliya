package xin.yiliya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xin.yiliya.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    final private String URL = "http://101.132.171.77:80/experiment/";

    @Autowired
    private RestTemplate rest;

    public Integer login(String account, String password) {
        return rest.getForObject(URL+"user/login.do?num={account}&pass={password}",Integer.class,account,password);
    }
}
