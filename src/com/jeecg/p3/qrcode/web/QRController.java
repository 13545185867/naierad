package com.jeecg.p3.qrcode.web;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jeecgframework.p3.base.vo.WeixinDto;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.WeiXinHttpUtil;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.DateUtil;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PaginatedList;
import org.jeecgframework.p3.core.utils.common.StringUtils;

import com.jeecg.p3.qrcode.service.QRCodeService;

import org.jeecgframework.p3.core.web.BaseController;

import redis.clients.jedis.Jedis;

 /**
 * 描述：</b>WxActToupiaoController<br>
 * @author chao.hua
 * @since：2017年06月21日 18时22分53秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/qrcode")
public class QRController extends BaseController{
  @Autowired
  private QRCodeService qrCodeService;
  
/**
  * 投票首页
  * @return
  */
@RequestMapping(value="getQRCode",method = {RequestMethod.GET,RequestMethod.POST})
public void getQRCode(
		HttpServletResponse response
		,HttpServletRequest request,
		ModelMap model) throws Exception{
	String shopURL = request.getParameter("url");  
	//if(shopURL.startsWith("http://")){
	//	shopURL = URLEncoder.encode(shopURL);
	//}
	String shopURL1 = URLDecoder.decode(shopURL);
    //二维码图片输出流  
    OutputStream out = null;  
    try{  
        response.setContentType("image/jpeg;charset=UTF-8");  
        BufferedImage image = qrCodeService.createQRCode(shopURL1);  
        //实例化输出流对象  
        out = response.getOutputStream();  
        //画图  
        ImageIO.write(image, "png", response.getOutputStream());  
        out.flush();  
        out.close();  
    }catch (Exception e){  
        e.printStackTrace();  
    }finally {  
        try{  
            if (null != response.getOutputStream()) {  
                response.getOutputStream().close();  
            }  
            if (null != out) {  
                out.close();  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
}

private String URLDecoder(String shopURL) {
	// TODO Auto-generated method stub
	return null;
}

}

