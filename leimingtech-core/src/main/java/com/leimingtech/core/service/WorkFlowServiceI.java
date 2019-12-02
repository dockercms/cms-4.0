package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.TSRole;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.entity.WorkFlowEntity;
import com.leimingtech.core.entity.WorkflowAuditingEntity;

public interface WorkFlowServiceI extends CommonService{
	public void delMain (WorkFlowEntity workFlow);
	public Integer getflowsteaplist(String roleid);
	/**
	 * 进入工作流
	 * 
	 * @param workflowid	工作流ID
	 * @param relationid	关联ID
	 * @param type			类型：1：内容；
	 * @return WorkflowAuditingEntity		下一步待审对象，如果不需审核，返回null
	 */
	public WorkflowAuditingEntity enterWorkFlow(String workflowid,String relationid,Integer type);
	/**
	 * 当前用户是否有审批流程权限
	 * @param workflowid 当前内容所分配的工作流ID
	 * @param roleList  当前用户所拥有的角色
	 * @return
	 */
	public boolean judge(String contentsId,List<TSRole> roleList);
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
	public boolean Audit(TSUser user,Integer auditingresult,String opinion,String relationid,Integer type);
	/**
	 * 获取当前该评审对象
	 * 
	 * @param relationid				关联ID
	 * @param type						类型：1：内容；
	 * @return WorkflowAuditingEntity	当前评审对象，如果已完成所有评审，返回null
	 */
	public WorkflowAuditingEntity getCurrAudit(String relationid,Integer type);
}
