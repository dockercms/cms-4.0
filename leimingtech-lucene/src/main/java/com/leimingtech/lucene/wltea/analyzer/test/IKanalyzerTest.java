package com.leimingtech.lucene.wltea.analyzer.test;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.leimingtech.lucene.wltea.analyzer.lucene.IKAnalyzer;


/**
 * @author :linjm linjianmao@gmail.com
 * @version :2014-4-15下午01:58:12 description :
 **/
public class IKanalyzerTest {

//	private static Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_34);
//	private static Analyzer analyzer = new StopAnalyzer(Version.LUCENE_36);
//	private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
	private static Analyzer analyzer = new IKAnalyzer();//分词器

	// private static Analyzer analyzer = new PaodingAnalyzer();

	private static void testAnalyzer(String str) throws Exception {
		TokenStream ts = analyzer.tokenStream(null, new StringReader(str));

		CharTermAttribute termAtt = ts.getAttribute(CharTermAttribute.class);
		// TypeAttribute typeAtt= ts.getAttribute(TypeAttribute.class);
		// OffsetAttribute offsetAtt= ts.getAttribute(OffsetAttribute.class);
		// PositionIncrementAttribute posAtt=
		// ts.getAttribute(PositionIncrementAttribute.class);
		// System.out.println("termAtt\ttypeAtt\toffsetAtt\tposAtt");
		//分词结果
		while (ts.incrementToken()) {
			// System.out.println(termAtt.toString()+"\t"+typeAtt.type()+"\t("+offsetAtt.startOffset()+","+offsetAtt.endOffset()+")\t"+posAtt.getPositionIncrement());
			System.out.println(termAtt.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		//内容
		testAnalyzer("北京雷铭智信科技有限公司（简称：雷铭科技），是领先的企业级信息化产品及解决方案提供商。公司是一家注重研发高效、易用、优质的企业信息化产品，其中主推网站内容管理系统、B2C电子商务系统、企业知识管理系统及解决方案，为众多客户提供优质的信息服务。");
	}
}
