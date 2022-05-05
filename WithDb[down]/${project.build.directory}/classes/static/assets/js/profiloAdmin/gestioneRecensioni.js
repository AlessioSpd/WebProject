window.addEventListener('load', function () {

    // SE SONO AUTENTICATO
    if (sessionStorage.getItem('amministratore')) {
        let amministratoreData = JSON.parse(sessionStorage.getItem('amministratore'));
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

    if(state !== "2") data["stato"] = state;

    $.ajax({
        url: `/public/recensione/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result.data.content)
                for(var i = 0; i < result.data.content.length; ++i){
                    addCard(result.data.content[i]);
                }
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    })
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
        url: `/public/recensione/get/${idReview}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            result.data.stato = state;
            if(state === -1) notificationMail("review" + state, "TidySrl", result.data.source.email);
            else{
                notificationMail("review" + state, result.data.source.email, result.data.target.email);
                notificationMail("reviewClient", "TidySrl", result.data.source.email);
            }
            update(result.data)
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function update(utente){
    $.ajax({
        url: `/public/recensione/update/${utente.id}`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(utente),
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