package com.leimingtech.platform.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author :linjm linjianmao@gmail.com
 * @version :2014-5-13下午03:11:02 description :
 **/
public class SpringMailTestCase {

	public void send163ByMutil() throws MessagingException {
		JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
		javaMail.setHost("smtp.qq.com");
		javaMail.setPassword("123456");
		javaMail.setUsername("xxxxxx@qq.com");
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		javaMail.setJavaMailProperties(prop);
		MimeMessage message = javaMail.createMimeMessage();
		MimeMessageHelper messageHelp = new MimeMessageHelper(message, true,
				"GBK");
		messageHelp.setFrom("xxxxx@qq.com");
		messageHelp.setTo("578169426@qq.com");
		messageHelp.setSubject("邮件测试");
		// messageHelp
		String body = "<html><head><META http- equiv=Content-Type content='text/html; charset=GBK'></HEAD>< title>test</title></head><body>dear 小燕子 \n ";
		body += " < red > This is Text! </ red >  pic  < img   src = 'cid:a' > </ img > < br > hello < img   src = 'cid:b' > </ img > </ body > </ html > ";
		messageHelp.setText(body, true);
		messageHelp.addInline("a", new File("D:/11111.jpg"));
		messageHelp.addInline("b", new File("D:/output.jpg"));
		File file = new File("D:/test.rar");
		try {
			messageHelp.addAttachment(MimeUtility.encodeWord(file.getName()),
					file);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		javaMail.send(message);
	}

	public static void main(String[] args) throws MessagingException {
		SpringMailTestCase send = new SpringMailTestCase();
		send.send163ByMutil();

	}
}
