$(function() {
	$(".control-item").on("click", function() {
		if($(this).is(".chat"))
			$(".bar-footer").show();
		else
			$(".bar-footer").hide();
	});
	$("#logoutBtn").on("click", function() {
		window.location.href = "/user/logout";
	});

	var token = /\bl=([^;]+)/.exec(document.cookie)[1];
	var me = $("#mobileHidden").val();
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function(frame) {
		$("#sendBtn").prop("disabled", false);
		stomp.subscribe("/topic/chat", function(data) {
			var chat = JSON.parse(data.body);
			var elem = $("<div></div>").addClass("message");
			elem.addClass(chat.mobi == me ? "to" : "from");
			if(chat.mobi == me)
				elem.text(chat.msg);
			else
				elem.append($("<p></p>").text(chat.name)).append($("<div></div>").text(chat.msg));
			$("#chatroom").append(elem);
			var scroll = $("#chatroom").height() - $(window).height() + 81;
			$("#chatroom").scrollTop(scroll);
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