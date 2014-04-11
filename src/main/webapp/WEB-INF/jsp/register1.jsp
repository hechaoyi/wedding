<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>快速注册 &rsaquo; 第1步</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet-theme-ios.min.css">
    <script src="/public/ratchet-2.0.2/js/ratchet.min.js"></script>
    <script src="/public/zepto-1.1.3/zepto.min.js"></script>
    <script src="/public/scripts/register1.js"></script>
  </head>
  <body>

    <header class="bar bar-nav">
      <h1 class="title">快速注册 &rsaquo; 第1步</h1>
    </header>
    <div class="content">
      <div class="card">
        <div class="content-padded">
          <label>请输入您的手机号码</label>
          <p>输入手机号码后，点击发送验证码。稍后您会收到一条短信，在下方输入4位短信验证码后点击下一步</p>
          <div style="overflow:hidden">
            <input id="mobileTxt" type="text" name="mobile" placeholder="手机号码.." style="float:left; width:70%;" />
            <button id="sendVCodeBtn" class="btn btn-positive" style="float:right; width:25%; margin-top:6px;"
              disabled="disabled">发送验证码</button>
          </div>
          <div style="overflow:hidden">
            <input id="vcodeTxt" type="text" name="vcode" placeholder="短信验证码.." style="float:left; width:70%;" />
          </div>
          <button id="submitBtn" class="btn btn-primary btn-block" disabled="disabled">下一步</button>
        </div>
      </div>
    </div>

  </body>
</html>
