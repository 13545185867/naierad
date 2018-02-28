package com.jeecg.p3.messageLog.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;

import com.jeecg.p3.messageLog.service.SendMessageLogService;
import com.jeecg.p3.messageLog.entity.SendMessageLog;
import com.jeecg.p3.messageLog.dao.SendMessageLogDao;

@Service("sendMessageLogService")
public class SendMessageLogServiceImpl implements SendMessageLogService {
	@Resource
	private SendMessageLogDao sendMessageLogDao;

	@Override
	public void doAdd(SendMessageLog sendMessageLog) {
		sendMessageLogDao.add(sendMessageLog);
	}

	@Override
	public void doEdit(SendMessageLog sendMessageLog) {
		sendMessageLogDao.update(sendMessageLog);
	}

	@Override
	public void doDelete(String id) {
		sendMessageLogDao.delete(id);
	}

	@Override
	public SendMessageLog queryById(String id) {
		SendMessageLog sendMessageLog  = sendMessageLogDao.get(id);
		return sendMessageLog;
	}

	@Override
	public PageList<SendMessageLog> queryPageList(
		PageQuery<SendMessageLog> pageQuery) {
		PageList<SendMessageLog> result = new PageList<SendMessageLog>();
		Integer itemCount = sendMessageLogDao.count(pageQuery);
		List<SendMessageLog> list = sendMessageLogDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<SendMessageLog> queryByIPMACPHONE(SendMessageLog SML) {
		// TODO Auto-generated method stub
		return sendMessageLogDao.queryByIPMACPHONE(SML);
	}
	
	 public String getMACAddress(String ip) throws Exception {    
	        String line = "";    
	        String macAddress = "";    
	        final String MAC_ADDRESS_PREFIX = "MAC Address = ";    
	        final String LOOPBACK_ADDRESS = "127.0.0.1";    
	        //如果为127.0.0.1,则获取本地MAC地址。    
	        if (LOOPBACK_ADDRESS.equals(ip)) {    
	            InetAddress inetAddress = InetAddress.getLocalHost();    
	            //貌似此方法需要JDK1.6。    
	            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();    
	            //下面代码是把mac地址拼装成String    
	            StringBuilder sb = new StringBuilder();    
	            for (int i = 0; i < mac.length; i++) {    
	                if (i != 0) {    
	                    sb.append("-");    
	                }    
	                //mac[i] & 0xFF 是为了把byte转化为正整数    
	                String s = Integer.toHexString(mac[i] & 0xFF);    
	                sb.append(s.length() == 1 ? 0 + s : s);    
	            }    
	            //把字符串所有小写字母改为大写成为正规的mac地址并返回    
	            macAddress = sb.toString().trim().toUpperCase();    
	            return macAddress;    
	        }    
	        //获取非本地IP的MAC地址    
	        try {    
	            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);    
	            InputStreamReader isr = new InputStreamReader(p.getInputStream());    
	            BufferedReader br = new BufferedReader(isr);    
	            while ((line = br.readLine()) != null) {    
	                if (line != null) {    
	                    int index = line.indexOf(MAC_ADDRESS_PREFIX);    
	                    if (index != -1) {    
	                        macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();    
	                    }    
	                }    
	            }    
	            br.close();    
	        } catch (IOException e) {    
	            e.printStackTrace(System.out);    
	        }    
	        return macAddress;    
	    }    
	 
	   public String getIpAddr(HttpServletRequest request) throws Exception {    
	        String ip = request.getHeader("x-forwarded-for");    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("WL-Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_CLIENT_IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getRemoteAddr();    
	        }    
	        return ip;    
	    } 
	
}
