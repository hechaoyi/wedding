$(function() {
	$(".table-view-cell input").on("change", function() {
		$("#submitBtn").prop("disabled", false);
	});
	$("#submitBtn").on("click", function() {
		var category;
		$(".table-view-cell input").each(function() {
			if($(this).prop("checked"))
				category = $(this).val();
		});
		var name = $("#nameTxt").val();
		if(!category || !name)
			return;
		$.post("/saveName", {
			category: category,
			name: name
		}, function(data) {
			if(!data || data.code != 0) {
				alert(data ? data.msg : "服务器出错啦~请稍后再试");
			} else {
				window.location.href = data.data;
			}
		});
	});
})