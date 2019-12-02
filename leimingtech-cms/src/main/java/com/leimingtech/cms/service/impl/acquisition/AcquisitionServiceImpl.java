package com.leimingtech.cms.service.impl.acquisition;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.leimingtech.cms.entity.acquisition.AcquisitionEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 数据采集管理实现
 * 
 * @author liuzhen 2014年4月17日 11:20:53
 * 
 */
@Service("acquisitionService")
@Transactional
public class AcquisitionServiceImpl extends CommonServiceImpl implements AcquisitionServiceI, ServletContextAware {
	private Logger log = LoggerFactory.getLogger(AcquisitionServiceImpl.class);

	private static final long serialVersionUID = 1L;
	/**
	 * 动态页翻页页号
	 */
	public static final String PAGE = "[page]";
	/**
	 * 停止状态
	 */
	public static final int STOP = 0;
	/**
	 * 采集状态
	 */
	public static final int START = 1;
	/**
	 * 暂停状态
	 */
	public static final int PAUSE = 2;

	public static enum AcquisitionResultType {
		SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND, VIEWSTARTNOTFOUND, VIEWENDNOTFOUND, AUTHORSTARTNOTFOUND, AUTHORENDNOTFOUND, ORIGINSTARTNOTFOUND, ORIGINENDNOTFOUND, DESCRISTARTNOTFOUND, DESCRIENDNOTFOUND, RELEASESTARTNOTFOUND, RELEASEENDNOTFOUND, VIEWIDSTARTNOTFOUND, VIEWIDENDNOTFOUND, TITLEEXIST, URLEXIST, UNKNOW
	}

	public static enum AcquisitionRepeatCheckType {
		NONE, TITLE, URL
	}

	/**
	 * 是否停止
	 * 
	 * @return
	 */
	public boolean isStop(AcquisitionEntity acqu) {
		int status = acqu.getStatus();
		return status == 0;
	}

	/**
	 * 是否暂停
	 * 
	 * @return
	 */
	public boolean isPuase(AcquisitionEntity acqu) {
		int status = acqu.getStatus();
		return status == 2;
	}

	/**
	 * 是否中断。停止和暂停都需要中断。
	 * 
	 * @return
	 */
	public boolean isBreak(AcquisitionEntity acqu) {
		int status = acqu.getStatus();
		return status == 0 || status == 2;
	}

	public boolean getImgAcqu(AcquisitionEntity acqu) {
		int status = acqu.getImgAcqu();
		return status == 1;
	}

	public String[] getPlans(AcquisitionEntity acqu) {
		String plan = acqu.getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}

	public String[] getAllPlans(AcquisitionEntity acqu) {
		String[] plans = getPlans(acqu);
		Integer start = acqu.getDynamicStart();
		Integer end = acqu.getDynamicEnd();
		if (!StringUtils.isBlank(acqu.getDynamicAddr()) && start != null && end != null && start >= 0 && end >= start) {
			int plansLen = plans.length;
			String[] allPlans = new String[plansLen + end - start + 1];
			for (int i = 0; i < plansLen; i++) {
				allPlans[i] = plans[i];
			}
			for (int i = 0, len = end - start + 1; i < len; i++) {
				allPlans[plansLen + i] = getDynamicAddrByNum(start + i, acqu);
			}
			return allPlans;
		} else {
			return plans;
		}
	}

	public String getDynamicAddrByNum(int num, AcquisitionEntity acqu) {
		return StringUtils.replace(acqu.getDynamicAddr(), PAGE, String.valueOf(num));
	}

	public int getTotalNum(AcquisitionEntity acqu) {
		int count = 0;
		Integer start = acqu.getDynamicStart();
		Integer end = acqu.getDynamicEnd();
		if (!StringUtils.isBlank(acqu.getDynamicAddr()) && start != null && end != null) {
			count = end - start + 1;
		}
		if (!StringUtils.isBlank(acqu.getPlanList())) {
			count += getPlans(acqu).length;
		}
		return count;
	}

	/**
	 * 查询采集状态为启动的数据
	 * 
	 * @return null/一条数据
	 */
	public AcquisitionEntity getStarted() {
		Criteria crit = getSession().createCriteria(AcquisitionEntity.class);
		crit.add(Restrictions.eq("status", START));
		crit.setMaxResults(1);
		return (AcquisitionEntity) crit.uniqueResult();

	}

