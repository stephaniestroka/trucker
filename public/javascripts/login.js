$(document).ready(function () {
    $("#login").click(function() {
        user = $("#lg_username").val();
        $.ajax({
            method: "PUT",
            url: "/push/send/login/" + user,
            data: { }
        })
        .done(function( msg ) {
            window.location.replace("/truck/1/simulation");
        });
    });
});