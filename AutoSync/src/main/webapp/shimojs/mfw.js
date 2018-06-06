let mfwStart = () => {
    let box = [...document.querySelectorAll('.table.table-bordered')][0]
    let tbody  = [...box.querySelectorAll('tbody')]
    for(let i =0; i<tbody.length; i++) {
        let tr = tbody[i].querySelector('tr')
        let td = [...tr.querySelectorAll('td')][17]
        let button = td.querySelector('.btn.btn-xs.btn-primary')
        button.onclick = function () {
            let td = $(this).parent().parent().children('td');
            let data ={
                item: {
                    man: {
                    name: userName
                    },
    
                    //订单号
                    itemNumber: td.eq(1).text().substring(0,16),
                    //出发地
                    departure:td.eq(11).children('p').eq(1).text(),
                    //目的地
                    destination:td.eq(11).children('p').eq(3).text(),
                    //出行日期
                    startDay: td.eq(9).children('p').eq(0).text().substring(5,td.eq(9).children('p').eq(0).text().length),
                    //出行人数
                    userNum: td.eq(10).children('p').eq(0).text()+" "+td.eq(10).children('p').eq(1).text(),
                    //出行天数
                    playDays:td.eq(9).children('p').eq(2).text().substring(5,td.eq(9).children('p').eq(1).text().length) ,
                    //需求
                    need:"",
                    itemCustomer: {
                        name:td.eq(8).children('p').eq(0).text().substring(5, td.eq(8).children('p').text().length),
                        weChat:"",
                        number:td.eq(8).children('p').eq(1).text().substring(7, td.eq(8).children('p').eq(1).text().length)
                    },
                    //用户生成时间
                    orderTimer:td.eq(4).text(),
                    //备注
                    remark: [{
                        content:"无",
                        time:"2017-09-22 17:10:10"
                    }],
                    url: encodeURI(location.href)
    
                }
            }
            let bar = [...document.querySelectorAll('.btn-toolbar')][0]
            let button = bar.querySelector('.btn.btn-success.btn-sm.pull-right')
            button.onclick = function () {
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
                        if(e.code === 0) {
                            alert('上传成功！')
                        }
                    },
                    error(e) {
                        alert(e.value)
                    }
                })
            }

        }
    }

}


if(href.indexOf('seller.mafengwo.cn/#/customize/work') !== -1) {
    setTimeout(() => {
        mfwStart()
    }, 5000)
}