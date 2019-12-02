package com.leimingtech.platform.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * 这种方法的测试类   几乎相当于重启项目 因为要扫描注解  然后注入容器
 * @TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true) 
 * transactionManager的默认取值是"transactionManager"，
 * defaultRollback的默认取值是true，当然，你也可以改成false。
 * true表示测试不会对数据库造成污染,false的话当然就会改动到数据库中了。
 * 在方法名上添加@Rollback(false)表示这个测试用例不需要回滚。
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SpringSMSTestCase {

//	@Autowired
//	private SendSmsServiceI sendSmsService;
//	@Test
//	public void sendSMS(){
//		String result = sendSmsService.sendSms("","","15910567548", "能快点么 能慢啊啊", null);
//		System.out.println(result);
//	}
	
//	@Autowired
//	private SmsServiceI smsService;
//	@Test
//	public void send(){
//		String result = smsService.sendSMS("15910576548", "这要是怎么用啊 啊啊啊 啊啊", "");
//		System.out.println(result);
//	}
}
