package com.fula.component;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zl
 * @description wx sdk
 * @date 2020/8/29 10:47
 */
@Component
public class WXComponent {

    private static WxMpService wxMpService;
    private static WxMpMessageRouter wxMpMessageRouter;

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.token}")
    private String token;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.aesKey}")
    private String aesKey;

    @PostConstruct
    public void init(){
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(appId); // 设置微信公众号的appid
        config.setSecret(secret); // 设置微信公众号的app corpSecret
        config.setToken(token); // 设置微信公众号的token
        config.setAesKey(aesKey); // 设置微信公众号的EncodingAESKey
        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        wxMpMessageRouter = new WxMpMessageRouter(WXComponent.getWxMpService());
        wxMpMessageRouter
                .rule().async(false).rContent("bilibili").handler(getBilibiliHandler()).end()
                .rule().async(false).handler(getTextHandler()).end();
    }

    public static WxMpService getWxMpService() {
        return wxMpService;
    }

    public static WxMpMessageRouter getRouter(){
        return wxMpMessageRouter;
    }

    /**
     * @author zl
     * @description 默认处理, 不解析,直接返回
     * @date 2020/8/29 15:24
     * @return me.chanjar.weixin.mp.api.WxMpMessageHandler  
     */  
    private static WxMpMessageHandler getTextHandler(){
        return (wxMessage, context, wxMpService1, sessionManager) -> {
            WxMpXmlOutTextMessage out = null;
            String defaultMsg = "暂不支持的消息类型";
            if (wxMessage.getMsgType().equals("text")) {
                defaultMsg = wxMessage.getContent();
            }
            out = WxMpXmlOutMessage.TEXT().content(defaultMsg)
                    .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .build();
            return out;
        };
    }
    /**
     * @author zl
     * @description  bilibili 开头处理,
     * @date 2020/8/29 15:23  
     * @return me.chanjar.weixin.mp.api.WxMpMessageHandler
     */  
    private static WxMpMessageHandler getBilibiliHandler(){
        return (wxMessage, context, wxMpService1, sessionManager) -> {
            String[] actions = wxMessage.getContent().split(" ");
            WxMpXmlOutTextMessage out = null;

            return out;
        };
    }

}
