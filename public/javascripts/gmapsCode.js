var Gmaps = {};

$( document ).ready(function(){

     /************************** VARIABLES ****************************/
    var polylines = [];
    var snappedCoordinates = [];
    var isCompleted = false;

    /************************** FUNCTIONS ****************************/

        Gmaps.addSingleSnappedCoordinate = function(location) {
            var latlng = new google.maps.LatLng(
                location.latitude,
                location.longitude);
            snappedCoordinates.push(latlng);
        };

        Gmaps.requestNext100Locations = function() {
            var route = gmapsJsRoutes.controllers.GmapsController.nextLocations();

                // make async call
                $.ajax({
                    url: route.url,
                    success: function (result) {
                        Gmaps.setIsCompleted(result);
                        if (!isCompleted) {
                            if (typeof result.code == 'undefined') {
                                for (i = 0; i < result.length; i++) {
                                    Gmaps.addSingleSnappedCoordinate(result[i]);
                                }
                            }
                        }
                    },
                    async: false
                });

                //TODO there must be a better way... for sure :-/
                if(isCompleted) {
                    Drive.completeTheDrive();
                }
        }

        Gmaps.setIsCompleted = function(data) {
            if(typeof data.code != 'undefined' && snappedCoordinates.length == 0) {
                isCompleted = true;
                console.log("Travel is completed");
            } else {
                isCompleted = false;
            }
        }

        // Draws the snapped polyline (after processing snap-to-road response).
        Gmaps.drawSnappedPolyline = function() {
            
            if (snappedCoordinates.length > 0) {
                var partialCoordinates = [];
                for (i = 0; i < 10; i++) {
                    if (snappedCoordinates.length > 0) {
                        partialCoordinates.push(snappedCoordinates.shift());
                    }
                }
                var snappedPolyline = new google.maps.Polyline({
                    path: partialCoordinates,
                    strokeColor: 'black',
                    strokeWeight: 3
                });

                snappedPolyline.setMap(Gmaps.map);
                polylines.push(snappedPolyline);
            }
        }
});

    /************************** INIT STUFF ****************************/
    // functions that can run during page loading

    function initialize() {
      showGoogleMaps();
      initializeItinerary();
    }

    function showGoogleMaps() {
        var mapOptions = {
          zoom: 13,
          center: {lat: 48.267035, lng: 11.662849}
        };
        Gmaps.map = new google.maps.Map(document.getElementById('map'), mapOptions);
    }

    function initializeItinerary() {
        var route = gmapsJsRoutes.controllers.GmapsController.newItinerary();
        $.get(route.url, function(data){
            console.log(data["message"])
        })
    }

$(window).load(initialize);
