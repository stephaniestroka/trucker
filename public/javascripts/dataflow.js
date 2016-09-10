$(document).ready(function() {

    i = 0;
    
    // TODO: push types
    types = ["driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man", "driver", "driver", "man", "public", "employer", "public", "man", "man"];
    function startSimulation() {
        handle = window.setInterval(function() {
            if (types.length != 0) {
                addDataPkg(i, types.shift());
                i = i + 1;
            }
            
        }, 2000);
    }
    
    function addDataPkg(number, type) {
        datapkg = document.createElement('div');
        cssClass = "";
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
            console.log("Sending data pkg " + number + " to the " + cssClass);
            $(datapkg).addClass(cssClass);
            // TODO: Because I'm a JS noob, the following code is commented out.
//            window.setTimeout(function() {
//                console.log("Removing data pkg " + number);
//                $(datapkg).remove();
//            }, 3000);
        }, 1000);
        
    }
    
});