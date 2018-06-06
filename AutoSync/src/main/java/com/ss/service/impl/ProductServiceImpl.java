package com.ss.service.impl;


import com.ss.api.AliService;
import com.ss.api.CtripService;
import com.ss.mapper.IProductDao;
import com.ss.mapper.IUiDao;
import com.ss.pojo.*;
import com.ss.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("productService")
@Slf4j
public class ProductServiceImpl implements IProductService {


	@Resource
	private IProductDao productDao;
	
	@Resource
	private IUiDao uiDao;
	
	@SuppressWarnings("deprecation")
	@Override
	public void saveOrUpdateProduct(List<Product> list) {
		if(CollectionUtils.isEmpty(list)){
			return ;
		}
		String sourceId = list.get(0).getSourceId();
		List<Product> productList = productDao.getUpdatePro(sourceId);
		
		List<Product> updateList = new ArrayList<>();
		List<Product> saveList = new ArrayList<>();
		
		boolean zeroFlag = true;
		for (Product product : list) {
			if(new Double(0).compareTo(product.gettSprice())!=0 || 
					new Double(0).compareTo(product.gettPrice())!=0 ||
					new Double(0).compareTo(product.gettCPrice())!=0){
				zeroFlag = false;
				break;
			}
		}
		if(zeroFlag){
			log.warn("all price is 0 for product " + list.get(0).getSourceId());
			// update status and rsn
			SourceProduct sp = new SourceProduct();
			sp.setId(Long.valueOf(sourceId));
			sp.setmStatus("pending");
			sp.setReasonCode("zero");
			productDao.updateSourceProductStatus(sp);
		}
		
		for (Product productIncoming : list) {
			if(productIncoming.gettDate().before(new Date())){
				continue;
			}
			// set stock to 0 when there is no price
			if(new Double(0).compareTo(productIncoming.gettPrice())==0 &&
					new Double(0).compareTo(productIncoming.gettCPrice())==0){
				productIncoming.setStock(0);
			}else if(8<productIncoming.getStock()){
				productIncoming.setStock(8);
			}
		}
		
		if(CollectionUtils.isEmpty(productList)){
			log.info("no recrod exists in db, save as new product");
			saveList.addAll(list);
		}else{
			
			boolean limitFlag = false;
			SourceProduct percentSp = getPercentage(sourceId);
			log.info("source id is " + sourceId + ", percentSp is " + percentSp );
			Float increasePer = (percentSp==null||percentSp.getIncreasePer()==null)?new Float(0.0):percentSp.getIncreasePer();
			Float decreasePer = (percentSp==null||percentSp.getDecreasePer()==null)?new Float(0.0):percentSp.getDecreasePer();
			
			for (Product productIncoming : list) {
				
				boolean matchFlag = false;
				for (Product productExists : productList) {
					if(productExists.gettDate().compareTo(productIncoming.gettDate())==0){
						matchFlag = true;
						log.info(" incoming Price is " + productIncoming.gettPrice() + ", exists Price is " + productExists.gettPrice()
						+ ", increasePer is " + increasePer + ", decreasePer is " + decreasePer);
						if(productExists.gettSprice().compareTo(productIncoming.gettSprice())==0
								&& productExists.gettCPrice().compareTo(productIncoming.gettCPrice())==0
								&& productExists.gettPrice().compareTo(productIncoming.gettPrice())==0
								&& (productExists.getStock()==productIncoming.getStock()||
										(productExists.getStock()==8&&productIncoming.getStock()>8)
										)){
							// do nothing for now
						}else if( 
								( productExists.gettPrice()- productIncoming.gettPrice()>0 &&
								(productExists.gettPrice()- productIncoming.gettPrice())/ productExists.gettPrice()> decreasePer )
								||
								( productIncoming.gettPrice()- productExists.gettPrice()>0 &&
										(productIncoming.gettPrice()- productExists.gettPrice())/ productExists.gettPrice()> increasePer )
								){
							if(Math.abs(productExists.gettSprice()-productIncoming.gettSprice())> productExists.getLimit()
									|| Math.abs(productExists.gettCPrice()-productIncoming.gettCPrice())> productExists.getLimit()
									|| Math.abs(productExists.gettPrice()-productIncoming.gettPrice()) > productExists.getLimit()){
										limitFlag =true;
									}
							updateList.add(productIncoming);
						}
						else{
							log.info("will not update for source " + sourceId + ", t_date " + productIncoming.gettDate());
						}
					}else{
						continue;
					}
				}
				if(!matchFlag){
					saveList.add(productIncoming);
				}
				
				if(limitFlag){
					log.warn("price over than default limit for source id:"+productIncoming.getSourceId() +", date:"+ productIncoming.gettDate());
					// update status and rsn
					TargetProduct tp = new TargetProduct();
					tp.setSourceId(Long.valueOf(sourceId));
					tp.setmStatus("pending");
					tp.setReasonCode("limit");
					productDao.updateTargetProductStatus(tp);
				}
				
			}
		}
		
		if( updateList.size()>0){
			log.info("update " + updateList.size()+ "records for source " + sourceId);
			// 2. do update
			for (Product updatePro : updateList) {
				productDao.updateProduct(updatePro);
			}
			log.info(updateList.size() + " were updated for " + sourceId);
			boolean childFlag = false;
			for (Product product : updateList) {
				if(product.gettPrice() < product.gettCPrice()){
					childFlag = true;
					log.warn("child price is over than tprice for source " + product.getSourceId() + ", date " + product.gettDate() );
					break;
				}
			}
			if(childFlag){
				TargetProduct tp = new TargetProduct();
				// update status and rsn
				tp.setSourceId(Long.valueOf(sourceId));
				tp.setmStatus("pending");
				tp.setReasonCode("child");
				productDao.updateTargetProductStatus(tp);
			}
		}
		if(saveList.size()>0){
			log.info("save " + saveList.size()+ "records for source " + sourceId);
			boolean childFlag = false;
			for (Product product : saveList) {
				
				if(product.gettPrice() < product.gettCPrice()){
					childFlag = true;
					log.warn("child price is over than tprice for source " + product.getSourceId() + ", date " + product.gettDate() );
				}
			}
			// 3. do add
			productDao.insertProduct(saveList);
			log.info(saveList.size() + " were saved for " + sourceId);
			boolean singleFlag = false;
			for (Product product : saveList) {
				if(new Double(0).compareTo(product.gettSprice())==0){
					singleFlag = true;
					break;
				}
			}
			if(singleFlag){
				TargetProduct tp = new TargetProduct();
				tp.setSourceId(Long.valueOf(saveList.get(0).getSourceId()));
				tp.setmStatus("pending");
				tp.setReasonCode("single");
				productDao.updateTargetProductStatus(tp);
			}
			if(childFlag){
				// update status and rsn
				TargetProduct tp = new TargetProduct();
				tp.setSourceId(Long.valueOf(sourceId));
				tp.setmStatus("pending");
				tp.setReasonCode("child");
				productDao.updateTargetProductStatus(tp);
			}
		}
		
		// update by packageName 
		List<String> sources = getAllSourceId(sourceId);
		if(sources!=null && sources.size()>0){
			updateList.addAll(saveList);
			for (String source : sources) {
				for (Product product : updateList) {
					product.setSourceId(source);
					//get offset
					Integer offset = 0;
					List<Integer> offsetL = productDao.getOffset(source);
					if(offsetL!=null && offsetL.size()>0){
						offset = offsetL.get(0);
					}
					Date dt = product.gettDate();
					dt.setDate(product.gettDate().getDate()+offset);
					product.settDate(dt);
					productDao.updateProductPackage(product);
				}
			}
		}
		
		syncProduct(sourceId);
	}
	
