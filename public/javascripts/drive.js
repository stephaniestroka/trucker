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
            Drive.stopStartTruckSound()
            Drive.playDrivingSound();
        } else {
            Drive.stopDrive();
            $("#btn_startDrive").html("Start");
            $("#btn_brake").addClass("disabled");
            $("#btn_accelerate").addClass("disabled");
            $("#btn_sleep").addClass("disabled");
            velocity = 0;
            Drive.updateSpeedometer();
            Drive.stopDrivingSound();
        }
    });
    
    $("#btn_brake").click(function(){
        Drive.brake();
        Drive.updateSpeedometer();
        Drive.stopDrivingSound();
        Drive.playBrakeSound();
        window.setTimeout(function(){
                    Drive.playDrivingSound();
                },1000)
    });

    $("#btn_accelerate").click(function(){
        Drive.accelarate();
        Drive.updateSpeedometer();
        Drive.stopDrivingSound();
        Drive.playAccelerationSound();
        window.setTimeout(function(){
            Drive.playDrivingSound();
        },1500)
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

    /************************** SOUNDS ****************************/

    Drive.playStartTruckSound = function() {
        var audio = document.getElementById("audioStartTruck");
        audio.loop = true;
        audio.play();
    }

    Drive.stopStartTruckSound = function() {
        var audio = document.getElementById("audioStartTruck");
        audio.pause();
    }

    Drive.playDrivingSound = function() {
        var audio = document.getElementById("audioDriving");
        audio.loop = true;
        audio.play();
    }

    Drive.stopDrivingSound = function() {
        var audio = document.getElementById("audioDriving");
        audio.pause();
    }

    Drive.playAccelerationSound = function() {
        var audio = document.getElementById("audioAcceleration");
        audio.play();
    }

    Drive.playBrakeSound = function() {
        var audio = document.getElementById("audioBrake");
        audio.play();
    }



    /************************** RUNNING ****************************/
    Drive.playStartTruckSound();
});




