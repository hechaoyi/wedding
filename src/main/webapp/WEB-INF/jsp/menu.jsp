<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>菜单</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
  </head>
  <body>

    <a href="/admin/dashboard">面板</a>
    <a href="/admin/lottery">抽奖</a>
    <a href="/admin/remote">遥控器</a>

    <form action="/user/upgrade" method="POST">
      <label>nickname</label><input type="text" name="name" />
      <label>passcode</label><input type="text" name="passcode" />
      <input type="submit" value="提交" />
    </form>

  </body>
</html>
