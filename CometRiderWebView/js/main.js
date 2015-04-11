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
var groupPoints = new google.maps.MVCArray;
var routeid;
var GeoMarker;
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
	if (savepoints.length != 0) {
		points = [];
		groupPoints = new google.maps.MVCArray;
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
	}
	if (lines.length != 0) {
		var linePath = new google.maps.Polyline({
			path: google.maps.geometry.encoding.decodePath(lines),
			geodesic: true,
			strokeColor: color,
			strokeOpacity: 1.0,
			strokeWeight: 2,
			map: map
		});
		overlay.push(linePath);
		//groupPoints.push(linePath.getPath().getArray());
		groupPoints = linePath.getPath().getArray();
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
		//groupPoints.push(curvePath.getPath().getArray());
		if(groupPoints.length == 0) groupPoints = curvePath.getPath().getArray();
		else {
			$.each(curvePath.getPath().getArray(), function( index, value ) {
				groupPoints.push(value);
			});
		}
	}
	var bounds = new google.maps.LatLngBounds();
	for (var n = 0; n < groupPoints.length ; n++){
		bounds.extend(groupPoints[n]);
	}
	map.fitBounds(bounds);
}

var shuttleMarkers = [];
function tracker() {
		var image = 'img/car.png';
		if(typeof(EventSource) !== "undefined") {
			var source = new EventSource("./php/includes/sse.php");
			source.onmessage = function(event) {
				shuttleLocations = jQuery.parseJSON(event.data);
				if(shuttleMarkers.length != 0) {
					for(i=0; i<shuttleMarkers.length; i++)
						shuttleMarkers[i].setMap(null);
				}
				shuttleMarkers.length = 0;
				for(var count in shuttleLocations) {
					if(shuttleLocations[count].Route == routeid) {
					var shuttle = new google.maps.LatLng(shuttleLocations[count].Latitude, shuttleLocations[count].Longitude);
					cap = shuttleLocations[count].Capacity+"/"+shuttleLocations[count].Type;
					shuttle = new google.maps.Marker({
						position: shuttle,
						icon: image,
						title: cap
					});
					shuttle.setMap(map);
					shuttleMarkers.push(shuttle);
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
	  if(overlay.length != 0) while(overlay[0]) overlay.pop().setMap(null);
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
	$.post("index.php",{ i: id, l: lat, lg: lng }, function(){
		$("#success").modal('show');
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(lat, lng),
			map: map,
			icon: "img/dot.png" //custom pin icon
		});
	});
}

$('.interest').click(function() {
	navigator.geolocation.getCurrentPosition(function(position) {
		currentPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		closestPt = closest(currentPos, groupPoints);
		console.log();
		if(closestPt[1] > 300)
			drawRoute(currentPos, closestPt[0]);
		else
			bookCab(routeid, closestPt[0].k, closestPt[0].D);
	});
	
	
});