	/**
	 * 判断有多少已经开始采集数据
	 * 
	 * @return
	 */
	public Integer hasStarted() {
		return getStarted() == null ? 0 : getMaxQueue() + 1;
	}

	/**
	 * 获取数据采集中最大的队列
	 * 
	 * @return 最大队列数
	 */
	public Integer getMaxQueue() {
		Query query = getSession().createQuery("select max(bean.queue) from AcquisitionEntity bean ");
		return ((Number) query.iterate().next()).intValue();
	}

	public AcquisitionEntity popAcquFromQueue() {
		Query query = getSession().createQuery("from AcquisitionEntity bean where bean.queue>0 order by bean.queue").setMaxResults(1);
		AcquisitionEntity acquisition = (AcquisitionEntity) query.uniqueResult();
		if (acquisition != null) {
			String id = acquisition.getId();
			cancel(id);
		}
		return acquisition;
	}

	public void cancel(String id) {
		AcquisitionEntity acqu = getEntity(AcquisitionEntity.class, id);
		Integer queue = acqu.getQueue();
		for (AcquisitionEntity c : getLargerQueues(queue)) {
			c.setQueue(c.getQueue() - 1);
		}
		acqu.setQueue(0);
	}

	public List<AcquisitionEntity> getLargerQueues(Integer queueNum) {
		Query query = getSession().createQuery("from AcquisitionEntity bean where bean.queue>:queueNum and bean.site.id=:siteId");
		query.setParameter("queueNum", queueNum);
		return query.list();
	}

	/**
	 * 将需要采集的加入到队列
	 * 
	 * @param ids
	 *            需要采集的ID
	 * @param queueNum
	 *            当前队列ID
	 */
	public void addToQueue(String[] ids, Integer queueNum) {
		for (String id : ids) {
			AcquisitionEntity acqu = getEntity(AcquisitionEntity.class, id);
			if (acqu.getStatus() == START || acqu.getQueue() > 0) {
				continue;
			}
			acqu.setQueue(queueNum++);
		}
	}

	/**
	 * 开始采集时改变状态为采集状态
	 * 
	 * @param id
	 *            采集管理ID
	 * @return AcquisitionEntity
	 */
	public AcquisitionEntity startAcq(String id) {
		AcquisitionEntity acqu = getEntity(AcquisitionEntity.class, id);
		if (acqu == null) {
			return acqu;
		}
		acqu.setStatus(START);
		acqu.setStartTime(new Date());
		acqu.setEndTime(null);
		if (acqu.getCurrNum() <= 0) {
			acqu.setCurrNum(1);
		}
		if (acqu.getCurrItem() <= 0) {
			acqu.setCurrItem(1);
		}
		acqu.setTotalItem(0);
		return acqu;
	}

	/**
	 * 判断当前采集的数据是否需要关闭
	 * 
	 * @param id
	 *            当前采集的数据Id
	 * @param currNum
	 * @param currItem
	 * @param totalItem
	 * @return
	 */
	public boolean isNeedBreak(String id, int currNum, int currItem, int totalItem) {
		AcquisitionEntity acqu = getEntity(AcquisitionEntity.class, id);
		if (acqu == null) {
			return true;
		} else if (isPuase(acqu)) {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			acqu.setEndTime(new Date());
			return true;
		} else if (isStop(acqu)) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
			acqu.setEndTime(new Date());
			return true;
		} else {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			return false;
		}
	}

	/**
	 * 结束采集
	 * 
	 * @param id
	 *            需要结束采集的ID
	 */
	public void end(String id) {
		AcquisitionEntity acqu = getEntity(AcquisitionEntity.class, id);
		if (acqu == null) {
			return;
		}
		/*acqu.setStatus(STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(acqu.getCurrNum());
		acqu.setCurrItem(acqu.getCurrItem());
		acqu.setTotalItem(acqu.getTotalItem());*/
	//	acqu.setTotalNum(acqu.getTotalNum());
		
		acqu.setStatus(STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(0);
		acqu.setCurrItem(0);
		acqu.setTotalItem(0);
		acqu.setTotalItem(0);
	}

	public String getPath(String path) {
		String realpath = context.getRealPath(path);
		// tomcat8.0获取不到真实路径，通过/获取路径
		if (realpath == null) {
			realpath = context.getRealPath("/") + path;
		}
		return realpath;
	}

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private ServletContext context;


}