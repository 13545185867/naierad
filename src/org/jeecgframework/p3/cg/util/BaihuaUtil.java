package org.jeecgframework.p3.cg.util;

import java.util.List;

public class BaihuaUtil {

		public static boolean isadmin(List<String> list){
			boolean isadmin =false;
			for(String s:list){
				if(s.equals("00")){
					isadmin=true;
				}
			}
			return isadmin;
		}
}
