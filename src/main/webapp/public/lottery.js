$(function() {
	$.noty.defaults.layout = "topRight";
	$.noty.defaults.timeout = 2000;
	$.noty.defaults.maxVisible = 10;
	$.easing.inout = function(p) {
		if(p < 0.25 || p > 0.75)
			return 0.5 - Math.cos(4 * p * Math.PI ) / 2;
		return 1;
	}
	var socket = new SockJS("/ws");
	var stomp = Stomp.over(socket);
	stomp.connect({}, function() {
		stomp.subscribe("/topic/chat", function(data) {
			var chat = JSON.parse(data.body);
			noty({
				layout: "topLeft",
				type: "alert",
				text: chat.name+"说：<br/>"+chat.msg
			});
		});
		stomp.subscribe("/queue/event", function(data) {
			var event = JSON.parse(data.body);
			switch(event.type) {
			case "ACCESS":
				noty({
					type: "alert",
					text: "在【"+event.detail.source+"】有来宾通过二维码扫描加入互动"
				});
				break;
			case "MOBILE":
				noty({
					type: "information",
					text: "手机号为【"+event.detail.mobile+"】的来宾已经完成了帐号绑定"
				});
				break;
			case "REGISTER":
				noty({
					type: "success",
					text: "恭喜【"+event.detail.name+"】已经完成了注册！"
				});
				break;
			case "BLESS":
				noty({
					layout: "top",
					type: "error",
					maxVisible: 2,
					text: "【"+event.detail.name+"】送出了祝福："+event.detail.bless
				});
				break;
			}
		});
	}, function() {
		alert("与服务器的连接断开了，如需重连请刷新");
	});
	$(".bar-nav button.pull-left").on("click", function() {
		window.location.href = "/admin/dashboard";
	});
	var timer1 = null, timer2 = null;
	$("#lotteryBtn").on("click", function() {
		var start = $(this).is(".btn-positive");
		$(this).toggleClass("btn-positive btn-negative").text(start ? "停止" : "开始");
		if(start) {
			$("#panel").empty();
			$.post("/admin/roll", { count: 20 }, function(data) {
				if(!data || data.code != 0) {
					alert(data ? data.msg : "服务器出错啦~请稍后再试");
					return;
				}
				startLottery(data.data);
			});
			timer1 = setInterval(function() {
				$.post("/admin/roll", { count: 5 }, function(data) {
					if(!data || data.code != 0) {
						alert(data ? data.msg : "服务器出错啦~请稍后再试");
						return;
					}
					startLottery(data.data);
				});
			}, 500);
			timer2 = setInterval(function() {
				var width = $("#panel").width(), height = $("#panel").height();
				var candidate = $(".card").filter(function() {
					var top = $(this).position().top, left = $(this).position().left;
					return top > 200 && top < height-200 && left > 200 && left < width-200; 
				});
				var president = candidate.eq(Math.floor(Math.random()*candidate.size()))
					.addClass("king");
				$(".card").not(president).removeClass("king");
			}, 200);
		} else {
			if(timer1) {
				clearInterval(timer1);
				timer1 = null;
			}
			if(timer2) {
				clearInterval(timer2);
				timer2 = null;
			}
			$(".card:animated").stop(true);
			var panel = $("#panel");
			var king = $(".card.king").css({ zIndex: 10000 });
			king.transition({ rotate: "20deg" }, 300)
				.transition({ rotate: "-20deg" }, 300)
				.transition({
					rotate: "0deg",
					x: $(panel).width()/2-king.position().left-king.width()/2,
					y: $(panel).height()/2-king.position().top-king.height()/2
				}, 1200)
				.transition({ scale: 3 }, 1200);
			$("<div></div>").addClass("overlay").css("opacity", 0).appendTo("body")
				.animate({ opacity: 0.8 }, 3000).on("click", function() {
					$(".card").add(this).fadeOut(1000, function() {
						$(this).remove();
					});
				});
		}
	});

	function startLottery(data) {
		if(!timer1 || !timer2)
			return;
		var panel = $("#panel");
		$.each(data, function() {
			var src, dst;
			switch(Math.floor(Math.random()*4)) {
			case 0:
				src = { top: 0, left: Math.floor(Math.random()*panel.width()) };
				dst = { top: panel.height(), left: Math.floor(Math.random()*panel.width()) }
				break;
			case 1:
				src = { top: Math.floor(Math.random()*panel.height()), left: panel.width() };
				dst = { top: Math.floor(Math.random()*panel.height()), left: 0 }
				break;
			case 2:
				src = { top: panel.height(), left: Math.floor(Math.random()*panel.width()) };
				dst = { top: 0, left: Math.floor(Math.random()*panel.width()) }
				break;
			case 3:
				src = { top: Math.floor(Math.random()*panel.height()), left: 0 }
				dst = { top: Math.floor(Math.random()*panel.height()), left: panel.width() };
				break;
			}
			src.opacity = 0;
			dst.opacity = [1, "inout"];
			var duration = Math.floor(Math.random()*500*this.weight+2000);
			$("<div></div>").css(src)
				.addClass("card color" + Math.floor(Math.random()*7+1))
				.append($("<h4></h4>").text(this.displayName)
					.append($("<small></small>").text("["+this.mobile+"]")))
				.append($("<p></p>").text(this.bless)).appendTo(panel)
				.animate(dst, duration, function() {
					$(this).remove();
				});
		});
	}
})