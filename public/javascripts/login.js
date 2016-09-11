$(document).ready(function () {
    $("#login").click(function() {
        user = $("#lg_username").val();
        $.ajax({
            method: "PUT",
            url: "/push/send/login/" + user,
            data: { }
        })
        .done(function( msg ) {
            if (typeof msg.idRequest != 'undefined') {
                console.log("Sending login request successful. Received request id " + msg.idRequest);
                setInterval(function() {
                    $.get("/push/response/" + msg.idRequest, function(data) {
                        if (typeof data.action != "undefined") {
                            if (data.action.type == "AuthenticationResponseAction" && data.action.success == true) {
                                window.location.replace("/truck/1/simulation");
                            }
                        }
                    });
                }, 1000);
            }
        });
    });
});