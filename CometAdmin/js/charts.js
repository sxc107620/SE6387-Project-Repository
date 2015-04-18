/* --------------------------------------------------------
 Flot Charts
 -----------------------------------------------------------*/
//Driver Bar Chart
$(function () {
	$('#pickerDriver').datetimepicker({
		pickTime: false,
		viewMode: 'months',
		minViewMode: 'months',
		format: "MMM"
	});
	
	var d = new Date();
	var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	$('#queryDateDriver').val(monthNames[d.getMonth()]);
	driverHoursFn();
	
	$('#pickerDriver').on('changeDate', function(e) {
		driverHoursFn();
	});
	
	function driverHoursFn() {
		var dateRaw = $("#pickerDriver").data("datetimepicker").getDate();
		var dateObject = new Date(dateRaw);
		var dateFormatted = dateObject.toISOString().substring(5, 7);
		
		$.post("chartDataDriver.php",{ m: dateFormatted }, function(data){
			data = JSON.parse(data);
			var barData = new Array();

			barData.push({
					data : data,
					label: ' has driven for ',
					bars : {
							show : true,
							barWidth : 0.1,
							fill:1,
							lineWidth: 0,
							fillColor: 'rgba(255,255,255,0.8)',
							align: "center"
					}
			});

			//Display graph
			$.plot($("#driver-chart"), barData, {
					
					grid : {
							borderWidth: 1,
							borderColor: 'rgba(255,255,255,0.25)',
							show : true,
							hoverable : true,
							clickable : true,       
					},
					
					yaxis: {
						tickColor: 'rgba(255,255,255,0.15)',
						tickDecimals: 0,
						font :{
							lineHeight: 13,
							style: "normal",
							color: "rgba(255,255,255,0.8)",
						},
						shadowSize: 0,
					},
					
					xaxis: {
						tickColor: 'rgba(255,255,255,0)',
						mode: "categories",
						font :{
							lineHeight: 13,
							style: "normal",
							color: "rgba(255,255,255,0.8)",
						},
						shadowSize: 0,
					},
					
					legend : true,
					tooltip : true,
					tooltipOpts : {
							content : "<b>%x</b> = <span>%y</span>",
							defaultTheme : false
					}

			});
			
			$("#driver-chart").bind("plothover", function (event, pos, item) {
				if (item) {
					var x = item.series.data[item.dataIndex][0],
						y = item.datapoint[1].toFixed(2);
					$("#driverchart-tooltip").html(x + item.series.label + y + " hours").css({top: item.pageY+5, left: item.pageX-200}).fadeIn(200);
				}
				else {
					$("#driverchart-tooltip").hide();
				}
			});

			$("<div id='driverchart-tooltip' class='chart-tooltip'></div>").appendTo("body");
		});
	}
	
	
	
	$('#statTabs a[href="#shuttle"]').click(function (e) {
	e.preventDefault();
	if ($("#shuttle-chart")[0]) {
		var data = new Array();
		var names = $("#shuttle-chart").data('names').split(';');
		var capacity = $("#shuttle-chart").data('cap').split(';');
		
		for(i=0; i<names.length; i++) data.push(new Array(names[i], parseInt(capacity[i])));
		
        var barData = new Array();

        barData.push({
                data : data,
                label: ' passengers have driven shuttle ',
                bars : {
                        show : true,
                        barWidth : 0.1,
                        fill:1,
                        lineWidth: 0,
                        fillColor: 'rgba(255,255,255,0.8)',
						align: "center"
                }
        });

        //Display graph
        $.plot($("#shuttle-chart"), barData, {
                
                grid : {
                        borderWidth: 1,
                        borderColor: 'rgba(255,255,255,0.25)',
                        show : true,
                        hoverable : true,
                        clickable : true,       
                },
                
                yaxis: {
                    tickColor: 'rgba(255,255,255,0.15)',
                    tickDecimals: 0,
                    font :{
                        lineHeight: 13,
                        style: "normal",
                        color: "rgba(255,255,255,0.8)",
                    },
                    shadowSize: 0,
                },
                
                xaxis: {
                    tickColor: 'rgba(255,255,255,0)',
					mode: "categories",
                    font :{
						lineHeight: 13,
                        style: "normal",
                        color: "rgba(255,255,255,0.8)",
                    },
                    shadowSize: 0,
                },
                
                legend : true,
                tooltip : true,
                tooltipOpts : {
                        content : "<b>%x</b> = <span>%y</span>",
                        defaultTheme : false
                }

        });
        
        $("#shuttle-chart").bind("plothover", function (event, pos, item) {
            if (item) {
                var x = item.series.data[item.dataIndex][0],
                    y = item.datapoint[1].toFixed(2);
                $("#shuttlechart-tooltip").html(parseInt(y) + item.series.label + x ).css({top: item.pageY+5, left: item.pageX-225}).fadeIn(200);
            }
            else {
                $("#shuttlechart-tooltip").hide();
            }
        });

        $("<div id='shuttlechart-tooltip' class='chart-tooltip'></div>").appendTo("body");

    }
	});
	
	$('#statTabs a[href="#route"]').click(function (e) {
		e.preventDefault();

		//Datetime picker
		var dateNow = new Date();
		$('#picker').datetimepicker({
			pickTime: false,
			defaultDate:dateNow
		});
		
		$('#picker').on('changeDate', function(e) {
			if($("#routeSel").val() != 'Select route') updateChart();
		});

		
		var currentTime = new Date();
		var month = ("0" + (currentTime.getMonth()+1)).slice(-2);
		var day = ("0" + currentTime.getDate()).slice(-2);
		var year = currentTime.getFullYear();
		$('#queryDate').val(month + "/" + day + "/" + year);
		
		$( "#routeSel" ).change(function() {
			updateChart();
		});
		
		function updateChart() {
			var dateRaw = $("#picker").data("datetimepicker").getDate();
			var dateObject = new Date(dateRaw);
			var dateFormatted = dateObject.toISOString();
			var from = dateFormatted.substring(0, 10) + " 00:00:00";
			var to = dateFormatted.substring(0, 10) + " 23:59:59";
			$.post("chartData.php",{ route: $( "#routeSel" ).val(), f: from, t: to }, function(data){
				data = JSON.parse(data);
				var barData = new Array();

				barData.push({
						data : data,
						label: ' passengers have driven the shuttle at this hour',
						bars : {
								show : true,
								barWidth : 0.1,
								fill:1,
								lineWidth: 0,
								fillColor: 'rgba(255,255,255,0.8)',
								align: "center"
						}
				});

				//Display graph
				$.plot($("#route-chart"), barData, {
						
						grid : {
								borderWidth: 1,
								borderColor: 'rgba(255,255,255,0.25)',
								show : true,
								hoverable : true,
								clickable : true,       
						},
						
						yaxis: {
							tickColor: 'rgba(255,255,255,0.15)',
							tickDecimals: 0,
							font :{
								lineHeight: 13,
								style: "normal",
								color: "rgba(255,255,255,0.8)",
							},
							shadowSize: 0,
						},
						
						xaxis: {
							tickColor: 'rgba(255,255,255,0)',
							min: 7,
							max: 23,
							tickSize: 1,
							font :{
								lineHeight: 13,
								style: "normal",
								color: "rgba(255,255,255,0.8)",
							},
							shadowSize: 0,
						},
						
						legend : true,
						tooltip : true,
						tooltipOpts : {
								content : "<b>%x</b> = <span>%y</span>",
								defaultTheme : false
						}

				});
				
				$("#route-chart").bind("plothover", function (event, pos, item) {
					if (item) {
						var x = item.series.data[item.dataIndex][0],
							y = item.datapoint[1].toFixed(2);
						$("#routechart-tooltip").html(parseInt(y) + item.series.label ).css({top: item.pageY+5, left: item.pageX+5}).fadeIn(200);
					}
					else {
						$("#routechart-tooltip").hide();
					}
				});

				$("<div id='routechart-tooltip' class='chart-tooltip'></div>").appendTo("body");

			});
		}
	});


});

