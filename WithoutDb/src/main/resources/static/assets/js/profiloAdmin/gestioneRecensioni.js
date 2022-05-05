window.addEventListener('load', function () {

    // SE SONO AUTENTICATO
    if (sessionStorage.getItem('admin')) {
        let amministratoreData = JSON.parse(sessionStorage.getItem('admin'));
        $('#benvenuto').html('Benvenuto ' + amministratoreData.email);

        // STAMPO I DATI DEL MIO PROFILO
        setFormData('#ilMioProfilo', amministratoreData);
    }else location.replace('/loginAdmin.html');

    loadData();
});

function loadData(){
    document.getElementById('cardContainer').innerHTML = "";
    let data = {};

    let state = $('#stateSelector').find('option:selected').val();

    if(state !== "2"){
        data["stato"] = state;
        console.log("data da mandare == " + JSON.stringify(data));

        $.ajax({
            url: `/admin/manageReview/filter`,
            type: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if(result)
                    for(var i = 0; i < result.length; ++i){
                        addCard(result[i]);
                    }
            },
            error: function (request, error) {
                toast(TOAST.ERROR, request.responseJSON.exception);
                console.log(request, error);
            }
        })
    }
}

function addCard(content){
    let div = document.getElementById('cardContainer');
    div.innerHTML = `<div class="row" style="padding-top: 10px;">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-body shadow-sm">
                                        <h4 class="card-title">${content.source.nome} ${content.source.cognome}</h4>
                                        <h6 class="text-muted card-subtitle mb-2">${content.target.nome}</h6>
                                        <p class="card-text">${content.testo}</p>
                                        <div>
                                            <div class="row">` +
                                                (content.stato === 0 ?
                                                    `<div class="col-auto">
                                                        <button class="btn btn-primary" type="button" onclick="actionOnState('${content.id}', 1)">Accetta</button>
                                                    </div>
                                                    <div class="col-auto">
                                                        <button class="btn btn-danger" type="button" onclick="actionOnState('${content.id}', -1)">Rifiuta</button>
                                                    </div>`
                                                    : (content.stato === 1 ?
                                                        `<div class="col-auto">
                                                            <button class="btn btn-danger" type="button" onclick="actionOnState('${content.id}', -1)">Rifiuta</button>
                                                        </div>` : ``)
                                                ) + `
                                                <div class="col-auto">
                                                    <div style="padding-top: 9px" >
                                                        <i class="bi bi-star">${content.rating}</i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>` + div.innerHTML;
    return false;
}

function actionOnState(idReview, state){
    $.ajax({
        url: `/admin/manageReview/get/${idReview}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            result.stato = state;
            // if(state === -1) notificationMail("review" + state, "TidySrl", result.email);
            // else{
            //     notificationMail("review" + state, result.email, result.email);
            //     notificationMail("reviewClient", "TidySrl", result.email);
            // }
            update(result)
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function update(review){
    $.ajax({
        url: `/admin/manageReview/update/${review.id}`,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(review),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            loadData();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}