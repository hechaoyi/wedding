<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>关于我们</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
  </head>
  <body>

    <nav class="bar bar-tab">
      <a class="tab-item" href="/user/chatroom">
        <span class="icon icon-home"></span>
        <span class="tab-label">聊天</span>
      </a>
      <a class="tab-item" href="/user/blessing">
        <span class="icon icon-compose"></span>
        <span class="tab-label">祝词</span>
      </a>
      <a class="tab-item" href="/user/explore">
        <span class="icon icon-search"></span>
        <span class="tab-label">探索</span>
      </a>
      <a class="tab-item active">
        <span class="icon icon-person"></span>
        <span class="tab-label">关于我们</span>
      </a>
      <a class="tab-item" href="/user/logout" data-ignore="push">
        <span class="icon icon-more"></span>
        <span class="tab-label">切换用户</span>
      </a>
    </nav>

    <div class="content">
      <p>
        关于我们
      </p>
    </div>

    <script src="/public/ratchet-2.0.2/js/ratchet.min.js"></script>
    <script src="/webjars/sockjs-client/0.3.4/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.0/stomp.min.js"></script>
    <script src="/public/user.js"></script>
  </body>
</html>
