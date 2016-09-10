
$( document ).ready(function(){

    $("#btn_startDrive").click(function(){
        value = $("#btn_startDrive").text();
        if (value == "Start") {
            startDrive();
            updateSpeedometer();
            $("#btn_brake").removeClass("disabled");
            $("#btn_accelerate").removeClass("disabled");
            $("#btn_sleep").removeClass("disabled");
            $("#btn_startDrive").html("Stop");
        } else {
            stopDrive();
            $("#btn_startDrive").html("Start");
            $("#btn_brake").addClass("disabled");
            $("#btn_accelerate").addClass("disabled");
            $("#btn_sleep").addClass("disabled");
            velocity = 0;
            updateSpeedometer();
        }
    });

    $("#btn_sleep").click(function() {
        $.get("/push/send/sleep/johnny@digitalid.net", function(data) { console.log("Send sleep notification") });
    });
    
    $("#btn_brake").click(function(){
        brake();
        updateSpeedometer();
    });

    $("#btn_accelerate").click(function(){
        accelarate();
        updateSpeedometer();
    });

    function updateSpeedometer() {
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
});

var driveIntervalId;
var START_VELOCITY = 10;
var velocity = START_VELOCITY;

function startDrive() {
    driveIntervalId = setInterval(function(){
            requestNext100Locations();
            drawSnappedPolyline();
    }, 1000/velocity);
}

function accelarate() {
    velocity = velocity + 5;
    refreshInterval();
    console.log("Acceletate to velocity: " + velocity);
}

function brake() {
    if(velocity != 0) {
        velocity = velocity - 5;
    }

    if(velocity == 0) {
        stopDrive();
        console.log("Stopped");
    } else {
        refreshInterval();
        console.log("Decelerate to velocity: " + velocity);
    }
}

function stopDrive() {
    clearInterval(driveIntervalId);
}

function refreshInterval() {
    stopDrive();
    startDrive();
}

function completeTheDrive() {
    stopDrive();
    velocity = 0;
    updateSpeedometer();
}
