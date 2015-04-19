$(document).ready(function() {
	var runner;
	
	$("#run").click(function() {
		var cab = [];
		var status = [];
		var route = [];
		var runList = [];
		$(this).text("Running");
		
		$.each($('.cabList'), function() {
			cab.push($(this).val());
		});
		$.each($('.routeList'), function() {
			route.push($(this).val());
		});
		$.each($('.statusList'), function(index) {
			status.push($(this).val());
			if($(this).val() == 'on-duty') {
				runList.push(new Array(cab[index], route[index]));
			}
		});
		
		$.post("php/status.php",{ c: cab, s: status, r: route });
		
		runner = setInterval(function(){
			$.post("php/run.php",{ r: runList });
		}, 10);
		
	});
	
	$("#stop").click(function() {
		$("#run").text("Run");
		clearInterval(runner);
	});
});