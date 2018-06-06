package com.ss.service;

import com.ss.pojo.Attachment;

import java.util.List;

/**
 * Created by stella on 2017/10/19.
 */
public interface AttachmentService {

    void save(Attachment attachment);
    Attachment getFile(String id);

    List<Attachment> findByOrderItemId(Attachment attachment);
    void update(List<Attachment> attachments);
    void delete(List<Long> list);
}
