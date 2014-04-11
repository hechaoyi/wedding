$(function() {
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
			var scroll = $("#chatroom").append(elem).height() - $(window).height() + 101;
			$(".content").scrollTop(scroll);
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
})