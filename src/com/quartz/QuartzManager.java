package com.quartz;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeewx.api.coupon.qrcode.JwQrcodeAPI;
import org.jeewx.api.coupon.qrcode.model.GetticketRtn;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import weixin.guanjia.core.util.WeixinUtil;
import weixin.p3.oauth2.def.WeiXinOpenConstants;
import weixin.util.DateUtils;

import com.jeecg.p3.timetask.entity.TSTimetask;
import com.jeecg.p3.timetask.service.TSTimetaskService;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;


public class QuartzManager implements BeanFactoryAware {
    private Logger log = Logger.getLogger(QuartzManager.class);
    private Scheduler scheduler;
    private static BeanFactory beanFactory= null ;

    // private ApplicationContext apc;
    
	  @Autowired
	  private TSTimetaskService tSTimetaskService;

	@Autowired
	private JwWebJwidService jwWebJwidServicenew;
	 /**
	 * 循环每秒执行，查询超时的公众账号，重置Token
	 */
	public void autoResetToken() {
		long start = System.currentTimeMillis();
		
		//提前半小时重置Token
		long currentTime = new Date().getTime() - 1000 * 3600 * 2 + 30*60*1000;
		String currentDatetime = DateUtils.date2Str(DateUtils.getDate(currentTime), DateUtils.datetimeFormat);
		//String hql = "from WeixinAccountEntity where addtoekntime < '"+currentDatetime+"'";
		List<JwWebJwid> list =null;
		//= systemService.findHql(hql);
		list = jwWebJwidServicenew.getByAddTicketTime(DateUtils.str2Date(currentDatetime, DateUtils.datetimeFormat));
		//org.jeecgframework.core.util.LogUtil.info("===================定时任务【重置超过2小时失效token】开始===================");
		
		for(JwWebJwid account : list){
			try {
				restWeiXinToken(account);
			} catch (Exception e) {
			//	LogUtil.info("---------定时任务【重置超过2小时失效token】失败公众号------------------"+account.getAccountname());
			}
			
		}
		long end = System.currentTimeMillis();
		long times = end - start;
		//org.jeecgframework.core.util.LogUtil.info("====================定时任务【重置超过2小时失效token】结束，" + "====执行重置公众号数量："+ (list!=null?list.size():0) +" ========总耗时"+times+"毫秒==========");
	}

	/**
	 * 重置restWeiXinToken
	 * @param account
	 */
	private void restWeiXinToken(JwWebJwid account){
		String token = null;
		// 失效 重新获取
		String requestUrl = WeixinUtil.access_token_url.replace("APPID", account.getAccountappid()).replace("APPSECRET", account.getAccountappsecret());
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET", null);
		Date getAccessTokenDate = new Date();
		if (null != jsonObject) {
			try {
				//[1].获取token凭证
				token = jsonObject.getString("access_token");
				// 重置token
				account.setAccountaccesstoken(token);
				// 重置获取token时间
				account.setAddtoekntime(getAccessTokenDate);
				
				
					//------------------------------------------------------------------------------------------------
					try {
						//[2].获取api凭证
						GetticketRtn getticketRtn = JwQrcodeAPI.doGetticket(token);
						if (null != getticketRtn) {
							try {
								// 重置token
								account.setApiticket(getticketRtn.getTicket());
								// 重置事件
								account.setApiticketttime(getAccessTokenDate);
							//	LogUtil.info("---------定时任务重置超过2小时失效token------------------"+"获取Apiticket成功");
							} catch (Exception e) {
								// 获取api凭证失败
								String wrongMessage = "获取api凭证失败 errcode:{"+ getticketRtn.getErrcode()+"} errmsg:{"+getticketRtn.getErrmsg()+"}";
							//	LogUtil.info(wrongMessage);
							}
						}
					} catch (Exception e) {
						//LogUtil.info("---------------------定时任务异常--【获取api凭证】--------------"+e.toString());
					}
					//------------------------------------------------------------------------------------------------
					//[3].获取jsapi凭证
					try {
						String jsapiticket = null;
						String jsapi_ticket_url = WeiXinOpenConstants.JSAPI_TICKET_URL.replace("ACCESS_TOKEN", token);
						JSONObject jsapi_ticket_json = WeixinUtil.httpRequest(jsapi_ticket_url, "GET", null);
						if (null != jsapi_ticket_json) {
							try {
								jsapiticket = jsapi_ticket_json.getString("ticket");
								// 重置token
								account.setJsapiticket(jsapiticket);
								// 重置事件
								account.setJsapitickettime(getAccessTokenDate);
								//LogUtil.info("---------定时任务重置超过2小时失效token------------------"+"获取Jsapiticket成功");
							} catch (Exception e) {
								//获取jsapi凭证失败
								String wrongMessage = "获取jsapi凭证失败 errcode:{"+ (jsonObject.containsKey("errcode")?jsonObject.get("errcode"):"") +"} errmsg:{"+ (jsonObject.containsKey("errmsg")?jsonObject.getString("errmsg"):"") +"}";
								//LogUtil.info(wrongMessage);
							}
						}
					} catch (Exception e) {
						//LogUtil.info("---------------------定时任务异常--【获取jsapi凭证】--------------"+e.toString());
					}
					//------------------------------------------------------------------------------------------------
				
					//systemService.saveOrUpdate(account);
				//LogUtil.info("---------定时任务定时任务【重置超过2小时失效token】成功公众号------------------" + account.getAccountname());
			} catch (Exception e) {
				// 获取token失败
				String wrongMessage = "获取token失败 errcode:{"+ (jsonObject.containsKey("jsonObject")?jsonObject.get("errcode"):"")+"} errmsg:{"+ (jsonObject.containsKey("errmsg")?jsonObject.getString("errmsg"):"") +"}";
				//LogUtil.info(wrongMessage);
				// 重置获取token时间【上次定时任务获取Token时间-失败保存】
				account.setAddtoekntime(getAccessTokenDate);
				// 重置获取token时间【上次定时任务获取Token时间-失败保存】
				account.setAddtoekntime(getAccessTokenDate);
				//systemService.saveOrUpdate(account);
				jwWebJwidServicenew.doEdit(account);
				//LogUtil.info("---------定时任务定时任务【重置超过2小时失效token】失败保存公众号------------------" + account.getAccountname());
			}
		}
	}
    
    
    
    
    
    
    
    
    
    
    
    

