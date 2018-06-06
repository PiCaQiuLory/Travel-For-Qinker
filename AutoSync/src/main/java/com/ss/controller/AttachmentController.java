package com.ss.controller;

import com.ss.pojo.Attachment;
import com.ss.pojo.OrderItem;
import com.ss.pojo.vo.Message;
import com.ss.service.AttachmentService;
import com.ss.service.OSSClientService;
import com.ss.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stella on 2017/10/19.
 */
@RestController
@RequestMapping("/attachment")
@Slf4j
public class AttachmentController {

    @Resource
    private AttachmentService attachmentService;

    @Autowired
    private OSSClientService ossClientService;

    //all
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Message upload(@RequestParam("file") CommonsMultipartFile file, HttpSession session){
        Message message = new Message();
        try {
            Attachment uploadFile = ossClientService.uploadObject(file);
            attachmentService.save(uploadFile);
            message.setCode(Constant.SUCCESS);
            message.setValue(uploadFile);
        } catch (IOException e) {
            message.setCode(Constant.FAILURE);
            message.setValue(e.getMessage());
        }
        return message;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("id") String id){
        Attachment attachment = attachmentService.getFile(id);
        OutputStream fOut = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            String codedFileName = attachment.getName();
            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName);
            IOUtils.copy(ossClientService.getFile(attachment.getRemoteName()), response.getOutputStream());
            fOut = response.getOutputStream();
        }
        catch (Exception ex){ex.printStackTrace();}
        finally {
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
            }
        }
    }

    //get attachment by order item id
    @ResponseBody
    @RequestMapping(value = "/item/{orderItemId}", method = RequestMethod.GET)
    public Message fetch(@PathVariable("orderItemId") long orderItemId){
        Message message = new Message();
        message.setCode(Constant.SUCCESS);
        Attachment seach = new Attachment();
        seach.setOrderItemId((int) orderItemId);
        message.setValue(attachmentService.findByOrderItemId(seach));
        return message;
    }

    @ResponseBody
    @RequestMapping(value= "/item", method = RequestMethod.POST)
    public Message updateToOrderItem(@RequestBody OrderItem item){
        Message message = new Message();
        Attachment seach = new Attachment();
        seach.setOrderItemId((int) item.getId());
        List<Attachment> attachments = attachmentService.findByOrderItemId(seach);
        HashMap<Long, Attachment> removeHashMap = new HashMap<>();
        for (Attachment attachment: attachments){
            if(removeHashMap.get(attachment.getId())==null){
                removeHashMap.put(attachment.getId(), attachment);
            }
        }
        for (Attachment attachment : item.getAttachmentList()){
            attachment.setOrderItemId((int) item.getId());
            removeHashMap.remove(attachment.getId());
        }
        if(item.getAttachmentList()!=null && item.getAttachmentList().size()>0) {
            attachmentService.update(item.getAttachmentList());
        }
        if (removeHashMap.size()>0) {
            removeHashMap.forEach((k, v) -> ossClientService.deleteFile(v.getRemoteName()));
            attachmentService.delete(new ArrayList<>(removeHashMap.keySet()));
        }
        return message;
    }

}
