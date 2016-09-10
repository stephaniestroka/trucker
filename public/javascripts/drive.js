var Drive = {};
$(document).ready(function() {

    /************************** VARIABLES ****************************/
    var START_VELOCITY = 10;
    var driveIntervalId;
    var velocity = START_VELOCITY;

    /************************** CLICK ACTIONS ****************************/

    $("#btn_startDrive").click(function(){
        Drive.startDrive();
        Drive.updateSpeedometer();
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
    }

    Drive.startDrive = function() {
        driveIntervalId = setInterval(function(){
                Gmaps.requestNextLocation();
                Gmaps.drawSnappedPolyline();
        }, 1000/velocity);

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




