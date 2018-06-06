package com.ss.controller;

import com.ss.pojo.CommonResponse;
import com.ss.pojo.RemoteQuery;
import com.ss.pojo.vo.Message;
import com.ss.service.IQueryService;
import com.ss.service.IUiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by stella on 2017/4/11.
 */
@RestController
@RequestMapping("/sqltool")
public class SqltoolController {
    @Resource
    private IUiService uiService;

    @Resource
    private IQueryService queryService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Message get(){
        Message message = new Message();
        List<RemoteQuery> products = uiService.queryRemoteQuery(null);
        message.setCode(Message.CODE_SUCCESS);
        message.setValue(products);
        return message;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public CommonResponse post(@RequestBody RemoteQuery vendor){
        if(vendor.getId()>0){
            return uiService.updateRemoteQuery(vendor);
        }else {
            return uiService.insertRemoteQuery(vendor);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResponse delete(@PathVariable("id") long id){
        return uiService.deleteRemoteQuery(id);
    }

    @ResponseBody
    @RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
    public CommonResponse startQuery(@PathVariable("id") long id){
        queryService.query(new Long(id).toString());
        return new CommonResponse();
    }

}
