$(function() {
	var check = function() {
		$("#submitBtn").prop("disabled", !$("#blessTxt").val());
	};
	$("#blessTxt").on("keyup", check);
	setInterval(check, 1000);
	$("#submitBtn").on("click", function() {
		$.post("/saveBless", {
			bless: $("#blessTxt").val()
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			} else {
				window.location.href = data.data;
			}
		});
	});
})