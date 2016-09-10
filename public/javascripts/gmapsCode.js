var apiKey = 'AIzaSyBoOklxjoiao1YpYuvzJu5EmDvMv3zvxeI';

var map;
var polylines = [];
var snappedCoordinates = [];
var isCompleted = false;

function initialize() {
  showGoogleMaps();
  initializeItinerary();
}

function showGoogleMaps() {
    var mapOptions = {
      zoom: 13,
      center: {lat: 48.267035, lng: 11.662849}
    };
    map = new google.maps.Map(document.getElementById('map'), mapOptions);
}

function initializeItinerary() {
    var route = gmapsJsRoutes.controllers.GmapsController.newItinerary();
    $.get(route.url, function(data){
        console.log(data["message"])
    })
}

function addSingleSnappedCoordinate(location) {
    var latlng = new google.maps.LatLng(
        location.latitude,
        location.longitude);
    snappedCoordinates.push(latlng);
}

function travel() {
    window.setInterval(function(){
            requestNextLocation();
            drawSnappedPolyline();
    }, 100);
}

function requestNextLocation() {
    var route = gmapsJsRoutes.controllers.GmapsController.nextLocation();

        // make async call
        $.ajax({
            url: route.url,
            success: function (result) {
                setIsCompleted(result);
                if (!isCompleted) {
                    addSingleSnappedCoordinate(result);
                    console.log(result.latitude + " " + result.longitude);
                }
            },
            async: false
        });
}

function setIsCompleted(data) {
    if(typeof data.code != 'undefined') {
        isCompleted = true;
        console.log("Travel is completed");
    } else {
        isCompleted = false;
    }
}


// Draws the snapped polyline (after processing snap-to-road response).
function drawSnappedPolyline() {
  var snappedPolyline = new google.maps.Polyline({
    path: snappedCoordinates,
    strokeColor: 'black',
    strokeWeight: 3
  });

  snappedPolyline.setMap(map);
  polylines.push(snappedPolyline);
}

$(window).load(initialize);
