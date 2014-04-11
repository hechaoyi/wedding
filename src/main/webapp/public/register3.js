$(function() {
	$("#submitBtn").on("click", function() {
		var bless = $("#blessTxt").val();
		if(!bless)
			return;
		$.post("/saveBless", {
			bless: bless
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			} else {
				window.location.href = data.data;
			}
		});
	});
})