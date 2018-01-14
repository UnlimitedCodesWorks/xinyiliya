package xin.yiliya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xin.yiliya.pojo.MessagePicture;
import xin.yiliya.service.PictureService;

@Component
public class PictureServiceImpl implements PictureService {

    private final String URL = "http://101.132.171.77:80/experiment/picture/";

    @Autowired
    private RestTemplate rest;

    public Integer sendPicture(MessagePicture messagePicture) {
        return rest.postForObject(URL+"sendPicOnly",messagePicture,Integer.class);
    }
}
