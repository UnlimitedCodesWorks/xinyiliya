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
import xin.yiliya.pojo.Message;
import xin.yiliya.pojo.MessagePicture;
import xin.yiliya.pojo.User;
import xin.yiliya.service.MessageService;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {

    private final String URL = "http://101.132.171.77:80/experiment/";

    @Autowired
    private RestTemplate rest;

    public Integer sendMessage(Message message) {
        return rest.postForObject(URL+"message/sendMsg",message,Integer.class);
    }

    public List<MessagePicture> viewHistory(Integer sendId, Integer receiveId) {
        ParameterizedTypeReference<List<MessagePicture>> typeRef = new ParameterizedTypeReference<List<MessagePicture>>() {
        };
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept","application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<MessagePicture>> responseEntity =
                rest.exchange(URL+"message/historyMsg?sendId={sendId}&receiveId={receiveId}",
                        HttpMethod.GET,requestEntity,typeRef,sendId,receiveId);
        return responseEntity.getBody();
    }

    public List<MessagePicture> viewNew(Integer sendId, Integer receiveId) {
        ParameterizedTypeReference<List<MessagePicture>> typeRef = new ParameterizedTypeReference<List<MessagePicture>>() {
        };
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept","application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<MessagePicture>> responseEntity =
                rest.exchange(URL+"message/newMsgs?sendId={sendId}&receiveId={receiveId}",
                        HttpMethod.GET,requestEntity,typeRef,sendId,receiveId);
        return responseEntity.getBody();
    }
}
