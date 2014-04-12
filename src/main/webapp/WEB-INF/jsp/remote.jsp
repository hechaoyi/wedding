<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>和超逸 &amp; 张伟伟 の 结婚典礼</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/styles/button.css">
    <script src="/webjars/sockjs-client/0.3.4/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.0/stomp.min.js"></script>
    <script src="/webjars/jquery/2.1.0/jquery.min.js"></script>
  </head>
  <body>

    <div class="canvas">
      <h1 class="text">未连接</h1>
      <div class="switch">
        <div class="screws">
          <span class="screw">*</span>
          <span class="screw">*</span>
          <span class="screw">*</span>
          <span class="screw">*</span>
        </div>
        <div class='switch-button-outer'>
          <div class="switch-button">
            <div class="switch-button-inner"></div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
function Switch(node) {
	this.switchRoot = node;
	this.switchRoot.onclick = this.switchClickEventHanlder;
}
Switch.prototype.switchClickEventHanlder = function (e) {
	e.target = e.target || e.srcElement;
	if(e.target.className.indexOf("switch-button") < 0) return;
	if(!this.classList.contains("checked")) {
		this.classList.add("checked");
	} else{
		this.classList.remove("checked");
	}
	this.checked = !this.checked;
};

$(".switch").each(function() {
	new Switch(this);
}).on("click", function() {
	var checked = $(this).is(".checked");
	$(".text").text(checked ? "已经开始" : "等待开始..");
	stomp.send("/queue/ctrl", {}, JSON.stringify({
		event: checked ? "start" : "stop"
	}));
});
var socket = new SockJS("/ws");
var stomp = Stomp.over(socket);
stomp.connect({}, function() {
	$(".text").text("已经连接，等待开始..");
	stomp.subscribe("/queue/ctrl", function(data) {
		var event = JSON.parse(data.body);
		switch(event.event) {
		case "start":
			$(".switch").addClass("checked");
			$(".text").text("已经开始");
			break;
		case "stop":
			$(".switch").removeClass("checked");
			$(".text").text("等待开始..");
			break;
		}
	});
}, function() {
	$(".text").text("与服务器的连接断开了，如需重连请刷新");
});
</script>
  </body>
</html>
