window.addEventListener('load', function () {

    var url = new URL(window.location.href);
    var token = {};
    token["token"] = url.searchParams.get("token");

    $.ajax({
        url: `/public/locale/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(token),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
           setVerify('locale' ,result.data.content[0]);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });

    $.ajax({
        url: `/public/cliente/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(token),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            setVerify('cliente', result.data.content[0]);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
});

function setVerify(userType, content){
    content.verify = true;

    $.ajax({
        url: `/public/${userType}/update/${content.id}`,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(content),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            location.replace("/index.html");
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}