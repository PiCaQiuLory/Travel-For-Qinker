package com.ss.controller;

import com.ss.pojo.CommonResponse;
import com.ss.pojo.ModelResult;
import com.ss.pojo.Provider;
import com.ss.pojo.vo.Message;
import com.ss.service.IUiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by stella on 2017/9/24.
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Resource
    private IUiService uiService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Message get(){
        Message message = new Message();
        List<Provider> products = uiService.queryProvider(null);
        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public CommonResponse post(@RequestBody Provider provider){
        if(provider.getId()>0){
            return uiService.updateProvider(provider);
        }else {
        	if(provider.getCategory() == null) {
        		return new CommonResponse();
        	}
            return uiService.insertProvider(provider);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/{id}")
    public CommonResponse delete(@PathVariable("id") long id){
        return uiService.deleteProvider(id);
    }

    @ResponseBody
    @RequestMapping(value = "/destination", method = RequestMethod.GET)
    public Message getDestination(){
        Message message = new Message();
        List<Provider> products = uiService.queryDestination(null);
        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }
    
    @RequestMapping("/getProviderAndBalance")
    @ResponseBody
    public ModelResult getProviderAndBalance() {
    	List<Provider> providers = uiService.getProviderAndBalance();
    	return ModelResult.buildOk(providers);
    }
}
