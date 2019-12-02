package com.leimingtech.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSRoleUser;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.entity.WorkFlowEntity;
import com.leimingtech.core.entity.WorkFlowStepEntity;
import com.leimingtech.core.entity.WorkflowAuditingEntity;
import com.leimingtech.core.service.WorkFlowServiceI;
@Service("workFlowService")
@Transactional
public class WorkFlowServiceImpl extends CommonServiceImpl implements WorkFlowServiceI {
	public void delMain(WorkFlowEntity workFlow) {
		//获取参数
		Object id0 = workFlow.getId();
		//删除-动态报表配置明细
		String hql0 = "from WorkFlowStepEntity where 1 = 1 AND gid = ? ";
		List<WorkFlowStepEntity> workFlowStepOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(workFlowStepOldList);
		//删除主表信息
		this.delete(workFlow);
	}

	public Integer getflowsteaplist(String roleid) {
		String hql="from WorkFlowStepEntity where 1 = 1 AND gid ='"+roleid+"'";
		List<WorkFlowStepEntity> flowsteplist=this.findHql(hql);
		return flowsteplist.size();
	}
	/**
	 * 进入工作流
	 * 
	 * @param workflowid	工作流ID
	 * @param relationid	关联ID
	 * @param type			类型：1：内容；
	 * @return WorkflowAuditingEntity		下一步待审对象，如果不需审核，返回null
	 */
	public WorkflowAuditingEntity enterWorkFlow(String workflowid,String relationid,Integer type){
		List<WorkFlowStepEntity> wokrFlowStepList = this.findByProperty(WorkFlowStepEntity.class, "gid", workflowid);
		for (WorkFlowStepEntity workFlowStepEntity : wokrFlowStepList) {
			WorkflowAuditingEntity workflowAuditing = new WorkflowAuditingEntity();
			workflowAuditing.setRelationid(relationid);
			workflowAuditing.setAuditingrole(workFlowStepEntity.getRoleid());
			workflowAuditing.setType(type);
			workflowAuditing.setStep(workFlowStepEntity.getStep());
			workflowAuditing.setCreatedtime(new Date());//创建时间
			this.save(workflowAuditing);
		}
		String hql = "from WorkflowAuditingEntity where relationid=\'"+relationid+"\' and type="+type+" and auditingresult is null and (delflag!=-1 or delflag is null)   order by step asc";
		List<WorkflowAuditingEntity> auditList = this.findByQueryString(hql);
		if(auditList.size()>0){
			//返回下一步评审对象
			return auditList.get(0);
		}else{
			//无需评审
			return null;
		}
	}
	/**
	 * 当前用户是否有审批流程权限
	 * @param workflowid 当前内容所分配的工作流ID
	 * @param roleList  当前用户所拥有的角色
	 * @return
	 */
	public boolean judge(String contentsId,List<TSRole> roleList){
		boolean flag = false;
		for(TSRole role:roleList){
			String hql = "from WorkflowAuditingEntity where relationid='"+contentsId+"' and auditingrole='"+role.getId()+"' and auditingresult is null and (delflag!=-1 or delflag is null)";
			List<WorkflowAuditingEntity> list = this.findByQueryString(hql);
			if(list.size()!=0){
				flag=true;
			}
		}
		return flag;
	}
	/**
	 * 评审
	 * 
	 * @param user				当前用户
	 * @param auditingresult	评审结果
	 * @param opinion			评审意见
	 * @param relationid		关联ID
	 * @param type				类型：1：内容；
	 * @return boolean			true:评审成功	false:评审失败
	 */
	public boolean Audit(TSUser user,Integer auditingresult,String opinion,String relationid,Integer type){
		boolean flag = false;
		String hql = "from WorkflowAuditingEntity where relationid='"+relationid+"' and type="+type+" and auditingresult is null and (delflag!=-1 or delflag is null) order by step asc";
		List<WorkflowAuditingEntity> auditList = this.findByQueryString(hql);
		if(auditList.size()>0){
			WorkflowAuditingEntity audit = auditList.get(0);
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser tsRoleUser : rUsers) {
				if(tsRoleUser.getTSRole().getId().equals(audit.getAuditingrole())){
					flag = true;//该当前用户评审
					audit.setAuditor(user.getUserName());
					audit.setAuditingdate(new Date());
					audit.setOperatoroid(user.getId());
					audit.setOpinion(opinion);
					audit.setAuditingresult(auditingresult);//0:不同意;1:同意
					this.saveOrUpdate(audit);
					if(auditingresult==0){
						//如果评审不同意，把当前评审对象删除标记置为-1，以便下一次可以进入新的流程
						String sql = "update cms_workflow_auditing set delflag=-1 where relationid='"+relationid+"' and type="+type;
						this.executeSql(sql);
					}
				}
			}
		}
		return flag;
	}
	/**
	 * 获取当前该评审对象
	 * 
	 * @param relationid				关联ID
	 * @param type						类型：1：内容；
	 * @return WorkflowAuditingEntity	当前评审对象，如果已完成所有评审，返回null
	 */
	public WorkflowAuditingEntity getCurrAudit(String relationid,Integer type){
		String hql = "from WorkflowAuditingEntity where relationid='"+relationid+"' and type="+type+" and auditingresult is null and (delflag!=-1 or delflag is null) order by step asc";
		List<WorkflowAuditingEntity> auditList = this.findByQueryString(hql);
		if(auditList.size()>0){
			//返回当前评审对象
			return auditList.get(0);
		}else{
			//无需评审
			return null;
		}
	}


}