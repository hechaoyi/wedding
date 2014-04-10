$(function() {
	var check = function() {
		var count = 0;
		$(".table-view-cell input").each(function() {
			if($(this).prop("checked"))
				count++;
		});
		$("#submitBtn").prop("disabled", count == 0 || !$("#nameTxt").val());
	};
	$(".table-view-cell input").on("change", check);
	$("#nameTxt").on("keyup", check);
	setInterval(check, 1000);
	$("#submitBtn").on("click", function() {
		var category;
		$(".table-view-cell input").each(function() {
			if($(this).prop("checked"))
				category = $(this).val();
		});
		$.post("/saveName", {
			category: category,
			name: $("#nameTxt").val()
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			} else {
				window.location.href = data.data;
			}
		});
	});
})