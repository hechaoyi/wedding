$(function() {
	$("#mobileTxt").on("keyup", function() {
		var isMobile = /^\d{11}$/.test($(this).val())
		$("#sendVCodeBtn").prop("disabled", !isMobile);
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
		// ajax
		return false;
	});
})