window.addEventListener('load', function () {
    if (sessionStorage.getItem('admin'))
        location.replace('/profiloAdmin/datiPersonali.html');

    $('#passForm').hide();
    $('#emailForm').show();
});

function checkEmail(form) {
    let email = getFormData(form).email;
    if(!email) return false;

    $.ajax({
        url: `/admin/exists/${email}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result) {
                console.log("login state");
                $('#emailForm').hide();
                document.getElementById("adminEmail").value = email;
                document.getElementById("adminEmail").readOnly = true;
                $('#passForm').show();
            }
            else{
                console.log("user !exist");
                toast(TOAST.ERROR, 'Non esiste un Admin con questa mail');
            }
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
    return false;
}

function back(){
    $('#passForm').hide();
    $('#emailForm').show();
    document.getElementById("adminEmail").readOnly = false;
}

function checkPassword(form) {
    let data = getFormData(form);

    console.log("login ajax");
    console.log(data);

    $.ajax({
        url: `/admin/login`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('admin', JSON.stringify(result));
            location.replace("/profiloAdmin/datiPersonali.html")
        },
        error: function (request, error) {
            // toast(TOAST.ERROR, request.responseJSON.exception);
            toast(TOAST.INFO, 'Password errata');
            console.log(request, error);
        }
    })

    return false;
}