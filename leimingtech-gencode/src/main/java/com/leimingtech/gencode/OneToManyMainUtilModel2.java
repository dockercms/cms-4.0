package com.leimingtech.gencode;

import com.leimingtech.gencode.codegenerate.generate.onetomany.mbCodeGenerateOneToMany;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.CodeParamEntity;
import com.leimingtech.gencode.codegenerate.pojo.onetomany.SubTableEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 代码生成器入口【一对多】
 *
 */
public class OneToManyMainUtilModel2 {

	/**
	 * 一对多(父子表)数据模型，生成方法
	 * @param args
	 */
	public static void main(String[] args) {
		//第一步：设置主表
		CodeParamEntity codeParamEntityIn = new CodeParamEntity();
		codeParamEntityIn.setTableName("t_class");//主表[表名]
		codeParamEntityIn.setEntityName("Cla");	 //主表[实体名]
		codeParamEntityIn.setEntityPackage("stu");	 //主表[包名]
		codeParamEntityIn.setFtlDescription("班级");	 //主表[描述]
		
		//第二步：设置子表集合
		List<SubTableEntity> subTabParamIn = new ArrayList<SubTableEntity>();
		//[1].子表一
		SubTableEntity po = new SubTableEntity();
		po.setTableName("t_stu");//子表[表名]
		po.setEntityName("Stu");	 //子表[实体名]
		po.setEntityPackage("stu");			 //子表[包]
		po.setFtlDescription("学生");		 //子表[描述]
		//子表[外键:与主表关联外键]
		//说明：这里面的外键是子表的外键字段,非主表和子表的对应关系  GORDER_ID修改为ID
		po.setForeignKeys(new String[]{"id","class_id"});
		subTabParamIn.add(po);
		codeParamEntityIn.setSubTabParam(subTabParamIn);
		
		//第三步：一对多(父子表)数据模型,代码生成
		mbCodeGenerateOneToMany.oneToManyCreate(subTabParamIn, codeParamEntityIn);
	}
}
