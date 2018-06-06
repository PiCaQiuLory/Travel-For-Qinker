package com.ss.quartz;

import com.ss.pojo.spider.SpiderDetail;
import com.ss.service.SpiderService;
import com.ss.utils.DateConventer;
import com.ss.utils.MailSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SpiderJob{
	
	@Autowired
	private SpiderService spiderService;

	@Value("${MAIL_TABLE_HEADER}")
	private String MAIL_HEADER;

	@Value("${MAIL_JC_LIU}")
	private String MAIL_JC_LIU;

	@Value("${MAIL_JC_LIU_NAME}")
	private String MAIL_JC_LIU_NAME;

	@Value("${MAIL_JUAN_LI}")
	private String MAIL_JUAN_LI;

	@Value("${MAIL_JUAN_LI_NAME}")
	private String MAIL_JUAN_LI_NAME;

	@Scheduled(fixedRate = 5000)
	public void execute(){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        Date date;
		try {
			date = DateConventer.formatDate("yyyy-MM-dd", calendar.getTime());
		} catch (Exception e1) {
			date = calendar.getTime();
		}  
		try {
			List<SpiderDetail> spiderDetails = spiderService.getAllSpiderDetail(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String content = MAIL_HEADER.substring(0, MAIL_HEADER.indexOf("期")+1)+dateFormat.format(date)+MAIL_HEADER.
					substring(MAIL_HEADER.indexOf("期")+1);
			for (SpiderDetail spiderDetail : spiderDetails) {
				content = content.concat("<tr><td style='border:solid 1px black;'>"+spiderDetail.getItemNumber()+
						"</td><td style='border:solid 1px black;'>"+spiderDetail.getPrice()+
						"</td><td style='border:solid 1px black;'>"+spiderDetail.getRemark()+"</td></tr>");
			}
			content += "</table>";
			//MailSendUtils.sendMail(MAIL_JUAN_LI, content, MAIL_JUAN_LI_NAME);
			//MailSendUtils.sendMail(MAIL_JC_LIU, content, MAIL_JC_LIU_NAME);
			MailSendUtils.sendMail("13162810508@163.com",content,"lory");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
