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
	var check = function() {
		$("#sendBtn").prop("disabled", !connected || !$("#messageTxt").val());
	};
	$("#messageTxt").on("keyup", check);
	setInterval(check, 1000);

	var me = $("#mobileHidden").val();
	var connected = false;
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function() {
		connected = true;
		$("#sendBtn").prop("disabled", !$("#messageTxt").val());
		stomp.subscribe("/topic/chat", function(data) {
			var chat = JSON.parse(data.body);
			var elem = $("<div></div>").addClass("message");
			elem.addClass(chat.mobi == me ? "to" : "from");
			if(chat.mobi == me)
				elem.text(chat.msg);
			else
				elem.append($("<p></p>").text(chat.name)).append($("<div></div>").text(chat.msg));
			$("#chatroom").append(elem).scrollTop($("#chatroom").height());
		});
	});
	$("#sendBtn").on("click", function() {
		stomp.send("/app/chat", {}, JSON.stringify({ msg: encodeURI($("#messageTxt").val()) }));
		$("#messageTxt").val("");
		$("#sendBtn").prop("disabled", true);
	});
})