var Drive = {};
$(document).ready(function() {

    /************************** VARIABLES ****************************/
    var START_VELOCITY = 10;
    var driveIntervalId;
    var getDataIntervalId;
    var velocity = START_VELOCITY;

    /************************** CLICK ACTIONS ****************************/

    $("#btn_startDrive").click(function(){
        value = $("#btn_startDrive").text();
        if (value == "Start") {
            Drive.startDrive();
            Drive.updateSpeedometer();
            $("#btn_brake").removeClass("disabled");
            $("#btn_accelerate").removeClass("disabled");
            $("#btn_sleep").removeClass("disabled");
            $("#btn_startDrive").html("Stop");
        } else {
            Drive.stopDrive();
            $("#btn_startDrive").html("Start");
            $("#btn_brake").addClass("disabled");
            $("#btn_accelerate").addClass("disabled");
            $("#btn_sleep").addClass("disabled");
            velocity = 0;
            Drive.updateSpeedometer();
        }
    });
    
    $("#btn_brake").click(function(){
        Drive.brake();
        Drive.updateSpeedometer();
    });

    $("#btn_accelerate").click(function(){
        Drive.accelarate();
        Drive.updateSpeedometer();
    });

    /************************** FUNCTIONS ****************************/

    Drive.updateSpeedometer = function() {
        $("#speedometer-value").html(velocity + " km/h");
        $.ajax({
            method: "PUT",
            url: "/push/send/speed/johnny@digitalid.net",
            data: { }
        })
        .done(function( msg ) {
            console.log("Updating driver of the speed.")
        });
    }

    Drive.startDrive = function() {
    
        getDataIntervalId = setInterval(function(){
                Gmaps.requestNext100Locations();
        }, 3000);
        driveIntervalId = setInterval(function(){
                Gmaps.drawSnappedPolyline();
        }, 5000/velocity);

        DataFlow.startDataFlow();
    }

    Drive.accelarate = function() {
        velocity = velocity + 5;
        Drive.refreshInterval();
        console.log("Acceletate to velocity: " + velocity);
    }

    Drive.brake = function() {
        if(velocity != 0) {
            velocity = velocity - 5;
        }

        if(velocity == 0) {
            Drive.stopDrive();
            console.log("Stopped");
        } else {
            Drive.refreshInterval();
            console.log("Decelerate to velocity: " + velocity);
        }
    }

     Drive.stopDrive = function() {
        clearInterval(driveIntervalId);
        clearInterval(getDataIntervalId);
    }

    Drive.refreshInterval = function() {
        Drive.stopDrive();
        Drive.startDrive();
    }

    Drive.completeTheDrive = function() {
        Drive.stopDrive();
        DataFlow.stopDataFlow();
        velocity = 0;
        Drive.updateSpeedometer();
    }
});




