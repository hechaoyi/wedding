<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>和超逸 &amp; 张伟伟 の 结婚典礼</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet-theme-ios.min.css">
    <script src="/public/ratchet-2.0.2/js/ratchet.min.js"></script>
    <script src="/webjars/sockjs-client/0.3.4/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.0/stomp.min.js"></script>
    <script src="/public/zepto-1.1.3/zepto.min.js"></script>
    <script src="/public/scripts/user.js"></script>
    <style type="text/css">
.message {
    border-radius: 8px;
    margin: 0 15px 10px;
    padding: 5px 10px;
    position: relative;
    font-size: 14px;
}
.message.to {
    background-color: #2095FE;
    color: #fff;
    margin-left: 80px;
}
.message.from {
    background-color: #E5E4E9;
    color: #363636;
    margin-right: 80px;
}
.message.admin {
    background-color: #2095FE;
    color: #fff;
    margin-right: 80px;
}
.message.from p {
	color: #cc00cc;
}
.message.admin p {
	color: #00ffff;
}
.message.to + .message.to, .message.from + .message.from, .message.admin + .message.admin {
  margin-top: -7px;
}
.message:before {
    border-color: #2095FE;
    border-radius: 50% 50% 50% 50%;
    border-style: solid;
    border-width: 0 20px;
    bottom: 0;
    clip: rect(20px, 35px, 42px, 0px);
    content: " ";
    height: 40px;
    position: absolute;
    right: -50px;
    width: 30px;
    z-index: -1;
}
.message.from:before {
    border-color: #E5E4E9;
    left: -50px;
    -webkit-transform: rotateY(180deg);
    -moz-transform: rotateY(180deg);
    transform: rotateY(180deg);
}
.message.admin:before {
    left: -50px;
    -webkit-transform: rotateY(180deg);
    -moz-transform: rotateY(180deg);
    transform: rotateY(180deg);
}
</style>
  </head>
  <body>

    <header class="bar bar-nav">
      <input type="hidden" id="mobileHidden" value="${mobile}" />
      <button id="logoutBtn" class="btn pull-right">切换用户</button>
      <div class="segmented-control">
        <a class="control-item active chat" href="#chatroom">聊天</a>
        <a class="control-item" href="#blessing">祝词</a>
      </div>
    </header>
    <div class="content">
      <div id="chatroom" class="content-padded control-content active" style="margin-bottom:47px"></div>
      <div id="blessing" class="card control-content">
        祝词
      </div>
    </div>
    <div class="bar bar-footer" style="height:37px">
      <input id="messageTxt" type="text" placeholder="消息.."
        style="float:left; width:80%; height:32px; margin:2px 0; padding:0 5px;" />
      <button id="sendBtn" class="btn btn-primary" disabled="disabled"
        style="float:right; width:15%; top:4px;">发送</button>
    </div>

  </body>
</html>
