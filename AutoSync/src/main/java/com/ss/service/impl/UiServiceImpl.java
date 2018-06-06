package com.ss.service.impl;

import com.ss.mapper.AttachmentDao;
import com.ss.mapper.IProductDao;
import com.ss.mapper.IUiDao;
import com.ss.pojo.*;
import com.ss.pojo.vo.SearchOrderVo;
import com.ss.service.IUiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(rollbackFor = Exception.class)
@Service("uiService")
public class UiServiceImpl implements IUiService {

	@Resource
	private IUiDao uiDao;

	@Resource
	private IProductDao productDao;
	@Resource
	private AttachmentDao attachmentDao;
	@Override
	public List<SourceProductResponse> querySourceProductResponse(SourceProductResponse spr) {
		if(spr==null)
			spr = new SourceProductResponse();
		List<SourceProductResponse> list = uiDao.querySourceProductResponse(spr);
		if(!CollectionUtils.isEmpty(list)){
			for (SourceProductResponse sourceProductResponse : list) {
				TargetProductResponse tpr = new TargetProductResponse();
				tpr.setSourceProductId(sourceProductResponse.getId());
				sourceProductResponse.setTargetList(uiDao.queryTargetProductResponse(tpr));
                DynamicProduct dynamicProduct = new DynamicProduct();
                dynamicProduct.setSourceId((int)sourceProductResponse.getId());
                sourceProductResponse.setDynamicProducts(uiDao.queryDynamicProduct(dynamicProduct));
			}
		}
		return list;
	}

	@Override
	public List<TargetProductResponse> queryTargetProductResponse(TargetProductResponse tpr) {
		if(tpr==null)
			tpr = new TargetProductResponse();
		List<TargetProductResponse> list = uiDao.queryTargetProductResponse(tpr);
		if(!CollectionUtils.isEmpty(list)){
			for (TargetProductResponse targetProductResponse : list) {
				SourceProductResponse spr = new SourceProductResponse();
				spr.setId(targetProductResponse.getSourceProductId());
				List<SourceProductResponse> sourceList = uiDao.querySourceProductResponse(spr);
				if(!CollectionUtils.isEmpty(sourceList)){
					targetProductResponse.setSourceProductResponse(sourceList.get(0));
				}
			}
		}
		return list;
	}

