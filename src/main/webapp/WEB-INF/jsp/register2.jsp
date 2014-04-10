<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>快速注册 &rsaquo; 第2步</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet.min.css">
    <link rel="stylesheet" href="/public/ratchet-2.0.2/css/ratchet-theme-ios.min.css">
    <script src="/public/ratchet-2.0.2/js/ratchet.min.js"></script>
    <script src="/public/zepto-1.1.3/zepto.min.js"></script>
    <script src="/public/register2.js"></script>
  </head>
  <body>

    <header class="bar bar-nav">
      <h1 class="title">快速注册 &rsaquo; 第2步</h1>
    </header>
    <div class="content">
      <div class="card">
        <div class="content-padded">
          <label>您怎么称呼？~</label>
          <p>您是谁的亲朋好友？请从下方列表中选择</p>
          <ul class="table-view">
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="male_family" id="category_male_family" />
              <label for="category_male_family" style="margin-left:20px">新郎和超逸的家人</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="female_family" id="category_female_family" />
              <label for="category_female_family" style="margin-left:20px">新娘张伟伟的家人</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="neu_classmate" id="category_neu_classmate" />
              <label for="category_neu_classmate" style="margin-left:20px">东北大学同学</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="male_classmate" id="category_male_classmate" />
              <label for="category_male_classmate" style="margin-left:20px">新郎和超逸的同学</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="female_workmate" id="category_female_workmate" />
              <label for="category_female_workmate" style="margin-left:20px">新娘张伟伟的同事</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="male_father_friend" id="category_male_father_friend" />
              <label for="category_male_father_friend" style="margin-left:20px">新郎父亲的同事和朋友</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="male_mother_friend" id="category_male_mother_friend" />
              <label for="category_male_mother_friend" style="margin-left:20px">新郎母亲的同事和朋友</label>
            </li>
            <li class="table-view-cell" style="padding-top:3px; padding-bottom:3px;">
              <input type="radio" name="category" value="other" id="category_other" />
              <label for="category_other" style="margin-left:20px">其他亲朋好友</label>
            </li>
          </ul>
          <p>怎么称呼您比较方便？长辈建议输入称谓，如大姨、张老师、刘科长，平辈建议直接输入姓名</p>
          <input id="nameTxt" type="text" name="name" placeholder="称谓或姓名.." />
          <button id="submitBtn" class="btn btn-primary btn-block" disabled="disabled">下一步</button>
        </div>
      </div>
    </div>

  </body>
</html>
