package com.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.wechat.utils.SHA1;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-20 16:24
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    private static final String token = "ggkt";

    /**
     * 服务器有效性验证; 公众号 后台配置的有效性url ，也就是 @RequestMapping("/api/wechat/message")
     *  然后根据这个url 对后台服务器进行验证（公众 向该url 发一个数据，然后 java后台将数据返回，做对比，
     *  数据一样 则 验证 成功 ）
     *
     * @param request
     * @return
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature: {} nonce: {} echostr: {} timestamp: {}", signature, nonce, echostr, timestamp);
        if (this.checkSignature(signature, timestamp, nonce)) {
            log.info("token ok");
            return echostr;
        }
        // 返回给 公众号 进行验证
        return echostr;
    }

    /* 对 获取到的数据 加密 进行返回 ；和公众号 做数据 结果验证 */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }

    /**
     * 接收微信服务器发送来的消息  xml 数据
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {

        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        System.out.println(JSONObject.toJSONString(wxMpXmlMessage));
        return "success";
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }
}
