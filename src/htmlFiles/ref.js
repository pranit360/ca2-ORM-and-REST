$(document).ready(populate);

function populate()
{

    $.ajax({
        url: '/Person',
        type: 'GET',
        datatype: 'json',
        error: function (data, textStatus, jqXHR) {
            var jsonResponse = JSON.parse(data.responseText);
            alert(jsonResponse.error);
        }
    }).done(function (persons)
    {
        var options = "";
        persons.forEach(function (person) {
            options += "<option id=" + person.id + ">" + person.firstName + " " + person.lastName +
                    "</option>";
        });
        $("#persons").html(options);
    });

}

$(document).ready(function () {

    populate();
    deletePerson();
    initPersons();
    initAddBtn();
    initRoleBtn();
    initSRoleBtn();
    initCancelBtn();
    initSaveBtn();

});


function initAddBtn() {
    $("#btn_add").click(function () {
        initDetails(true);
        populate();
    });
}

function initRoleBtn() {
    $("#btn_role").click(function () {
        initRoleDetails(true);
        //populate();
    });
}

//function initSRoleBtn() {
//    $("#btn_srole").click(function () {
//        initSRoleDetails(true);
//        //populate();
//    });
//}

function initSaveBtn() {
    $("#btn_save").click(function () {
        //First create post argument as a JavaScript object
        var newPerson = {"firstName": $("#fname").val(), "lastName": $("#lname").val(), 
            "phone": $("#phone").val(),"email": $("#email").val()};
        $.ajax({
            url: "../Person",
            data: JSON.stringify(newPerson), //Convert newPerson to JSON
            type: "post",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText + ": " + textStatus);
            }
        }).done(function (newPerson) {
            $("#id").val(newPerson.id);
            initDetails(false);
            populate();
        });
    });
}

function initSRoleBtn() {
    $("#btn_srole").click(function () {
        //First create post argument as a JavaScript object
        if(document.getElementById("mySelect").value == "Teacher"){
        
        var newRole = {"employed": $("#date").val(), "degree": $("#degree").val(), 
            "roleName": "Teacher"};var idGotten = $("#id").val();
        $.ajax({
            url: "../Role"+"/"+idGotten,
            data: JSON.stringify(newRole), //Convert newPerson to JSON
            type: "post",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText + ": " + textStatus);
            }
        }).done(function () {
                    //$("#id").val(newPerson.id);
            initSRoleDetails(true);
        });
    }
    
    else if(document.getElementById("mySelect").value == "student"){
        
        var newRole = {"semester": $("#semester").val(),  "roleName": "Student"};
        var idGotten = $("#id").val();
        $.ajax({
            url: "../Role"+"/"+idGotten,
            data: JSON.stringify(newRole), //Convert newPerson to JSON
            type: "post",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText + ": " + textStatus);
            }
        }).done(function () {
                    //$("#id").val(newPerson.id);
            initSRoleDetails(true);
        });
    }
    
    else if(document.getElementById("mySelect").value == "Assistant teacher"){
        
        var newRole = { "roleName": "assistant teacher"};
        var idGotten = $("#id").val();
        $.ajax({
            url: "../Role"+"/"+idGotten,
            data: JSON.stringify(newRole), //Convert newPerson to JSON
            type: "post",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText + ": " + textStatus);
            }
        }).done(function () {
                    //$("#id").val(newPerson.id);
            initSRoleDetails(true);
        });
    }
    else
    {
        alert("No role value was selected!");
    }
    });

}



function initCancelBtn() {
    $("#btn_cancel").click(function () {
        clearDetails();
        initDetails(false);
        populate();
    });
}


function initPersons() {
    $("#persons").click(function (e) {
        var id = e.target.id;
        if (isNaN(id)) {
            return;
        }
        updateDetails(id);
    });
}

function deletePerson() {
    $("#delete").click(function () {
        $.ajax({
            url: "/Person/" + $("#persons option:selected").attr("id"),
            type: "DELETE",
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseText + ": " + textStatus);
            }
        }).done(function () {
        $("#fname").removeAttr("disabled");
        $("#lname").removeAttr("disabled");
        $("#phone").removeAttr("disabled");
        $("#email").removeAttr("disabled");
        $("#id").removeAttr("disabled");
        $("#fname").val("");
        $("#lname").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#id").val("");
        $("#fname").attr("disabled", "disabled");
        $("#lname").attr("disabled", "disabled");
        $("#phone").attr("disabled", "disabled");
        $("#email").attr("disabled", "disabled");
        $("#id").attr("disabled", "disabled");
        populate();
        });
    });
}