	@Override
	public List<ProductResponse> queryProductResponse(ProductResponse pr) {
		if(pr==null)
			pr = new ProductResponse();
		List<ProductResponse> list = uiDao.queryProductResponse(pr);
		if(list==null)
			list = new ArrayList<>();
		return list;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public CommonResponse addSourceTargetProductResponse(SourceProductResponse spr) {
		CommonResponse cp = new CommonResponse();
		// duplicate url
		if(spr.getUrl()!=null && !spr.getUrl().trim().equals("")){
			SourceProductResponse querySpr = new SourceProductResponse();
			querySpr.setUrl(spr.getUrl());
			List<SourceProductResponse> queryList = uiDao.querySourceProductResponse(spr);
			if(queryList!=null &&queryList.size()>0){
				cp.setCode(1);
				cp.setValue("duplicate url");
				return cp;
			}
		}

		// duplicate target product id
		TargetProductResponse queryTpr = new TargetProductResponse();
		if(spr.getTargetList()!=null && spr.getTargetList().size()>0){
			Set<String> proIdSet = new HashSet<>();
			for (TargetProductResponse tpr : spr.getTargetList()) {
				if(proIdSet.contains(tpr.getTargetProductId()+tpr.getPackageName())){
					cp.setCode(1);
					cp.setValue("duplicate target product id");
					return cp;
				}else{
					proIdSet.add(tpr.getTargetProductId()+tpr.getPackageName());
				}
				queryTpr.setTargetProductId(tpr.getTargetProductId());
				queryTpr.setPackageName(tpr.getPackageName());
				List<TargetProductResponse> targetList = uiDao.queryTargetProductResponse(queryTpr);
				if(targetList!=null &&targetList.size()>0){
					cp.setCode(1);
					cp.setValue("duplicate target product id");
					return cp;
				}
			}
		}
		
		uiDao.insertSourceProduct(spr);
		if(spr.getTargetList()!=null && spr.getTargetList().size()>0){
			for (TargetProductResponse tpr : spr.getTargetList()) {
				tpr.setSourceProductId(spr.getId());
			}
			uiDao.insertTargetProduct(spr.getTargetList());
		}
		if(spr.getDynamicProducts()!=null && spr.getDynamicProducts().size()>0){
			for (DynamicProduct dynamicProduct: spr.getDynamicProducts()){
				dynamicProduct.setSourceId((int)spr.getId());
			}
			uiDao.insertDynamicProduct(spr.getDynamicProducts());
		}
		return cp;
	}

	@Override
	public CommonResponse updateSourceProduct(SourceProductResponse spr) {
		CommonResponse cp = new CommonResponse();
		// duplicate url
		SourceProductResponse querySpr = new SourceProductResponse();
		querySpr.setUrl(spr.getUrl());
        if(spr.getProductType()!=null && spr.getProductType().equals("dynamic")){
            DynamicProduct dynamicProduct = new DynamicProduct();
            dynamicProduct.setSourceId((int)spr.getId());
            //remove exists
            uiDao.deleteDynamicProducts(dynamicProduct);
            //add new
            uiDao.insertDynamicProduct(spr.getDynamicProducts());
        }
//		List<SourceProductResponse> queryList = uiDao.querySourceProductResponse(querySpr);
//		if(queryList!=null &&queryList.size()>0){
//			if(queryList.size()!=1){
//				cp.setCode(1);
//				cp.setValue("duplicate url");
//				return cp;
//			}else{
//				if(queryList.get(0).getId()!=spr.getId()){
//					cp.setCode(1);
//					cp.setValue("duplicate url");
//					return cp;
//				}
//			}
//		}
		if("normal".equalsIgnoreCase(spr.getmStatus())){
			spr.setReasonCode("");
		}
		uiDao.updateSourceProduct(spr);
		return cp;
	}

	@Override
	public CommonResponse updateTargetProduct(TargetProductResponse tpr) {
		CommonResponse cp = new CommonResponse();
		SourceProductResponse spr = new SourceProductResponse();
		spr.setId(tpr.getSourceProductId());
		List<SourceProductResponse> sourList = uiDao.querySourceProductResponse(spr);
		if (sourList == null || sourList.size() == 0) {
			cp.setCode(1);
			cp.setValue("invalid sourceId");
			return cp;
		}
		TargetProductResponse queryTpr = new TargetProductResponse();
		queryTpr.setTargetProductId(tpr.getTargetProductId());
		queryTpr.setPackageName(tpr.getPackageName());
		
		List<TargetProductResponse> targetList = uiDao.queryTargetProductResponse(queryTpr);
		if(tpr.getId()==0){
			if (targetList != null && targetList.size() > 0) {
				cp.setCode(1);
				cp.setValue("duplicate target product id");
				return cp;
			}else{
				List<TargetProductResponse> list = new ArrayList<>();
				list.add(tpr);
				uiDao.insertTargetProduct(list);
			}
		}else {
			if (targetList != null && targetList.size() > 0) {
				if (targetList.size() != 1) {
					cp.setCode(1);
					cp.setValue("duplicate target product id");
					return cp;
				} else {
					if (targetList.get(0).getId() != tpr.getId()) {
						cp.setCode(1);
						cp.setValue("duplicate target product id");
						return cp;
					}
				}
			}
			if ("normal".equalsIgnoreCase(tpr.getmStatus())) {
				tpr.setReasonCode("");
			}
			uiDao.updateTargetProduct(tpr);
		}
		return cp;
	}

	@Override
	public CommonResponse deleteSourceProduct(long id) {
		CommonResponse cr = new CommonResponse();
		try {
			uiDao.deleteSourceProduct(id);
		} catch (Exception e) {
			cr.setCode(1);
			cr.setValue("please delete target product first");
		}
		return cr;
	}

	@Override
	public CommonResponse deleteTargetProduct(long id) {
		uiDao.deleteTargetProduct(id);
		return new CommonResponse();
	}

	@Override
	public List<VendorResponse> queryVendorResponse(VendorResponse vr) {
		if(vr==null)
			vr = new VendorResponse();
		return uiDao.queryVendorResponse(vr);
	}

	@Override
	public CommonResponse insertVendorResponse(VendorResponse vr) {
		uiDao.insertVendorResponse(vr);
		return new CommonResponse();
	}

	@Override
	public CommonResponse deleteVendorResponse(long id) {
		uiDao.deleteVendorResponse(id);
		return new CommonResponse();
	}

	@Override
	public CommonResponse updateVendorResponse(VendorResponse vr) {
		uiDao.updateVendorResponse(vr);
		return new CommonResponse();
	}

	@Override
	public List<SourceProductResponse> queryAdtSourceProductResponse(SourceProductResponse spr) {
		return uiDao.queryAdtSourceProductResponse(spr);
	}

	@Override
	public List<TargetProductResponse> queryAdtTargetProductResponse(TargetProductResponse tpr) {
		return uiDao.queryAdtTargetProductResponse(tpr);
	}

	@Override
	public List<RemoteQuery> queryRemoteQuery(RemoteQuery remoteQuery) {
		return uiDao.queryRemoteQueryResponse(null);
	}

	@Override
	public CommonResponse insertRemoteQuery(RemoteQuery vr) {
		uiDao.insertRemoteQueryResponse(vr);
		return new CommonResponse();
	}

	@Override
	public CommonResponse deleteRemoteQuery(long id) {
		uiDao.deleteRemoteQueryResponse(id);
		return new CommonResponse();
	}

	@Override
	public CommonResponse updateRemoteQuery(RemoteQuery vr) {
		uiDao.updateRemoteQueryResponse(vr);
		return new CommonResponse();
	}

	@Override
	public List<SyncCount> querySyncCount(SyncCount syncCount) {
		return uiDao.querySyncCount(syncCount);
	}

	@Override
	public void updateProduct(String sourceId) {
		List<Product> productList = productDao.getUpdatePro(sourceId);
		for (Product p : productList){
			p.setSendInd("Y");
			productDao.updateProductStatus(p);
		}
	}

	@Override
	public CommonResponse insertOrder(Order order) {
		uiDao.insertOrder(order);
		return new CommonResponse();
	}

	@Override
	public CommonResponse updateOrder(Order vr) {
		uiDao.updateOrder(vr);
		return new CommonResponse();
	}

	@Override
	public List<Order> queryOrders(SearchOrderVo vo) {
		return uiDao.queryOrder(vo);
	}

	@Override
	public Integer queryOrderCount(SearchOrderVo vo) {
		return uiDao.countAllOrder(vo);
	}

	@Override
	public void deleteOrder(long id) {
		uiDao.deleteOrderItem(id);
		uiDao.deleteOrderLog(id);
		uiDao.deleteOrder(id);
	}

	@Override
	public List<OrderLog> queryOrderLogs(long id) {
		OrderLog log = new OrderLog();
		log.setOrderId(id);
		return uiDao.queryOrderLog(log);
	}

	@Override
	public List<OrderLog> queryAllOrderLogs() {
		OrderLog log = new OrderLog();
		log.setOrderId(0);
		return uiDao.queryOrderLog(log);
	}

	@Override
	public void addOrderLog(OrderLog log) {
		uiDao.addOrderLog(log);
	}

    @Override
    public void deleteOrderLogById(long id) {
        uiDao.deleteOrderLogById(id);
    }

    @Override
	public List<Provider> queryProvider(Provider vr) {
		return uiDao.queryProvider(vr);
	}

	@Override
	public CommonResponse insertProvider(Provider vr) {
		uiDao.insertProvider(vr);
		return new CommonResponse();
	}

	@Override
	public CommonResponse deleteProvider(long id) {
		uiDao.deleteProvider(id);
		return new CommonResponse();
	}

	@Override
	public CommonResponse updateProvider(Provider vr) {
		uiDao.updateProvider(vr);
		return new CommonResponse();
	}

	@Override
	public List<OrderItem> queryOrderItem(OrderItem item) {
		return uiDao.queryOrderItem(item);
	}

	@Override
	public void deleteOrderItem(long orderId) {
		uiDao.deleteOrderItem(orderId);
	}

	@Override
	public void deleteOrderItem(List<Long> items) {
		attachmentDao.deleteByOrderItemId(items);
		uiDao.deleteOrderItemByIdList(items);
	}

	@Override
	public void updateOrderItem(List<OrderItem> items) {
		uiDao.updateOrderItem(items);
	}

	@Override
	public void insertOrderItem(List<OrderItem> items) {
		uiDao.insertOrderItem(items);
	}

	@Override
	public List<OrderCostItem> queryOrderCostItem(OrderCostItem item) {
		return uiDao.queryOrderCostItem(item);
	}

	@Override
	public void deleteOrderCostItem(List<Long> items) {
		uiDao.deleteOrderCostItemByIdList(items);
	}

	@Override
	public void updateOrderCostItem(List<OrderCostItem> items) {
		uiDao.updateOrderCostItem(items);
	}

	@Override
	public void insertOrderCostItem(List<OrderCostItem> items) {
		uiDao.insertOrderCostItem(items);
	}

	@Override
	public List<Provider> queryDestination(Provider provider) {
		return uiDao.queryDestination(null);
	}

	@Override
	public List<SearchOrderVo> judyIsThereOrder(String itemNumber) {
		return uiDao.judyIsThereOrder(itemNumber);
	}

	@Override
	public List<Provider> getProviderAndBalance() {
		return uiDao.getProviderAndBalance();
	}

	@Override
	public Provider getProviderByName(String name) {
		return uiDao.getProviderByName(name);
	}

	@Override
	public void updateProviderBalance(Provider provider) {
		uiDao.updateProviderBalance(provider);
	}

	@Override
	public void addProviderBalance(Provider provider) {
		uiDao.addProviderBalance(provider);
	}
}
