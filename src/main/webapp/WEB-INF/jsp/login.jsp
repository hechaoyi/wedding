<%@page pageEncoding="utf-8"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>登录列表</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet-theme-ios.min.css">
    <script src="/public/ratchet-2.0.2/js/ratchet.min.js"></script>
  </head>
  <body>

    <header class="bar bar-nav">
      <h1 class="title">登录列表</h1>
    </header>
    <div class="content">
      <ul class="table-view">
        <li class="table-view-divider">以前使用过的帐号：</li>
        <c:forEach items="${users}" var="user">
          <li class="table-view-cell">
            <a class="navigate-right"
              href="/login?mobile=${user.mobile}" data-ignore="push">${user.displayName}</a>
          </li>
        </c:forEach>
        <li class="table-view-divider">重新注册一个帐号：</li>
        <li class="table-view-cell">
          <a class="navigate-right"
            href="/register/step1" data-ignore="push">去注册</a>
        </li>
      </ul>
    </div>

  </body>
</html>
