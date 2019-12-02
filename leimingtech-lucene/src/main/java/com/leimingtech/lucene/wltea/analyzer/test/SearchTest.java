package com.leimingtech.lucene.wltea.analyzer.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author  :linjm linjianmao@gmail.com
 * @version :2014-4-15下午04:51:24
 *  description :
 **/

/*
 * @TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true) 
 * transactionManager的默认取值是"transactionManager"，
 * defaultRollback的默认取值是true，当然，你也可以改成false。
 * true表示测试不会对数据库造成污染,false的话当然就会改动到数据库中了。
 * 在方法名上添加@Rollback(false)表示这个测试用例不需要回滚。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SearchTest {

	@Autowired
//	private LuceneServiceI luceneServiceImpl;

	@Test
	public void search(){
		//luceneServiceImpl.searchIndex(new SiteEntity(),"2", "", "");
	}
}
