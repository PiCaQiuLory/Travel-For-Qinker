package com.ss.rmq;

import com.ss.pojo.Product;
import com.ss.service.IProductService;
import com.ss.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class QueueListenter implements MessageListener {


	@Resource
	private IProductService productService;
	
	@Override
	public void onMessage(Message msg) {
		if(msg.getBody()==null)
			log.info("message body is empty!");
		String json = new String(msg.getBody());
		log.debug("message received: " + json);
		
		// parse message and save into table product and target_product
		List<Product> proList = JsonUtil.parseJson(json);
		
		// save or update the products
		productService.saveOrUpdateProduct(proList);
	}

}
