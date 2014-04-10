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

	var connected = false;
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function() {
		connected = true;
		$("#sendBtn").prop("disabled", !$("#messageTxt").val());
		stomp.subscribe("/topic/chat", function(data) {
			alert(data);
		});
	});
	$("#sendBtn").on("click", function() {
		stomp.send("/app/chat", {}, JSON.stringify({ msg: encodeURI($("#messageTxt").val()) }));
		$("#messageTxt").val("");
		$("#sendBtn").prop("disabled", true);
	});
})