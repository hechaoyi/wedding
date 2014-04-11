<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>和超逸 &amp; 张伟伟 の 结婚典礼</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet-theme-ios.min.css">
    <script src="/webjars/sockjs-client/0.3.4/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.0/stomp.min.js"></script>
    <script src="/webjars/jquery/2.1.0/jquery.min.js"></script>
    <script src="/webjars/noty/2.2.2/jquery.noty.packaged.min.js"></script>
    <script src="/webjars/jquery-transit/0.9.9/jquery-transit.min.js"></script>
    <script src="/public/scripts/lottery.js"></script>
    <style type="text/css">
.noty_text {
	font-size: 18px;
	line-height: 20px;
}
.card {
	position: absolute;
	width: 280px;
	border-radius: 20px;
	padding: 15px;
}
.card p {
	margin-bottom: 0px;
}
.color1 h4 { color: #99CC33 }
.color1 p { color: #FF6600 }
.color1 { background-color: #CCCCCC }
.color2 h4 { color: #996699 }
.color2 p { color: #FF9999 }
.color2 { background-color: #FF99CC }
.color3 h4 { color: #666600 }
.color3 p { color: #CCCC00 }
.color3 { background-color: #CCCCFF }
.color4 h4 { color: #CC9933 }
.color4 p { color: #CCCC33 }
.color4 { background-color: #FFFF99 }
.color5 h4 { color: #009999 }
.color5 p { color: #66CCCC }
.color5 { background-color: #CCFFFF }
.color6 h4 { color: #FF6666 }
.color6 p { color: #0099CC }
.color6 { background-color: #FFCCCC }
.color7 h4 { color: #333399 }
.color7 p { color: #CCCCFF }
.color7 { background-color: #FFFFCC }
.king h4 { color: #CC0033 }
.king p { color: #990066 }
.king { background-color: #FFCC00; border: 10px solid #ff0000; }
.overlay {
	cursor: pointer;
	background: url(/public/images/overlay.png) repeat 0 0;
	position: fixed;
	width: 100%;
	height: 100%;
	top: 0px;
	left: 0px;
	z-index: 9999;
	overflow: hidden;
}
</style>
  </head>
  <body>

    <header class="bar bar-nav">
      <button class="btn btn-link btn-nav pull-left">
        回到主页 <span class="icon icon-left-nav"></span>
      </button>
      <h1 class="title">和超逸 &amp; 张伟伟 の 结婚典礼</h1>
    </header>
    <div class="content" id="panel" style="overflow:hidden"></div>
    <div style="position:fixed; bottom:0px; right:10px; width:100px;">
      <button id="lotteryBtn" class="btn btn-positive btn-block">开始</button>
    </div>

  </body>
</html>
