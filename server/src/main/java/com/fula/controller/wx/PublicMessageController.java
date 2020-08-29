package com.fula.controller.wx;

import com.fula.util.SessionUtil;
import com.fula.component.WXComponent;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author zl
 * @description 微信公众号接口
 * @date 2020/8/29 0:48  
 */
@RestController
@RequestMapping("/wx/v1")
public class PublicMessageController {

    public static final Logger logger = LoggerFactory.getLogger(PublicMessageController.class);
    private static final List<String> adminList = Arrays.asList("o9_fdw5rRa9tOI303cED4VRRGGw0");

    @GetMapping("/verify")
    public void verifyServer(@RequestParam(value = "signature", required = true) String signature,
                               @RequestParam(value = "timestamp", required = true) String timestamp,
                               @RequestParam(value = "nonce", required = true) String nonce,
                               @RequestParam(value = "echostr", required = true) String echostr,
                               HttpServletRequest request,
                               HttpServletResponse response){
        try {
            String ip = SessionUtil.getIpAddress(request);
            if (WXComponent.getWxMpService().checkSignature(timestamp, nonce, signature)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
                logger.info("normal request! ip: {}", ip);
            } else {
                logger.info("abnormal request！ ip: {}", ip);
            }
        } catch (Exception e) {
            logger.error("error when verify server", e);
        }
    }

    @PostMapping("verify")
    public void acceptMessage(HttpServletRequest request,HttpServletResponse response) {
        try{
            response.setCharacterEncoding("UTF-8");
            WxMpXmlMessage message = WxMpXmlMessage.fromXml(request.getInputStream());
            if (adminList.contains(message.getFromUser())) {
                logger.info("get message from admin. {}", message);
            } else {
                logger.info("get message from admin. {}", message);
            }
            WxMpXmlOutMessage outMessage = WXComponent.getRouter().route(message);

            if (outMessage == null) {
                //为null，说明路由配置有问题，需要注意
                response.getWriter().write("");
            } else {
                response.getWriter().write(outMessage.toXml());
            }
        }catch(Exception e){
            logger.error("e", e);
        }
    }


}
