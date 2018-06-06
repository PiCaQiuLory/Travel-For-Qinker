let qunarStart = () => {
	setTimeout(() => {
		
		[...document.querySelectorAll('.oni-button-text')][0].parentNode.onclick= function() {
			let i = $('.detail').find('.detail_con')
			let j = $('.detail').find('.detail_con').eq(1)
			let data ={
				item: {
					man: {
						name: userName,
					},
					//订单号
					itemNumber: i.find('.row').eq(0).find('.col-4').eq(0).text().trim().substring(4,i.find('.row').eq(0).find('.col-4').eq(0).text().trim().length),
					//出发地
					departure:i.find('.row').eq(1).find('.col-4').eq(0).text().trim().substring(5, i.find('.row').eq(1).find('.col-4').eq(0).text().trim().length),
					//目的地
					destination:i.find('.row').eq(1).find('.col-4').eq(1).text().trim().substring(4, i.find('.row').eq(1).find('.col-4').eq(1).text().trim().length),
					//出行日期
					startDay: i.find('.row').eq(5).find('.col-4').eq(1).text().trim().substring(9, i.find('.row').eq(5).find('.col-4').eq(1).text().trim().length),
					//出行人数
					userNum: i.find('.row').eq(2).find('.col-4').eq(1).text().trim().substring(3, i.find('.row').eq(2).find('.col-4').eq(1).text().trim().length).replace(/\s+/g, ""),
					//出行天数
					playDays:i.find('.row').eq(2).find('.col-5').text().trim().substring(3, i.find('.row').eq(2).find('.col-5').text().trim().length-1),
					//需求
					need:"",
					itemCustomer: {
						name:j.find('.row').eq(0).find('.col-4').eq(0).text().trim().substring(4, j.find('.row').eq(0).find('.col-4').eq(0).text().trim().length),
						weChat:"",
						number:j.find('.row').eq(0).find('.col-4').eq(1).text().trim()
					},
					//用户生成时间
					orderTimer:i.find('.row').eq(3).find('.col-4').eq(0).text().trim().substring(5, i.find('.row').eq(3).find('.col-4').eq(0).text().trim().length),
					//备注
					remark: [],
					url: encodeURI(window.location.href)
	
				}
	
	
			}
			console.log(data);
	
			$.ajax({
				type:"POST",
				url,
				headers: {
					userAgent: "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3095.5 Safari/537.36",
					contentType: "application/json; charset=utf-8"
				},
				contentType: "application/json; charset=utf-8",
				data:JSON.stringify(data),
				dataType: "JSON",
				success(e) {
					if(e === 0) {
						alert('上传成功！')
					} 
				},
				error(e) {
					alert(e.value)
				}
	
			})
		}
	}, 500)
    

}
if (href.indexOf("diy.dujia.qunar.com/sys/order/detail.qunar") !== -1) {
	document.querySelector('.add_remark_btn').onclick= function() {
		qunarStart()
    }
}
