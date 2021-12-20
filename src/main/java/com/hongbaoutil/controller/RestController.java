package com.hongbaoutil.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@Slf4j
@RequestMapping("/rest")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/speedUp")
    public String speedUp(@RequestParam(name="openId") String openId){
        ResponseEntity<String> response = restTemplate.getForEntity("http://search.api.luotuomaservice.cn/order/bufa?openid="+openId,String.class);
        return "加速成功";
    }


    @GetMapping("/search")
    public String search(@RequestParam(name = "fangWeiCode") String fangWeiCode) {
        ResponseEntity<String> response = restTemplate.getForEntity("http://search.api.luotuomaservice.cn/order/search?type=hongbao&thirdorder="+fangWeiCode,String.class);
        log.info("返回值：{}",response.getBody());
        if (response.getBody().equals("没有查询到该订单号\n")){
            return "没有查询到该订单号";
        }
        else {
            String[] arr = response.getBody().split("remark     &nbsp;   &nbsp;    ");
            String[] arr2 = arr[1].split("<br>redpacketinfo     &nbsp;");
        log.info("返回值：{}******", arr2[0]);


            if (arr2[0].equals("成功")) {
                return "成功到账";
            } else {
                return "未到账";
            }
        }
    }

}
