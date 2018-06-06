package com.ss.controller;

import com.ss.pojo.CommonResponse;
import com.ss.pojo.VendorResponse;
import com.ss.pojo.vo.Message;
import com.ss.service.IUiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by stella on 2016/7/18.
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Resource
    private IUiService uiService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Message get(){
        Message message = new Message();
        List<VendorResponse> products = uiService.queryVendorResponse(null);
        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public CommonResponse post(@RequestBody VendorResponse vendor){
        if(vendor.getId()>0){
            return uiService.updateVendorResponse(vendor);
        }else {
            return uiService.insertVendorResponse(vendor);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResponse delete(@PathVariable("id") long id){
        return uiService.deleteVendorResponse(id);
    }

}
