$(function() {
	$.noty.defaults.layout = "top";
	$.noty.defaults.timeout = 10000;
	$.noty.defaults.maxVisible = 5;
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
					layout: "center",
					type: "error",
					maxVisible: 1,
					text: "<h4>【"+event.detail.name+"】送出了祝福：</h4><blockquote>"+event.detail.bless+"</blockquote>"
				});
				break;
			case "UPGRADE":
				noty({
					type: "error",
					text: "恭喜【"+event.detail.name+"】由于【"+event.detail.reason+
						"】，幸运值提升到了"+event.detail.weight+"，抽奖环节中奖几率有额外加成哦~"
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
	$(".bar-nav button.pull-right").on("click", function() {
		window.location.href = "/admin/lottery";
	});
});
