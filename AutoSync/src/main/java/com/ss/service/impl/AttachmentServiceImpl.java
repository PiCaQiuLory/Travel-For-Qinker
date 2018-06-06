package com.ss.service.impl;

import com.ss.mapper.AttachmentDao;
import com.ss.pojo.Attachment;
import com.ss.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by stella on 2017/10/19.
 */
@Transactional(rollbackFor = Exception.class)
@Service("attachmentService")
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentDao attachmentDao;

    @Override
    public void save(Attachment attachment) {
        attachmentDao.save(attachment);
    }

    @Override
    public Attachment getFile(String id) {
        Attachment attachment = new Attachment();
        attachment.setId(Long.valueOf(id));
        return attachmentDao.findById(attachment);
    }

    @Override
    public List<Attachment> findByOrderItemId(Attachment attachment) {
        return attachmentDao.findByOrderItemId(attachment);
    }

    @Override
    public void update(List<Attachment> attachments) {
        attachmentDao.update(attachments);
    }

    @Override
    public void delete(List<Long> list) {
        attachmentDao.delete(list);
    }
}
