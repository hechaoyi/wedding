//var USER = {};
//(function() {
//	var socket = new SockJS("/ws");
//	var stomp = Stomp.over(socket);
//	stomp.connect({}, function() {
//		stomp.subscribe("/topic/chat", function(data) {
//			alert(data);
//		});
//	});
//	USER.send = function() {
//		var msg = document.getElementById("message").value;
//		stomp.send("/app/chat", {}, JSON.stringify({ "msg": msg }));
//	}
//})()
$(function() {
	$(".control-item").on("click", function() {
		if($(this).is(".chat"))
			$(".bar-footer").show();
		else
			$(".bar-footer").hide();
	});
})