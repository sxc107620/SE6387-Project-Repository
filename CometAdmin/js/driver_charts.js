/* --------------------------------------------------------
 Flot Charts
 -----------------------------------------------------------*/
//Driver Bar Chart
$(function () {
	$('#pickerDriver').datetimepicker({
		pickTime: false,
		viewMode: 'years',
		minViewMode: 'years',
		format: "yyyy"
	});
	
	var d = new Date();
	$('#queryDateDriver').val(d.getFullYear());
	driverHoursFn();
	
	$('#pickerDriver').on('changeDate', function(e) {
		driverHoursFn();
	});
	
	function driverHoursFn() {
		var dateRaw = $("#pickerDriver").data("datetimepicker").getDate();
		var dateObject = new Date(dateRaw);
		var dateFormatted = dateObject.toISOString().substring(0, 4);
		var userName = $('#driver-chart').data("uname");
					
		$.post("chartDataDriverPersonal.php",{ y: dateFormatted, u: userName }, function(data){
			data = JSON.parse(data);
			
			$.plot('#driver-chart', data, {
				 series: {
					pie: {
						show: true,
						radius: 1
					}
				},
				grid: {
					hoverable: true
				},
				tooltip: true,
				tooltipOpts: {
					content: "%y.2 hrs in %s", // show percentages, rounding to 2 decimal places
					shifts: {
					  x: 20,
					  y: 0
					},
					defaultTheme: true
				}
			});
		});
	}
});

