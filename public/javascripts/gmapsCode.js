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

function requestNextLocation() {
    var route = gmapsJsRoutes.controllers.GmapsController.nextLocation();

        // make async call
        $.ajax({
            url: route.url,
            success: function (result) {
                setIsCompleted(result);
                if (!isCompleted) {
                    addSingleSnappedCoordinate(result);
                }
            },
            async: false
        });

        //TODO there must be a better way... for sure :-/
        if(isCompleted) {
            completeTheDrive();
        }
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
