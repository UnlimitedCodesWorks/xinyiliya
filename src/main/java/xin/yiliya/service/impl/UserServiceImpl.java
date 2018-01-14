package xin.yiliya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import xin.yiliya.pojo.User;
import xin.yiliya.pojo.UserLaunch;
import xin.yiliya.service.UserService;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final String URL = "http://101.132.171.77:80/experiment/user/";

    @Autowired
    private RestTemplate rest;

    public Integer login(String account, String password) {
        return rest.getForObject(URL+"login?num={account}&pass={password}",Integer.class,account,password);
    }

    public Integer register(User user) {
        return rest.postForObject(URL+"register",user,Integer.class);
    }

    public Integer updateInfo(User user) {
        return rest.postForObject(URL+"update",user,Integer.class);
    }

    public UserLaunch getUserInfo(Integer userId) {
        return rest.getForObject(URL+"launch?userId={userId}",UserLaunch.class,userId);
    }

    public List<User> getUsersByInput(String input) {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept","application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<User>> responseEntity = rest.exchange(URL+"find?input={input}", HttpMethod.GET,requestEntity,typeRef,input);
        return responseEntity.getBody();
    }

    public List<User> getReplyUsers(Integer userId) {
        ParameterizedTypeReference<List<User>> typeRef = new ParameterizedTypeReference<List<User>>() {
        };
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept","application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<User>> responseEntity = rest.exchange(URL+"apply?userId={userId}", HttpMethod.GET,requestEntity,typeRef,userId);
        return responseEntity.getBody();
    }
}
