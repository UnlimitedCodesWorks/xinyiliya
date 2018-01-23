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
import xin.yiliya.pojo.File;
import xin.yiliya.pojo.MessageFile;
import xin.yiliya.pojo.MessagePicture;
import xin.yiliya.service.FileService;

import java.util.List;

@Component
public class FileServiceImpl implements FileService {

    private final String URL = "http://101.132.171.77:80/experiment/file/";

    @Autowired
    private RestTemplate rest;

    public Integer sendFile(MessageFile messageFile) {
        return rest.postForObject(URL+"sendFileOnly",messageFile,Integer.class);
    }

    public List<File> readFile(Integer sendId, Integer receiveId) {
        ParameterizedTypeReference<List<File>> typeRef = new ParameterizedTypeReference<List<File>>() {
        };
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept","application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<List<File>> responseEntity =
                rest.exchange(URL+"readFile?sendId={sendId}&receiveId={receiveId}",
                        HttpMethod.GET,requestEntity,typeRef,sendId,receiveId);
        return responseEntity.getBody();
    }

    public Integer cancelFile(File file) {
        return rest.postForObject(URL+"unDwlFile",file,Integer.class);
    }
}
