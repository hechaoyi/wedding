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
    <script src="/public/user.js"></script>
  </head>
  <body>

    <header class="bar bar-nav">
      <button id="logoutBtn" class="btn pull-right">切换用户</button>
      <div class="segmented-control">
        <a class="control-item active chat" href="#chatroom">聊天</a>
        <a class="control-item" href="#blessing">祝词</a>
        <a class="control-item" href="#explore">探索</a>
        <a class="control-item" href="#aboutus">关于</a>
      </div>
    </header>
    <div class="content">
      <div id="chatroom" class="card control-content active">
        聊天
      </div>
      <div id="blessing" class="card control-content">
        祝词
      </div>
      <div id="explore" class="card control-content">
        探索
      </div>
      <div id="aboutus" class="card control-content">
        关于
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
