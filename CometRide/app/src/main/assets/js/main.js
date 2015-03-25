height = $(window).height();
$('#map-container').css({
    'height': height + 'px'
});
latitude = 32.98565;
longitude = -96.74940;
capacity = 0;
var shuttle = new google.maps.LatLng(latitude, longitude);
var stop1Loc = new google.maps.LatLng(32.98565047, -96.74964756);
var stop2Loc = new google.maps.LatLng(32.98567297, -96.75087601);
var stop3Loc = new google.maps.LatLng(32.98566847, -96.75335974);
var stop4Loc = new google.maps.LatLng(32.98694188, -96.75374597);
var image = 'img/car.png';
var image_stop = 'img/dot.png';
var marker = new google.maps.Marker({
    position: shuttle,
    icon: image
});

var stop1 = new google.maps.Marker({
    position: stop1Loc,
    icon: image_stop
});

var stop2 = new google.maps.Marker({
    position: stop2Loc,
    icon: image_stop
});

var stop3 = new google.maps.Marker({
    position: stop3Loc,
    icon: image_stop
});

var stop4 = new google.maps.Marker({
    position: stop4Loc,
    icon: image_stop
});

function initialize() {
    var mapProp = {
        center: shuttle,
        zoom: 18,
        draggable: true,
        scrollwheel: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };



    var map = new google.maps.Map(document.getElementById("map-container"), mapProp);
    marker.setMap(map);
    stop1.setMap(map);
    stop2.setMap(map);
    stop3.setMap(map);
    stop4.setMap(map);

    var line = new google.maps.Polyline({
        path: [new google.maps.LatLng(32.98565047, -96.74940616), new google.maps.LatLng(32.98567297, -96.7537567), new google.maps.LatLng(32.98835926, -96.7537567)],
        strokeColor: "#FF0000",
        strokeOpacity: 0.3,
        strokeWeight: 5,
        map: map
    });


    var infoWindow = new google.maps.InfoWindow({
        content: '<div>Capacity: ' + capacity + '/8</div>'
    });
    infoWindow.open(map, marker);
};
initialize();