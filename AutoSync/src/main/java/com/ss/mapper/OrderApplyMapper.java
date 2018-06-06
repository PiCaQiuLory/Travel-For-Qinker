package com.ss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ss.pojo.Order;
import com.ss.pojo.OrderApply;
import com.ss.pojo.OrderApplyExample;
import com.ss.pojo.OrderApplyItem;
import com.ss.pojo.vo.OrderItemVo;

public interface OrderApplyMapper {
    long countByExample(OrderApplyExample example);

    int deleteByExample(OrderApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderApply record);

    int insertSelective(OrderApply record);

    List<OrderApply> selectByExample(OrderApplyExample example);

    OrderApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderApply record, @Param("example") OrderApplyExample example);

    int updateByExample(@Param("record") OrderApply record, @Param("example") OrderApplyExample example);

    int updateByPrimaryKeySelective(OrderApply record);

    int updateByPrimaryKey(OrderItemVo record);
    
    List<Order> getAllOrders(int id);
    
    List<Order> getAllOrdersByManager();
    
    List<OrderItemVo> getOrderItems(String itemNumber);
    
    List<OrderItemVo> getNonAuditingOrderForFinance();
    
    List<OrderItemVo> getAuditingOrderForFinance(Integer auditorId);
    
    List<OrderItemVo> getNonAuditingOrderForUser(Integer userId);
    
    List<OrderItemVo> getAuditingOrderForUser(Integer userId);
    
    List<Order> getAllOrdersWithNoPage(Integer userId);
    
    Integer getNonAuditingOrderNumberForFinance();
    
    void applyOneOrder(OrderItemVo item);
    
    void applyOrderList(List<OrderItemVo> orderItems);
    
    void changeOderItemStatusTojudy(int orderItemId);
    
    void changeOderItemsStatusTojudy(List<OrderItemVo> ids);
    
    void updateOrderStatus(OrderItemVo itemVo);
    
    void updateOrderItemStatus(Integer id);
    
    void updateOrderBill(OrderItemVo itemVo);
    
    void updateOrderItemByIdToRefuse(int id);
    
    void updateOrderItemByIdToCancle(int id);
    
    OrderItemVo getOrderItemById(int id);
    
    void deleteOrderApplys(Integer[] ids);
    
    int insertOneOrderApplyFromMore(OrderItemVo itemVo);
    
    List<Order> selectByParameters(Order order);
    
    void updateOrderTByIdToCancle(int orderItemId);
    
    void updateOrderTByIdToRefuse(int orderItemId);
    
    List<OrderItemVo> selectOrderApplys(Integer[] ids);
    
    void insertIntoOrderApplyFromMerge(List<OrderItemVo> items);
    
    Integer[] selectMergeOrderId(int id);
}