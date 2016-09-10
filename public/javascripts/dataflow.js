var DataFlow = {};
$(document).ready(function() {

    /************************** VARIABLES ****************************/
    var packagesIntervalId;
    var isStarted = false;


    /************************** FUNCTIONS ****************************/

    DataFlow.startDataFlow = function () {
        // TODO: push types
        var i = 0;
        var types = ["driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man"];
        if (!isStarted) {
            packagesIntervalId = window.setInterval(function() {
                       if (types.length != 0) {
                           DataFlow.addDataPkg(i, types.shift());
                           i = i + 1;
                       }

                   }, 1000);
        }
        isStarted = true;

    }

     DataFlow.stopDataFlow = function() {
        clearInterval(packagesIntervalId);
        isStarted = false;
    }

    DataFlow.addDataPkg = function(number, type) {
        var datapkg = document.createElement('div');
        var cssClass = "";
        if (type == "driver") {
            cssClass = "lower-left-corner";
        } else if (type == "man") {
            cssClass = "upper-left-corner";
        } else if (type == "employer") {
            cssClass = "lower-right-corner";
        } else if (type == "public") {
            cssClass = "upper-right-corner";
        }
        $(datapkg).addClass("data-pkg").addClass(type).attr("id", "data-pkg-" + number);
        $("#data-flow").append(datapkg);
        window.setTimeout(function() {
            console.log("Sending data pkg " + number + " to the " + cssClass + " (" + $(datapkg).attr("id") + ")");
            $(datapkg).addClass(cssClass);
            // TODO: Because I'm a JS noob, the following code is commented out.
//            window.setTimeout(function() {
//                console.log("Removing data pkg " + number);
//                $(datapkg).remove();
//            }, 3000);
        }, 1000);

    }

});