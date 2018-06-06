let tuniuStart = () => {
    [...document.querySelectorAll('.el-button.el-button--primary')][5].onclick = function() {
        let data ={
			item: {
				man: {
					name: userName
				},

				//订单号
				itemNumber: $('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)').text().substring(5, 11),
				//出发地
				departure:$('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1)').text().trim().split('：')[1],
				//目的地
				destination: $('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(2)').text().trim().split('：')[1],
				//出行日期
				startDay: $('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(3) > div:nth-child(1)').text().trim().split('：')[1],
				//出行人数
				userNum: $('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)').text().trim().substring(5, $('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(2) > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(3)').text().trim().length-2),
				//出行天数
				playDays:0,
				//需求
				need:"",
				itemCustomer: {
					name:$('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(3) > div > div > div > div:nth-child(1)').text().trim().split('：')[1],
					weChat:"",
					number:$('#app > div > div.row-bg.main > div > div.wrap > div > div:nth-child(3) > div > div > div > div:nth-child(2)').text().trim().split('：')[1]
				},
				//用户生成时间
				orderTimer: getTime(),
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
}
let getTime =  () => {

    let date = new Date()
    let seperator1 = "-"
    let seperator2 = ":"
    let month = date.getMonth() + 1
    let strDate = date.getDate()

    if (month >= 1 && month <= 9)  month = `0${month}`
    if (strDate >= 0 && strDate <= 9)  strDate = `0${strDate}`

    let time = date.getFullYear() + seperator1 + month + seperator1 + strDate
    + " " + date.getHours() + seperator2 + date.getMinutes()
    + seperator2 + date.getSeconds();

    return time
}
if(href.indexOf('mall.tuniu.cn/#/design/demand/') !== -1) {
    let timer = setInterval(() => {
        tuniuStart()
    }, 100)
}