    @SuppressWarnings("unused")
    private void reScheduleJob() throws Exception {
        // 通过查询数据库里计划任务来配置计划任务
    	PageQuery<TSTimetask> pageQuery = new PageQuery<TSTimetask>();
    	pageQuery.setPageNo(1);
    	pageQuery.setPageSize(20);
    	List<TSTimetask>  taskList = tSTimetaskService.getAllTask();
    	TSTimetask d= new TSTimetask();
        if (taskList != null && taskList.size() > 0) {
            for (TSTimetask tbcq : taskList) {
                configQuatrz(tbcq);
            }
        }
    }

    public boolean configQuatrz(TSTimetask tbcq) {
        boolean result = false;
        try {
            // 运行时可通过动态注入的scheduler得到trigger
            CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(
                    tbcq.getTaskId(), Scheduler.DEFAULT_GROUP);
            // 如果计划任务已存在则调用修改方法
            if (trigger != null) {
                change(tbcq, trigger);
            } else {
                // 如果计划任务不存在并且数据库里的任务状态为可用时,则创建计划任务
                if (tbcq.getIsEffect().equals("1")) {
                    this.createCronTriggerBean(tbcq);
                }
            }
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

    public void change(TSTimetask tbcq, CronTriggerBean trigger)
            throws Exception {
        // 如果任务为可用
        if (tbcq.getIsEffect().equals("1")) {
            // 判断从DB中取得的任务时间和现在的quartz线程中的任务时间是否相等
            // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
            if (!trigger.getCronExpression().equalsIgnoreCase(
                    tbcq.getCronExpression())) {
                trigger.setCronExpression(tbcq.getCronExpression());
                scheduler.rescheduleJob(tbcq.getTaskId(),
                        Scheduler.DEFAULT_GROUP, trigger);
                log.info(new Date() + ": 更新" + tbcq.getTaskId() + "计划任务");
            }
        } else {
            // 不可用
            scheduler.pauseTrigger(trigger.getName(), trigger.getGroup());// 停止触发器
            scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());// 移除触发器
            scheduler.deleteJob(trigger.getJobName(), trigger.getJobGroup());// 删除任务
            log.info(new Date() + ": 删除" + tbcq.getTaskId()  + "计划任务");

        }

    }

    /**
     * 创建/添加计划任务
     * 
     * @param tbcq
     *            计划任务配置对象
     * @throws Exception
     */
    public void createCronTriggerBean(TSTimetask tbcq) throws Exception {
        // 新建一个基于Spring的管理Job类
        MethodInvokingJobDetailFactoryBean mjdfb = new MethodInvokingJobDetailFactoryBean();
        mjdfb.setName("autoResetWeixinTokenJob");// 设置Job名称
        // 如果定义的任务类为Spring的定义的Bean则调用 getBean方法
            mjdfb.setTargetObject(beanFactory.getBean("weixinAccountTokenTask"));// 设置任务类

        mjdfb.setTargetMethod("autoResetToken");// 设置任务方法
        mjdfb.setConcurrent( false ); // 设置是否并发启动任务
        mjdfb.afterPropertiesSet();// 将管理Job类提交到计划管理类
        // 将Spring的管理Job类转为Quartz管理Job类
        JobDetail jobDetail = new JobDetail();
        jobDetail = (JobDetail) mjdfb.getObject();
        jobDetail.setName("autoResetWeixinTokenJob");
        scheduler.addJob(jobDetail, true); // 将Job添加到管理类
        // 新一个基于Spring的时间类
        CronTriggerBean c = new CronTriggerBean();
        c.setCronExpression(tbcq.getCronExpression());// 设置时间表达式
        c.setName(tbcq.getTaskId());// 设置名称
        c.setJobDetail(jobDetail);// 注入Job
        c.setJobName("autoResetWeixinTokenJob");// 设置Job名称
        scheduler.scheduleJob(c);// 注入到管理类
        scheduler.rescheduleJob(tbcq.getTaskId(), Scheduler.DEFAULT_GROUP,
                c);// 刷新管理类
        log.info(new Date() + ": 新建" + tbcq.getTaskId() + "计划任务");
    }



    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /*
     * public ApplicationContext getApc() { return apc; }
     * 
     * public void setApc(ApplicationContext apc) { this.apc = apc; }
     */
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        this.beanFactory = factory;

    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

   
}