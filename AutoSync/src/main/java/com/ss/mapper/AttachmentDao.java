package com.ss.mapper;

import com.ss.pojo.Attachment;

import java.util.List;

/**
 * Created by stella on 2017/10/19.
 */
public interface AttachmentDao {
    void save(Attachment attachment);
    Attachment findById(Attachment attachment);
    List<Attachment> findByOrderItemId(Attachment attachment);
    void deleteByOrderItemId(List<Long> idlist);
    void update(List<Attachment> attachments);
    void delete(List<Long> list);
}
