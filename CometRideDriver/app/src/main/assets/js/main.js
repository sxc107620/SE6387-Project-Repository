var clock;
var capacity = 0;
var shuttleType = 5;

//Login Page
function loginPage() {
	$('.cab-stat').hide();
	$('#counterPage').hide();
	$('#loginPage').show();
	$('#alertMessage').hide();
}

function counterPage() {
	$('.cab-stat').show();
	$('#counterPage').show();
	google.maps.event.trigger(map, 'resize');
	$('#loginPage').hide();
}

function initRoutes(data) {
	/*var json = [];
	json.push("Route 1", "Route 2", "Route 3");
	data = JSON.stringify(json);
	console.log(data)//["Route 1","Route 2","Route 3"];*/
	data = JSON.parse(data);
	for(i=0; i<data.length; i++) $("#login-route").append(new Option(data[i], i));
}

function initCabs(data) {
	/*var json = [];
	json.push(new Array("101", "7"), new Array("201", "9"), new Array("301", "7"));
	data = JSON.stringify(json);
	console.log(data)//[["101","7"],["201","9"],["301","7"]];*/
	data = JSON.parse(data);
	for(i=0; i<data.length; i++) $("#login-cab").append(new Option(data[i][0], data[i][1]));
}

function loginSuccess() {
	shuttleType = $("#login-cab").val();
	height = $(document).height() - $('#map').position().top;
	$('#map').height(height-140);
	counterPage();
	statusUpdate(1);
}

function loginFailure() {
	$('#alertMessage span').html("Your login credentials were incorrect");
	$('#alertMessage').show();
}

$('#login').click(function() {
	var error = '';
	uname = $('#login-username').val();
	pass = $('#login-password').val();
	route = $('#login-route').val();
	cab = $('#login-cab option:selected').html();
	if(uname == '') error += "<li>Username is empty</li>";
	if(pass == '') error += "<li>Password is empty</li>";
	if(route == 'Select route') error += "<li>Route not selected</li>";
	if(cab == 'Select cab') error += "<li>Cab not selected</li>";
	console.log(cab);
	if(error.length == '') {
		$('#alertMessage').hide();
		checkCredentials(uname, pass, route, cab);
	}
	else {
		$('#alertMessage span').html("<ul>" + error + "</ul>");
		$('#alertMessage').show();
	}
});

function getQueryParams(qs) {
    qs = qs.split("+").join(" ");

    var params = {}, tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])]
            = decodeURIComponent(tokens[2]);
    }

    return params;
}

/*var query = getQueryParams(document.location.search);
shuttleType = query.type;*/



marker = Array();


var UTD = new google.maps.LatLng(32.98594891, -96.7509511);
var mapOptions = {
	zoom: 18,
	center: UTD,
	panControl: true, //enable pan Control
	zoomControl: true, //enable zoom control
	scaleControl: true, // enable scale control
	mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
}

map = new google.maps.Map(document.getElementById('map'), mapOptions);

function initRoute(lines, curves, color) { //lines, curves, color
	//inc();
	if (lines.length != 0) {
		var linePath = new google.maps.Polyline({
			path: google.maps.geometry.encoding.decodePath(lines),
			geodesic: true,
			strokeColor: color,
			strokeOpacity: 1.0,
			strokeWeight: 2,
			map: map
		});
	}

	if (curves.length != 0) {
		var curvePath = new google.maps.Polyline({
			path: google.maps.geometry.encoding.decodePath(curves),
			geodesic: true,
			strokeColor: color,
			strokeOpacity: 1.0,
			strokeWeight: 2,
			map: map
		});
		var bounds = new google.maps.LatLngBounds();
		var points = curvePath.getPath().getArray();
		for (var n = 0; n < points.length ; n++){
			bounds.extend(points[n]);
		}
		map.fitBounds(bounds);
	}
	//map.setCenter(UTD);
}

var shuttleMarkers = '';
function updateCab(lat, lng) {
	center = new google.maps.LatLng(lat, lng);
	map.setCenter(center);
	if(shuttleMarkers != '') {
		shuttleMarkers.setMap(null);
	}
	shuttleMarkers = new google.maps.Marker({
		position: center,
		icon: "img/car.png",
		map: map
	});
}

function drawMarker(id, latPts, lngPts) {
	var ids = id.split(',');
	var lat = latPts.split(',');
	var lng = lngPts.split(',');
	for(i=0; i<ids.length; i++) {
		pt = new google.maps.LatLng(lat[i], lng[i]);
		marker[i] = new google.maps.Marker({
			position: pt,
			map: map,
			icon: "img/marker.png" //custom pin icon
		});
	}
}

//This is the function I really want
function deleteAllMarkers() {
	var num = marker.length;
	for(var i=0; i < num; i++) {
		marker[i].setMap(null);
	}
	marker.length = 0;
}

//Rendered obsolete by the above
function deleteMarker(id) {
//	var ids = id.split(',');
//	for(i=0; i<ids.length; i++) {
//		marker[ids[i]].setMap(null);
//	}
}

	// Instantiate a counter
	clock = new FlipClock($('.clock'), 00, {
		clockFace: 'Counter'
	});

	// Attach a click event to a button a increment the clock
	$('.increment').click(function() {
		inc();
	});

	// Attach a click event to a button a decrement the clock
	$('.decrement').click(function() {
		dec();
	});

	$('.reset').click(function() {
		res();
	});

	$('.btn-toggle').click(function() {
		$(this).find('.btn').toggleClass('active');

		if ($(this).find('.btn-warning').size() > 0) {
			$(this).find('.btn').toggleClass('btn-warning');
		}

		$(this).find('.btn').toggleClass('btn-default');

		if ($(this).find('.active').html() == "OFF-Duty") {
			clock.reset();
			capacity = 0;
			loginPage();
			statusUpdate(0);
		} 
	});


function inc() {
	if($('#count').css('display') != 'none') {
		if (capacity < shuttleType) {
			clock.increment();
			capacity++;
			capacityUpdate(capacity);
			passengerOn();
		}
	}
}

function dec() {
	if($('#count').css('display') != 'none') {
		if (capacity > 0) {
			clock.decrement();
			capacity--;
			capacityUpdate(capacity);
		}
	}
}

function res() {
	clock.reset();
	capacity = 0;
	capacityUpdate(capacity);
}

function screenOff() {
	$("#count").hide();
}

function screenOn() {
	$("#count").show();
}


function capacityUpdate(toast) {
  AndroidFunction.currentCapacity(toast);
}

function statusUpdate(toast) {
  AndroidFunction.currentStatus(toast);
}

function passengerOn() {
  AndroidFunction.incrementPressed();
}

function checkCredentials(username, password, route, cab) {
	AndroidFunction.credentials(username, password, route, cab);
}
 