	public SourceProduct getPercentage(String sourceId){
		return productDao.getPercentage(sourceId);
	}
	
	public List<String> getAllSourceId(String sourceId){
		return productDao.getSourceId(sourceId);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void syncProduct(String sourceId){
		List<OutputTemlate> list = null;
		if(sourceId==null){
			list = productDao.getOutputList();
		}else{
			list = productDao.getOutputListForUpdate(sourceId);
		}
		if(list==null || list.size()==0){
			log.debug("no records to send");
			return;
		}
		
		for (OutputTemlate ot : list) {
			if(ot.getDefaultChildPrice()!=null&&ot.getDefaultChildPrice()>0){
				ot.settCPrice(ot.getDefaultChildPrice());
			}else if(ot.gettPrice().compareTo(new Double(0))>0 && ot.gettCPrice().compareTo(new Double(0))==0){
				// 如果成人价格有。儿童价格没有，则设置 库存1，儿童价=成人价
//				ot.setStock(1);
				ot.settCPrice(ot.gettPrice());
			}
//			if(ot.gettPrice().compareTo(new Double(0))==0
//					&& adPriceMap.get(ot.getTargetProductId()).compareTo(new Double(6666))==0){
//				ot.settPrice(adPriceMap.get(ot.getTargetProductId()));
//				if(ot.gettCPrice().compareTo(new Double(0))==0
//						&& chPriceMap.get(ot.getTargetProductId()).compareTo(new Double(6666))==0){
//					ot.settCPrice(chPriceMap.get(ot.getTargetProductId()));
//				}
//			}
		}
		
		log.debug("calculate price");
		list.forEach(x -> getPrice(x, false));

		/*
		 * 如果价格为0，则把价格设置为最高价格
		 */
		Map<String, Double> adPriceMap = new HashMap<>();
		Map<String, Double> chPriceMap = new HashMap<>();
		for (OutputTemlate ot : list) {
			if(adPriceMap.get(ot.getTargetProductId())!=null){
				if(adPriceMap.get(ot.getTargetProductId()).compareTo(ot.gettPrice())<=0){
					adPriceMap.put(ot.getTargetProductId(), ot.gettPrice());
				}
				
				if(chPriceMap.get(ot.getTargetProductId()).compareTo(ot.gettCPrice())<=0){
					chPriceMap.put(ot.getTargetProductId(), ot.gettCPrice());
				}
			}else{
				adPriceMap.put(ot.getTargetProductId(), ot.gettPrice());
				chPriceMap.put(ot.getTargetProductId(), ot.gettCPrice());
			}
			
		}
		
		//如果所有价格都是0，则设置为6666
//		adPriceMap.entrySet().forEach( x -> {if(x.getValue().compareTo(new Double(0))==0) x.setValue(new Double(6666));});
//		chPriceMap.entrySet().forEach( x -> {if(x.getValue().compareTo(new Double(0))==0) x.setValue(new Double(6666));});

		
		List<OutputTemlate> ctripList = new ArrayList<>();
		List<OutputTemlate> aliist = new ArrayList<>();
		// split target source
		for (OutputTemlate ot : list) {
			if("ctrip".equals(ot.getTargetSource())){
				ctripList.add(ot);
			}else{
				aliist.add(ot);
			}
		}
		
		if(ctripList.size()>0){
			
			List<OutputTemlate> pkgList = new ArrayList<>();
			// split ctrip list
			for (OutputTemlate outputTemlate : ctripList) {
				// get all lists with package name 
				if(outputTemlate.getPackageName()!=null &&outputTemlate.getPackageName().length()>0 ){
					pkgList.add(outputTemlate);
				}
			}
			// sync the list without packageName
			ctripList.removeAll(pkgList);
			syncWithCtrip(ctripList);
			
			
			Map<String, List<OutputTemlate>> pkgMap = new HashMap<>();
		
			// group by targetId
			for (OutputTemlate pkgOt : pkgList) {
				if(pkgMap.get(pkgOt.getTargetProductId())!=null){
					pkgMap.get(pkgOt.getTargetProductId()).add(pkgOt);
				}else{
					List<OutputTemlate> newPkgList = new ArrayList<>();
					newPkgList.add(pkgOt);
					pkgMap.put(pkgOt.getTargetProductId(), newPkgList);
				}
			}
			if(pkgMap.size()>0){
				List<OutputTemlate> syncList = new ArrayList<>();
				// set baseAmount and pkgAmount for each 
				
				for (List<OutputTemlate> tgtList : pkgMap.values()) {
					
					for (OutputTemlate outputTemlate : tgtList) {
						if(outputTemlate.getMainFlag()!=null && "Y".equalsIgnoreCase(outputTemlate.getMainFlag().trim())){
							OutputTemlate ot = new OutputTemlate();
							ot = outputTemlate;
							
							List<OutputTemlate> optionList = new ArrayList<>();
							
							if(ot.gettPrice()==null ||  ot.gettPrice()==0){
								
							}else{
								OutputTemlate optionMain = new OutputTemlate();
								getPrice(ot, true);
								optionMain.settPrice(ot.gettPrice()-ot.getBaseAmount());
								optionMain.setPackageName(ot.getPackageName());
								optionList.add(optionMain);
							}
							
							
							for (OutputTemlate tgt : tgtList) {
								Date compareDt = new Date(tgt.gettDate().getTime());
								compareDt.setDate(tgt.gettDate().getDate()+tgt.getOffset());
								if( (tgt.getMainFlag()==null || !"Y".equalsIgnoreCase( tgt.getMainFlag().trim()))
										&& outputTemlate.gettDate().compareTo(compareDt)==0){
									if(tgt.gettPrice()==null ||  tgt.gettPrice()==0){
										continue;
									}
									getPrice(tgt, true);
									OutputTemlate option = new OutputTemlate();
									option.settPrice(tgt.gettPrice()-ot.getBaseAmount());
									option.setPackageName(tgt.getPackageName());
									optionList.add(option);
								}
							}
							ot.setPackageList(optionList);
							ot.settPrice(ot.getBaseAmount());
							if(ot.gettSprice()==null || ot.gettSprice()==0){
								ot.settSprice(ot.getBaseAmount());
							}
							if(ot.gettCPrice()==null || ot.gettCPrice()==0){
								ot.settCPrice(ot.getBaseAmount());
							}
							//hardcode to 8 for package products
							ot.setStock(8);
							syncList.add(ot);
						}
					}
					
				}
				
				syncPackageWithCtrip(syncList);
			}
		}

		if(aliist.size()>0){
			syncWithAliCtip(aliist);
		}
	}
	
	@Override
	public void syncProduct() {
//		syncProduct(null);

	}

	private void syncWithAliCtip(List<OutputTemlate> list){
		if(list.size()==0){
			return ;
		}
		VendorResponse vr = new VendorResponse();
		vr.setVendorId("alibaba");
		List<VendorResponse> vrList = uiDao.queryVendorResponse(vr);
		if(vrList.size()==0){
			log.warn("cannot find token configure from database");
			return;
		}
		for (OutputTemlate ot : list) {
			ot.setToken(vrList.get(0).getToken());
		}
		
		int count = AliService.updatePriceAndStock(list);
		
		for (OutputTemlate outputTemlate : list) {
			if(outputTemlate.isSent()){
				TargetProduct tp = new TargetProduct();
				tp.setId(outputTemlate.getId());
				productDao.updateLastSyncDatetime(tp);
			}
		}
		
		// update count
		updateCount("ali", count);
	}
	
	private synchronized void syncWithCtrip(List<OutputTemlate> list){
		log.debug("send to ctrip");
		Set<String> failedSet = new HashSet<String>();
		
		List<OutputTemlate> groupList = groupByDate(list);
		
		groupList.forEach(x -> {
			try {
				// CTRIP interface changed : if the price is 0, do not send request.
				if(new Double(0).compareTo( x.gettPrice())==0
						&& new Double(0).compareTo(x.gettCPrice())==0){
					x.setSent(false);
				}else{
					String s = CtripService.syncProduct(x);
					if(s!=null && s.length()>0){
						failedSet.add(s);
					}
					Thread.sleep(300);
				}
			} catch (Exception e) {
				log.error("got exception when sync price for source id:"+x.getSourceId() + ", date:" + x.gettDate());
				x.setSent(false);
				log.error(e.getMessage(), e);
			}
		});
		
		int count = groupList.size();
				
		synchronized (new Object()) {
			List<OutputTemlate> stockGroupList = groupByDateForStock(list);
			count += stockGroupList.size();
			for (OutputTemlate x : stockGroupList) {
				try {
					synchronized (new Object()) {
						String s = CtripService.syncStock(x);
						if(s!=null && s.length()>0){
							failedSet.add(s);
						}
						Thread.sleep(300);
					}
					
				} catch (Exception e2) {
					log.error("got exception when sync stock for source id:"+x.getSourceId() + ", date:" + x.gettDate());
					log.error(e2.getMessage(), e2);
				}
			}
			
		}
		
		
		groupList.forEach(x -> updateStatusAfterSend(x));
		
		for (OutputTemlate outputTemlate : list) {
			if( failedSet.contains( outputTemlate.getTargetProductId())){
				log.warn("sync target product " +  outputTemlate.getTargetProductId() + " failed, will not update last sync datetime");
				continue;
			}
			TargetProduct tp = new TargetProduct();
			tp.setId(outputTemlate.getId());
			productDao.updateLastSyncDatetime(tp);
		}
		
		// update count
		updateCount("ctrip", count);
		
	}
	
	private synchronized void syncPackageWithCtrip(List<OutputTemlate> list){
		log.debug("send to ctrip");
		Set<String> failedSet = new HashSet<String>();
		int count = 0;
		for(OutputTemlate x : list){
			try {
				// CTRIP interface changed : if the price is 0, do not send request.
				if(new Double(0).compareTo( x.gettPrice())==0
						&& new Double(0).compareTo(x.gettCPrice())==0){
					x.setSent(false);
				}else{
					count++;
					String s = CtripService.syncProduct(x);
					if(s!=null && s.length()>0){
						failedSet.add(s);
					}
					Thread.sleep(300);
				}
			} catch (Exception e) {
				log.error("got exception when sync price for source id:"+x.getSourceId() + ", date:" + x.gettDate());
				x.setSent(false);
				log.error(e.getMessage(), e);
			}
		}
		
		synchronized (new Object()) {
			count += list.size();
			for (OutputTemlate x : list) {
				try {
					synchronized (new Object()) {
						String s = CtripService.syncStock(x);
						if(s!=null && s.length()>0){
							failedSet.add(s);
						}
						Thread.sleep(300);
					}
					
				} catch (Exception e2) {
					log.error("got exception when sync stock for source id:"+x.getSourceId() + ", date:" + x.gettDate());
					log.error(e2.getMessage(), e2);
				}
			}
			
		}
		
		
		list.forEach(x -> updateStatusAfterSend(x));
		
		for (OutputTemlate outputTemlate : list) {
			if( failedSet.contains( outputTemlate.getTargetProductId())){
				log.warn("sync target product " +  outputTemlate.getTargetProductId() + " failed, will not update last sync datetime");
				continue;
			}
			TargetProduct tp = new TargetProduct();
			tp.setId(outputTemlate.getId());
			productDao.updateLastSyncDatetime(tp);
		}
		
		// update count
		updateCount("ctrip", count);
		
	}
	
	public void updateCount(String target, int count){
		Map<String, Object> map = new HashMap<>();
		map.put("target",	 target);
		map.put("count",	 count);
		map.put("tDate",	 new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		int updated = productDao.updateCount(map);
		if(updated==0){
			productDao.insertCount(map);
		}
	}
	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	}
	
	private List<OutputTemlate> groupByDate(List<OutputTemlate> incomingList){
		List<OutputTemlate> otList = new ArrayList<>();
		Map<String, OutputTemlate> groupMap = new HashMap<>();
		if(incomingList!=null){
			for (OutputTemlate ot : incomingList) {
				String key = ot.getSourceId()+ot.gettPrice()+ot.gettCPrice()+ot.gettSprice() + ot.getTargetProductId();
				if(groupMap.containsKey(key)){
					groupMap.get(key).gettDateList().add(ot.gettDate());
				}else{
					ot.settDateList(new ArrayList<>());
					ot.gettDateList().add(ot.gettDate());
					groupMap.put(key, ot);
				}
			}
			otList.addAll(groupMap.values());
		}
		
		return otList;
	}
	
	private List<OutputTemlate> groupByDateForStock(List<OutputTemlate> incomingList){
		List<OutputTemlate> otList = new ArrayList<>();
		Map<String, OutputTemlate> groupMap = new HashMap<>();
		if(incomingList!=null){
			for (OutputTemlate ot : incomingList) {
				String key = ot.getTargetProductId()+ot.getStock();

				if(groupMap.containsKey(key)){
					groupMap.get(key).gettDateList().add(ot.gettDate());
				}else{
					ot.settDateList(new ArrayList<>());
					ot.gettDateList().add(ot.gettDate());
					groupMap.put(key, ot);
				}
			}
			otList.addAll(groupMap.values());
		}
		
		return otList;
	}
	
	private void updateStatusAfterSend(OutputTemlate ot){
		if(ot.isSent()){
			if(ot.gettDateList()!=null && ot.gettDateList().size()>0){
				for (Date dt : ot.gettDateList()) {
					Product product = new Product();
					product.settDate(dt);
					product.setSourceId(ot.getSourceId());
					product.setSendInd("N");
					productDao.updateProductStatus(product);
				}
			}else{
				Product product = new Product();
				product.settDate(ot.gettDate());
				product.setSourceId(ot.getSourceId());
				product.setSendInd("N");
				productDao.updateProductStatus(product);
			}
		}
	}
	
	private void getPrice(OutputTemlate ot, boolean forOption){
		if(!forOption){
			if(ot.getPackageName()!=null&& ot.getPackageName().length()>0 && "ctrip".equals(ot.getTargetSource())){
				return;
			}
		}else{
			if("ctrip".equals(ot.getTargetSource()) && (ot.getPackageName()==null|| ot.getPackageName().length()==0) ){
				return;
			}
		}
		ot.settPrice(calculate(StringUtils.trimAllWhitespace(ot.getPriceDelta()), ot.gettPrice()));
		ot.settCPrice(calculate(ot.getcPriceDelta(), ot.gettCPrice()));
		if(ot.gettCPrice()> ot.gettPrice()){
			ot.settCPrice( ot.gettPrice());
		}
		ot.settSprice(calculateS(StringUtils.trimAllWhitespace(ot.getsPriceDelta()), ot.gettSprice()));
		if(new Double(-1.0).compareTo(ot.gettPrice())==0 ||
				new Double(-1.0).compareTo(ot.gettCPrice())==0 ||
				new Double(-1.0).compareTo(ot.gettSprice())==0 ){
			ot.setSent(true);
		}else{
			// calculate price with ratio again
			Double ratio = ot.getRatio();
			if(ratio!=null && ratio.compareTo(new Double(0))!=0){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				long to = ot.gettDate().getTime();
			    long from =0l;
				try {
					from = df.parse(df.format(new Date())).getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Long days = ((to - from) / (1000 * 60 * 60 * 24));
				if(days<0){
					days = 0l;
				}
				Double newTprice = ot.gettPrice()*(1+ratio*days);
				Double newCprice = ot.gettCPrice()*(1+ratio*days);
				Double newSprice = ot.gettSprice()*(1+ratio*days);
				ot.settPrice(Double.valueOf(newTprice.intValue()));
				ot.settCPrice(Double.valueOf(newCprice.intValue()));
				ot.settSprice(Double.valueOf(newSprice.intValue()));
			}
		}
	}
	
	public Double calculate(String delta, Double p){
		log.debug("calculate - amount is " + p + ", delta is " + delta);
		if(delta==null 
				|| delta.length()<2 
				|| !(delta.startsWith("+") || delta.startsWith("-") || delta.startsWith("*")) || p==null ){
			return -1.0;
		}
		if(new Double(0).compareTo(p)==0){
			return p;
		}
		Double amount = null;
		String s = delta.substring(1);
		if(s.contains("+")){
			String[] a = s.split("\\+");
			amount = doAmount(delta.substring(0, 1), p, Double.valueOf(a[0]));
			p = doAmount("+", amount, Double.valueOf(a[1]));
		}else if(s.contains("*")){
			String[] a = s.split("\\*");
			amount = doAmount(delta.substring(0, 1), p, Double.valueOf(a[0]));
			p = doAmount("*", amount, Double.valueOf(a[1]));
		}else{
			try {
				amount = Double.valueOf(delta.substring(1));
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				return -1.0;
			}
			p = doAmount(delta.substring(0, 1), p, amount);
		}


		log.debug("calculate - final amount is  " + p);
		return Math.ceil(p);
	}

	//calculate single price
	public Double calculateS(String delta, Double p){
		log.debug("calculate - amount is " + p + ", delta is " + delta);
		if(delta==null
				|| delta.length()<2
				|| !(delta.startsWith("+") || delta.startsWith("-") || delta.startsWith("*")) || p==null ){
			return -1.0;
		}
		Double amount = null;
		String s = delta.substring(1);
		if(s.contains("+")){
			String[] a = s.split("\\+");
			amount = doAmount(delta.substring(0, 1), p, Double.valueOf(a[0]));
			p = doAmount("+", amount, Double.valueOf(a[1]));
		}else if(s.contains("*")){
			String[] a = s.split("\\*");
			amount = doAmount(delta.substring(0, 1), p, Double.valueOf(a[0]));
			p = doAmount("*", amount, Double.valueOf(a[1]));
		}else{
			try {
				amount = Double.valueOf(delta.substring(1));
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				return -1.0;
			}
			p = doAmount(delta.substring(0, 1), p, amount);
		}
		log.debug("calculate - final amount is  " + p);
		return Math.ceil(p);
	}

	private Double doAmount(String method,Double p, Double amount){
		switch (method) {
			case "+":
				p = p + amount;
				break;
			case "-":
				p = p - amount;
				break;
			case "*":
				p = p * amount;
				break;
			default:
				break;
		}
		return p;
	}
	
}
