window.addEventListener('load', function () {

    // SE SONO AUTENTICATO
    if (sessionStorage.getItem('admin')) {
        let amministratoreData = JSON.parse(sessionStorage.getItem('admin'));
        $('#benvenuto').html('Benvenuto ' + amministratoreData.email);

        // STAMPO I DATI DEL MIO PROFILO
        setFormData('#ilMioProfilo', amministratoreData);

    }
    else location.replace('/loginAdmin.html');
    addData();
});

function addData(){
    document.getElementById('cardContainer').innerHTML = "";
    let data = {
        "blacklist": $('#listSelector').find('option:selected').val(),
        "email": document.getElementById('inputNomeUtente').value
    };

    $.ajax({
        url: `/admin/manageUser/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result)
                for(var i = 0; i < result.length; ++i)
                    addCard(result[i]);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    })

}

function addCard(utente){
    let div = document.getElementById('cardContainer');

    div.innerHTML = `<div class="row" style="padding-top: 10px;">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-body shadow-sm">
                                    <div class="row">
                                        <div class="col-8">
                                            <h4 class="card-title">${utente.email}</h4>
                                        </div>
                                        <div class="col-4">
                                            <div class="row">
                                                <div class="col-auto">` +
                                                (utente.blacklist ?
                                                    `<button class="btn btn-primary" onclick="actionOnBlacklist('${utente.email}', false)" type="button">Togli dalla blacklist</button>`
                                                    : `<button class="btn btn-danger" onclick="actionOnBlacklist('${utente.email}', true)" type="button">Inserisci nella blacklist</button>`
                                                ) + `</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>` + div.innerHTML;
}

function actionOnBlacklist(utenteMail , tipo){
    $.ajax({
        url: `/admin/manageUser/get/${utenteMail}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            result.blacklist = tipo;
            update(result);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function update(utente){
    $.ajax({
        url: `/admin/manageUser/update/${utente.id}`,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(utente),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result){
                // notificationMail((utente.blacklist ? "toBlack" : "toWhite"), "TidySrl", result.email);
                addData();
            } else
                toast(TOAST.ERROR, 'qualcosa è andato storto!');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            // console.log(request, error);
        }
    });
}