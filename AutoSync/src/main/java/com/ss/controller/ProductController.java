package com.ss.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ss.pojo.CommonResponse;
import com.ss.pojo.ProductResponse;
import com.ss.pojo.SourceProductResponse;
import com.ss.pojo.TargetProductResponse;
import com.ss.pojo.vo.LogVo;
import com.ss.pojo.vo.Message;
import com.ss.service.IUiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
/**
 * Created by stella on 2016/7/23.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private final static String QUEUE_NAME = "tripqueue3";
    @Resource
    private IUiService uiService;

    @ResponseBody
    @RequestMapping(value = "/target", method = RequestMethod.GET)
    public Message getTargetProduct(){
        Message message = new Message();
        List<TargetProductResponse> products = uiService.queryTargetProductResponse(null);

        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public Message getSourceProduct(){
        Message message = new Message();
        List<SourceProductResponse> products = uiService.querySourceProductResponse(null);

        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message getProduct(@RequestBody ProductResponse productResponse){
        Message message = new Message();
        StringBuffer url = new StringBuffer();
        if (productResponse!=null){
            if(productResponse.getSourceUrl()==null || productResponse.getSourceUrl().trim()!=""){
                url.append("%").append(productResponse.getSourceUrl()).append("%");
                productResponse.setSourceUrl(url.toString());
            }else{
                productResponse.setSourceUrl(null);
            }
//            productResponse.setSourceProductId(productResponse.getId());
        }
        List<ProductResponse> products = uiService.queryProductResponse(productResponse);

        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }


    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public CommonResponse saveProduct(@RequestBody SourceProductResponse product){
        return uiService.addSourceTargetProductResponse(product);
    }

    @ResponseBody
    @RequestMapping(value = "/target", method = RequestMethod.POST)
    public CommonResponse saveTarget(@RequestBody TargetProductResponse product){
        return uiService.updateTargetProduct(product);
    }

    @ResponseBody
    @RequestMapping(value = "/source", method = RequestMethod.POST)
    public CommonResponse saveSource(@RequestBody SourceProductResponse product){
        return uiService.updateSourceProduct(product);
    }
    @ResponseBody
    @RequestMapping(value = "/target/{id}", method = RequestMethod.DELETE)
    public CommonResponse deleteTarget(@PathVariable("id") long id){
        return uiService.deleteTargetProduct(id);
    }
    @ResponseBody
    @RequestMapping(value = "/source/{id}", method = RequestMethod.DELETE)
    public CommonResponse deleteSource(@PathVariable("id") long id){
        return uiService.deleteSourceProduct(id);
    }

    @ResponseBody
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public Message saveSource(@RequestBody LogVo logVo){
        Message message = new Message();
        if(logVo.getType().equals("target")){
            TargetProductResponse targetProductResponse = new TargetProductResponse();
            targetProductResponse.setId(logVo.getId());
            message.setValue(uiService.queryAdtTargetProductResponse(targetProductResponse));
        }else{
            SourceProductResponse sourceProductResponse = new SourceProductResponse();
            sourceProductResponse.setId(logVo.getId());
            message.setValue(uiService.queryAdtSourceProductResponse(sourceProductResponse));
        }
        message.setCode(Message.CODE_SUCCESS);
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
    public Message syncNow(@PathVariable("id") String id){
        Message message = new Message();
        List<SourceProductResponse> sourceProductList = uiService.querySourceProductResponse(null);
        if (sourceProductList!=null){
            for (SourceProductResponse sourceProduct: sourceProductList){
                if (sourceProduct.getImmediate()!=null && sourceProduct.getImmediate()==1){
                    message.setCode(Message.CODE_ERROR);
                    message.setValue("Wait");
                    return message;
                }
            }
        }
        uiService.updateProduct(id);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String sourceId  = id;
            channel.basicPublish("", QUEUE_NAME, null, sourceId.getBytes());
            System.out.println(" [x] Sent source id '" + sourceId + "' to queue for scrapy");
        } catch (Exception e) {
            message.setCode(Message.CODE_ERROR);
            message.setValue("Manual Sync Fail: " + e);
        }
        message.setCode(Message.CODE_SUCCESS);
        return message;
    }
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Message getSyncCount(){
        Message message = new Message();

        message.setCode(Message.CODE_SUCCESS);
        message.setValue(uiService.querySyncCount(null));
        return message;
    }

}
