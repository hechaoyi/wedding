$(function() {
	$.noty.defaults.layout = "topRight";
	$.noty.defaults.timeout = 2000;
	$.noty.defaults.maxVisible = 5;
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function() {
		stomp.subscribe("/topic/chat", function(data) {
			var chat = JSON.parse(data.body);
			noty({
				layout: "topLeft",
				type: "alert",
				text: chat.name+"说：<br/>"+chat.msg
			});
		});
		stomp.subscribe("/queue/event", function(data) {
			var event = JSON.parse(data.body);
			switch(event.type) {
			case "ACCESS":
				noty({
					type: "alert",
					text: "在【"+event.detail.source+"】有来宾通过二维码扫描加入互动"
				});
				break;
			case "MOBILE":
				noty({
					type: "information",
					text: "手机号为【"+event.detail.mobile+"】的来宾已经完成了帐号绑定"
				});
				break;
			case "REGISTER":
				noty({
					type: "success",
					text: "恭喜【"+event.detail.name+"】已经完成了注册！"
				});
				break;
			case "BLESS":
				noty({
					layout: "top",
					type: "error",
					maxVisible: 1,
					text: "【"+event.detail.name+"】送出了祝福："+event.detail.bless
				});
				break;
			}
		});
	}, function() {
		alert("与服务器的连接断开了，如需重连请刷新");
	});
	$(".bar-nav button.pull-left").on("click", function() {
		window.location.href = "/admin/dashboard";
	});
})