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
    <script src="/public/admin.js"></script>
    <style type="text/css">
.message {
    border-radius: 20px;
    margin: 0 15px 10px;
    padding: 15px 20px;
    position: relative;
    margin-right: 320px;
}
.message.admin {
    background-color: #2095FE;
    color: #fff;
}
.message.user {
    background-color: #E5E4E9;
    color: #363636;
}
.message.admin p {
	color: #00ffff;
}
.message.user p {
	color: #cc00cc;
}
.message.admin + .message.admin, .message.user + .message.user {
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
    left: -50px;
    -webkit-transform: rotateY(180deg);
    -moz-transform: rotateY(180deg);
    transform: rotateY(180deg);
    width: 30px;
    z-index: -1;
}
.message.user:before {
    border-color: #E5E4E9;
}
</style>
  </head>
  <body>

    <header class="bar bar-nav">
      <h1 class="title">和超逸 &amp; 张伟伟 の 结婚典礼</h1>
    </header>
    <div class="content">
      <div id="chatroom" class="content-padded" style="margin-bottom:47px"></div>
    </div>
    <div class="bar bar-footer" style="height:37px">
      <input id="messageTxt" type="text" placeholder="消息.."
        style="float:left; width:80%; height:32px; margin:2px 0; padding:0 5px;" />
      <button id="sendBtn" class="btn btn-primary" disabled="disabled"
        style="float:right; width:15%; top:4px;">发送</button>
    </div>

  </body>
</html>
