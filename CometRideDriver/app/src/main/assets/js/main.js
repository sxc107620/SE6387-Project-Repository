var clock;
var capacity = 0;
var shuttleType = 5;
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

var query = getQueryParams(document.location.search);
shuttleType = query.type;

height = $(window).height() - $('#map').position().top;
$('#map').height(height);


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

function initRoute(lines, curves, color, lat, lng) {
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
	}
	center = new google.maps.LatLng(lat, lng);
	map.setCenter(center);
}

function updateCab(lat, lng) {
	center = new google.maps.LatLng(lat, lng);
	map.setCenter(center);
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
			$("#count").hide();
			clock.reset();
			capacity = 0;
			statusUpdate(0);
		} else {
			$("#count").show();
			statusUpdate(1);
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


 