function initDetails(init) {
    if (init) {
        $("#fname").removeAttr("disabled");
        $("#lname").removeAttr("disabled");
        $("#phone").removeAttr("disabled");
        $("#email").removeAttr("disabled");
        $("#btn_save").removeAttr("disabled");
        $("#btn_cancel").removeAttr("disabled");
        $("#btn_add").attr("disabled", "disabled");
        $("#btn_role").attr("disabled", "disabled");
        $("#btn_srole").attr("disabled", "disabled");
    }
    else {
        $("#fname").val("");
        $("#lname").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#id").val("");
        $("#fname").attr("disabled", "disabled");
        $("#lname").attr("disabled", "disabled");
        $("#phone").attr("disabled", "disabled");
        $("#email").attr("disabled", "disabled");
        $("#btn_save").attr("disabled", "disabled");
        $("#btn_cancel").attr("disabled", "disabled");
        $("#btn_role").removeAttr("disabled", "disabled");
        $("#btn_srole").removeAttr("disabled", "disabled");
        $("#btn_add").removeAttr("disabled");
    }
}

function initRoleDetails(init) {
    if (init) {
        $("#degree").removeAttr("disabled");
        $("#date").removeAttr("disabled");
        $("#semester").removeAttr("disabled");


        $("#btn_cancel").removeAttr("disabled");
        $("#btn_add").attr("disabled", "disabled");
        $("#btn_save").attr("disabled", "disabled");
        $("#btn_srole").removeAttr("disabled");
    }
    else {
        $("#degree").attr("disabled", "disabled");
        $("#date").attr("disabled", "disabled");
        $("#semester").attr("disabled", "disabled");

        $("#btn_save").attr("disabled", "disabled");
        $("#btn_cancel").attr("disabled", "disabled");
        $("#btn_role").removeAttr("disabled", "disabled");
        $("#btn_srole").removeAttr("disabled", "disabled");
        $("#btn_add").attr("disabled", "disabled");
    }
}

function initSRoleDetails(init) {
    if (init) {
        $("#degree").val("");
        $("#semester").val("");
        $("#date").val("");

        $("#degree").attr("disabled", "disabled");
        $("#date").attr("disabled", "disabled");
        $("#semester").attr("disabled", "disabled");






        $("#btn_cancel").removeAttr("disabled");
        $("#btn_add").removeAttr("disabled");
        $("#btn_save").attr("disabled", "disabled");
        $("#btn_srole").attr("disabled", "disabled");
    }
    else {
        $("#degree").removeAttr("disabled");
        $("#date").removeAttr("disabled");
        $("#semester").removeAttr("disabled");

        $("#btn_save").removeAttr("disabled");
        $("#btn_cancel").removeAttr("disabled");
        $("#btn_role").removeAttr("disabled", "disabled");
        $("#btn_srole").removeAttr("disabled", "disabled");
        $("#btn_add").attr("disabled", "disabled");
    }
}

function clearDetails() {
    $("#id").val("");
    $("#fname").val("");
    $("#lname").val("");
    $("#phone").val("");
    $("#email").val("");
}
function updateDetails(id) {
    $.ajax({
        url: "../Person/" + id,
        type: "GET",
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.getResonseText + ": " + textStatus);
        }
    }).done(function (person) {
        $("#id").val(person.id);
        $("#fname").val(person.firstName);
        $("#lname").val(person.lastName);
        $("#phone").val(person.phone);
        $("#email").val(person.email);
    });
}

function fetchAll() {
    $.ajax({
        url: "../person",
        type: "GET",
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    }).done(function (persons) {
        var options = "";
        persons.forEach(function (person) {
            options += "<option id=" + person.id + ">" + person.fName[0] + ", " + person.lName + "</option>";
        });
        $("#persons").html(options);
        clearDetails();
    });
}
