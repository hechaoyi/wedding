$(function() {
	$("#mobileTxt").on("keyup", function() {
		var isMobile = /^\d{11}$/.test($(this).val())
		var isVCode = /^\d{4}$/.test($("#vcodeTxt").val())
		$("#sendVCodeBtn").prop("disabled", !isMobile);
		$("#submitBtn").prop("disabled", !isMobile || !isVCode);
	});
	$("#vcodeTxt").on("keyup", function() {
		var isMobile = /^\d{11}$/.test($("#mobileTxt").val())
		var isVCode = /^\d{4}$/.test($(this).val())
		$("#submitBtn").prop("disabled", !isMobile || !isVCode);
	});
	$("#sendVCodeBtn").on("click", function() {
		var btn = $("#sendVCodeBtn").prop("disabled", true);
		(function(sec) {
			if(sec > 0) {
				btn.text(sec + "秒后重试");
				var func = arguments.callee;
				setTimeout(function() {
					func(sec - 1)
				}, 1000);
			} else {
				btn.text("发送验证码").prop("disabled", false);
			}
		})(60);
		$.post("/sendVCode", {
			mobile: $("#mobileTxt").val()
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			}
		});
	});
	$("#submitBtn").on("click", function() {
		$.post("/saveMobile", {
			mobile: $("#mobileTxt").val(),
			vcode: $("#vcodeTxt").val()
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			} else {
				window.location.href = data.data;
			}
		});
	});
})
