const url = "http://112.124.107.209:8989/api/order/new";
const href = window.location.href;

let ctripStart = () => {
  const box = document.querySelector(".ant-modal-content");
  const button = [...box.querySelectorAll("button")][1];
  button.onclick = () => {
    let departure = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(2) > ul > li:nth-child(5)"
    ).text();
    let itemNumber = $(".order-id")
      .text()
      .substring(4, $(".order-id").text().length);
    let destination = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(2) > ul > li:nth-child(6)"
    ).text();
    let startDay = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(2) > ul > li:nth-child(3)"
    ).text();
    let userNum = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(2) > ul > li:nth-child(7)"
    ).text();
    let playDays = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(2) > ul > li:nth-child(4)"
    ).text();
    let need = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(2) > div.ant-col-20 > p:nth-child(2)"
    ).text();
    let name = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(4) > ul > li:nth-child(1)"
    ).text();
    let weChat = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(4) > ul > li:nth-child(6)"
    ).text();
    let number = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.ant-row > div.ant-col-20 > div > div.ant-row > div:nth-child(1) > div:nth-child(1) > div.ant-card-body > div > div > div:nth-child(1) > div:nth-child(4) > ul > li:nth-child(4)"
    ).text();
    let orderTimer = $(
      "#root > div > div.ant-layout > div.main-content.ant-layout-content > div.order-info > span.order-date"
    ).text();

    orderTimer = orderTimer.substring(5, orderTimer.length);
    startDay = startDay.substring(0, 10);

    let data = {
      item: {
        man: {
          name: userName,
        },
        url: encodeURI(location.href),
        //订单号
        itemNumber,
        //出发地
        departure,
        //目的地
        destination,
        //出行日期
        startDay,
        //出行人数replace(/\s+/g, "")
        userNum,
        //出行天数
        playDays,
        //需求
        need,
        itemCustomer: {
          name,
          weChat,
          number
        },
        //抢单时间 9.23 15:00
        orderTimer,
        //备注 9.23 15:30
        remark: []
      }
    };
    console.log(data);

    $.ajax({
      type: "POST",
      url,
      headers: {
        userAgent:
          "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3095.5 Safari/537.36",
        contentType: "application/json; charset=utf-8"
      },
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify(data),
      dataType: "JSON",
      success(e) {
        alert(e.value);
      },
      error(callback) {
        console.log(callback);
      }
    });
  };
};

if (href.indexOf("vbooking.ctrip.com/ttl_vbk/ctrip_custom_system/?requireId")) {
  let timer = setInterval(() => {
    const box = document.querySelector(".ant-modal-content");
    if (box) {
      ctripStart();
      clearInterval(timer);
    }
  }, 100);
}
