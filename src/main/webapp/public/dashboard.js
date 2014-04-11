$(function() {
	$.noty.defaults.timeout = 20000;
	var token = /\bl=([^;]+)/.exec(document.cookie)[1];
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function() {
		$("#sendBtn").prop("disabled", false);
		stomp.subscribe("/topic/chat", function(data) {
			var chat = JSON.parse(data.body);
			var elem = $("<div></div>").addClass("message");
			elem.addClass(chat.admin ? "admin" : "user");
			elem.append($("<p></p>").text(chat.name)).append($("<div></div>").text(chat.msg));
			$("#chatroom").prepend(elem.hide());
			$(".content").scrollTop(0);
			elem.slideDown("slow");
		});
		stomp.subscribe("/queue/event", function(data) {
			var event = JSON.parse(data.body);
			switch(event.type) {
			case "ACCESS":
				noty({
					type: "alert",
					text: "在【"+event.detail.source+"】有来宾通过二维码扫描访问系统"
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
					layout: "center",
					type: "error",
					maxVisible: 1,
					text: "<h4>【"+event.detail.name+"】送出了祝福：</h4><blockquote>"+event.detail.bless+"</blockquote>"
				});
				break;
			}
		});
	}, function() {
		alert("与服务器的连接断开了，如需重连请刷新");
	});
	$("#sendBtn").on("click", function() {
		var msg = $("#messageTxt").val();
		if(!msg)
			return;
		stomp.send("/app/chat", {}, JSON.stringify({
			token: token,
			msg: encodeURI(msg)
		}));
		$("#messageTxt").val("");
	});
});
