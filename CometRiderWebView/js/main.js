/* --------------------------------------------------------
	Custom Scrollbar
    -----------------------------------------------------------*/
(function() {
if($('.overflow')[0]) {
	var overflowRegular, overflowInvisible = false;
	overflowRegular = $('.overflow').niceScroll();
}
})();

/* --------------------------------------------------------
	Slide Menu
    -----------------------------------------------------------*/
$(document).ready(function() {   
	var sideslider = $('[data-toggle=collapse-side]');
	var sel = sideslider.attr('data-target');
	var sel2 = sideslider.attr('data-target-2');
	sideslider.click(function(event){
		$(sel).toggleClass('in');
		$(sel2).toggleClass('out');
	});
	
	$('.nav-list').on('click', 'li', function() {
		$('.nav-list li.active').removeClass('active');
		$(this).addClass('active');
		$(sel).toggleClass('in');
		$(sel2).toggleClass('out');
	});
});

	
	/* --------------------------------------------------------
	Google Map
    -----------------------------------------------------------*/
height = $(window).height();
height-=50;
$('#map-container').css({
    'height': height + 'px'
});

var map; 
overlay = [];
var points = new google.maps.MVCArray;
var routeid;
var GeoMarker;
var interestLocation = [];
function initialize() {
	var UTD = new google.maps.LatLng(32.98594891, -96.7509511);
    var mapProp = {
		center: UTD,
        zoom: 17,
        draggable: true,
        scrollwheel: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
	map = new google.maps.Map(document.getElementById("map-container"), mapProp);
	
	/*Geomarker hits the performance*/
	//GeoMarker = new GeolocationMarker();
	//GeoMarker.setCircleOptions({fillOpacity: 0, strokeWeight: 0});
	//GeoMarker.setMap(map);
	
	lines = $('.nav-list li a').first().data('lines');
	curves = $('.nav-list li a').first().data('curves');
	savepoints = $('.nav-list li a').first().data('savepoints');
	color = $('.nav-list li a').first().data('color');
	routeid = $('.nav-list li a').first().data('rid');
	plotter(lines, curves, savepoints, color);
	tracker();	
};
initialize();

function plotter(lines, curves, savepoints, color) {
	var slines = lines.split('+');
	if (savepoints.length != 0) {
		points = [];
		points = google.maps.geometry.encoding.decodePath(savepoints);
		for(j=0; j<points.length; j++) {
			var marker = new google.maps.Marker({
				position: points[j],
				map: map,
				animation: google.maps.Animation.DROP, //bounce animation
				icon: "img/marker.png" //custom pin icon
			});
			overlay.push(marker);
		}
	var bounds = new google.maps.LatLngBounds();
	for (var n = 0; n < points.length ; n++){
		bounds.extend(points[n]);
	}
	map.fitBounds(bounds);
	}
	
	if (slines.length != 0) {
		$.each(slines, function( index, value ) {
			if(value.length != 0) {
				var linePath = new google.maps.Polyline({
					path: google.maps.geometry.encoding.decodePath(value),
					geodesic: true,
					strokeColor: color,
					strokeOpacity: 1.0,
					strokeWeight: 2,
					map: map
				});
				overlay.push(linePath);
			}
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
		overlay.push(curvePath);
	}
	
	//Interest Points
	if(interestLocation != 0) {
		if(typeof(interestLocation[routeid]) != 'undefined')
			interestLocation[routeid].setMap(map);
	}
}

var shuttle = [];
var prevLt = [];
var prevLn = [];
var infowindow = [];
function tracker() {
		var image = 'img/car.png';
		if(typeof(EventSource) !== "undefined") {
			var source = new EventSource("./php/includes/sse.php");
			source.onmessage = function(event) {
				shuttleLocations = jQuery.parseJSON(event.data);
				for(var count in shuttleLocations) {
					if(shuttleLocations[count].Route == routeid) {
						if((prevLt[count] != shuttleLocations[count].Latitude) && (prevLn[count] != shuttleLocations[count].Longitude)) {
							if(typeof(shuttle[count]) != 'undefined') shuttle[count].setMap(null);
							var shuttlePos = new google.maps.LatLng(shuttleLocations[count].Latitude, shuttleLocations[count].Longitude);
							cap = shuttleLocations[count].Capacity+"/"+shuttleLocations[count].Type;
							var options = {
								position: shuttlePos,
								disableAutoPan: true
							};
							infowindow[count] = new google.maps.InfoWindow(options);
							var contentString = 
								'<div id="infowindow">' +
								'Capacity:    ' + cap +
								'</div>';
							shuttle[count] = new google.maps.Marker({
								icon: image,
								zIndex:  google.maps.Marker.MAX_ZINDEX + 1 
							});
							shuttle[count].setPosition(shuttlePos);
							shuttle[count].setMap(map);
							
							infowindow[count].setContent(contentString);
							infowindow[count].open(map, shuttle[count]);
							google.maps.event.addListener(infowindow[count], 'domready', function(){
								$(".gm-style-iw").next("div").hide();
							});
							
							prevLt[count] = shuttleLocations[count].Latitude;
							prevLn[count] = shuttleLocations[count].Longitude;
						} 
					}
				}
			};
		} 
}

$('.nav-list').on('click', '.routeChange', function() {
	routeid = $(this).data('rid');
	lines = $(this).data('lines');
	curves = $(this).data('curves');
	savepoints = $(this).data('savepoints');
	color = $(this).data('color');
	while(overlay.length != 0) overlay.pop().setMap(null);
	$.each(shuttle, function(index, value) {
		if(typeof(value) != 'undefined') value.setMap(null);
	});
	$.each(infowindow, function(index, value) {
		if(typeof(value) != 'undefined') value.close();
	});
	plotter(lines, curves, savepoints, color);
	tracker();	
});

function closest(llng, listData) {
    var arr     = listData;
    var pnt     = llng;
    var distArr = [];
    var dist    = google.maps.geometry.spherical.computeDistanceBetween;


    for (index in arr)
        distArr.push([arr[index], dist(pnt, arr[index])]);

    return distArr.sort(function(a,b){
        return a[1]-b[1];
    })[0];
}


function drawRoute(src, des) {
	var service = new google.maps.DirectionsService();
	var curvesArray = new google.maps.MVCArray;
	var lineSymbol = {
	  path: 'M 0,-1 0,1',
	  strokeOpacity: 1,
	  scale: 4
	};
	var poly = new google.maps.Polyline({
		map: map,
		strokeOpacity: 0,
		icons: [{
			icon: lineSymbol,
			offset: '0',
			repeat: '20px'
		  }],
	});
	map.setCenter(src);
	overlay.push(poly);
	service.route({
		origin: src,
		destination: des,
		travelMode: google.maps.DirectionsTravelMode.WALKING
	}, function (result, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
				curvesArray.push(result.routes[0].overview_path[i]);
			}
			poly.setPath(curvesArray);
		}
	});
	$("#failure").modal('show');
}


function bookCab(id, lat, lng) {
	var id = routeid;
	$.post("index.php",{ i: id, l: lat, lg: lng }, function(){
		$("#success").modal('show');
		var image = {
			url: 'img/dot.png',
			size: new google.maps.Size(18, 16),
			origin: new google.maps.Point(0,0),
			anchor: new google.maps.Point(0, 16)
		  };
		interestLocation[routeid] = new google.maps.Marker({
			position: new google.maps.LatLng(lat, lng),
			map: map,
			icon: image //custom pin icon
		});
		overlay.push(interestLocation[id]);
	});
}

$('.interest').click(function() {
	navigator.geolocation.getCurrentPosition(function(position) {
		currentPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		closestPt = closest(currentPos, points);
		console.log();
		if(closestPt[1] > 300)
			drawRoute(currentPos, closestPt[0]);
		else
			bookCab(routeid, closestPt[0].k, closestPt[0].D);
	});
	
	
});