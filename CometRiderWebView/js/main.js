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
var routeid;
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
		var bounds = new google.maps.LatLngBounds();
		var points = curvePath.getPath().getArray();
		for (var n = 0; n < points.length ; n++){
			bounds.extend(points[n]);
		}
		map.fitBounds(bounds);
	}
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
				for(var count in shuttleLocations) {
					if(shuttleLocations[count].Route == routeid) {
					var shuttle = new google.maps.LatLng(shuttleLocations[count].Latitude, shuttleLocations[count].Longitude);
					cap = shuttleLocations[count].Capacity+"/"+shuttleLocations[count].Type;
					shuttleMarkers[count] = new google.maps.Marker({
						position: shuttle,
						icon: image,
						title: cap
					});
					shuttleMarkers[count].setMap(map);
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
	


