package com.jeecg.p3.userinfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;

import com.jeecg.p3.userinfo.service.UserinfoService;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userinfo.dao.UserinfoDao;
import com.jeecg.p3.userservice.entity.Vipinfo;

@Service("userinfoService")
public class UserinfoServiceImpl implements UserinfoService {
	@Resource
	private UserinfoDao userinfoDao;

	@Override
	public void doAdd(Userinfo userinfo) {
		userinfoDao.add(userinfo);
	}

	@Override
	public void doEdit(Userinfo userinfo) {
		userinfoDao.update(userinfo);
	}

	@Override
	public void doDelete(String id) {
		userinfoDao.delete(id);
	}

	@Override
	public Userinfo queryById(String id) {
		Userinfo userinfo  = userinfoDao.get(id);
		return userinfo;
	}

	@Override
	public PageList<Userinfo> queryPageList(
		PageQuery<Userinfo> pageQuery) {
		PageList<Userinfo> result = new PageList<Userinfo>();
		Integer itemCount = userinfoDao.count(pageQuery);
		List<Userinfo> list = userinfoDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public Integer count(PageQuery<Userinfo> pageQuery) {
		// TODO Auto-generated method stub
		return userinfoDao.count(pageQuery);
	}

	@Override
	public void updateByopenid(Userinfo user) {
		// TODO Auto-generated method stub
		userinfoDao.updateByopenid(user);
	}

	@Override
	public List<Userinfo> getByOpenId(String openid) {
		// TODO Auto-generated method stub
		return userinfoDao.getByOpenId(openid);
	}

	@Override
	public List<Userinfo> queryList(PageQuery<Userinfo> pageQuery) {
		// TODO Auto-generated method stub
		return userinfoDao.queryList(pageQuery);
	}

	@Override
	public List<Vipinfo> queryVip(PageQuery<Vipinfo> pageQuery) {
		// TODO Auto-generated method stub
		return userinfoDao.queryVip(pageQuery);
	}

	@Override
	public PageList<Vipinfo> queryPageVip(PageQuery<Vipinfo> pageQuery) {
		// TODO Auto-generated method stub
		PageList<Vipinfo> result = new PageList<Vipinfo>();
		Integer itemCount = userinfoDao.vipcount(pageQuery);
		List<Vipinfo> list = userinfoDao.queryVipPageList(pageQuery, itemCount) ;
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	
}
