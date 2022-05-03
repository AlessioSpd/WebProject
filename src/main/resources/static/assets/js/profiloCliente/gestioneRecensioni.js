idReview = 0, reviews = {};

const STATES = {
    REFUSED: -1,
    PENDING: 0,
    ACCEPTED: 1
}

function addReview(recensione) {

    let div = document.getElementById("reviews");

    div.innerHTML = `
        <div class="row mt-3">
            <div class="col-12">
            <figure class="p-3 shadow-sm bg-white rounded">
                <div class="text-end pb-3">   
                    <button type="button" class="btn btn-outline-warning" onclick="showEditableReview(${idReview});"><i class="bi bi-pencil"></i></button>
                    <button type="button" class="btn btn-outline-danger" onclick="deleteReview(${idReview});"><i class="bi bi-trash"></i></button>
                </div>
                <blockquote class="blockquote">
                <div class="d-flex justify-content-between">
                    <h5 class="mb-0">Hai recensito "${recensione.target.nome}"</h5>` +
                    (recensione.stato === STATES.ACCEPTED ?
                    `<span class="badge bg-success">Accettata</span>` :
                    (recensione.stato === STATES.REFUSED ?
                    `<span class="badge bg-danger">Rifiutata</span>` :
                    `<span class="badge bg-info">In attesa...</span>`)) +
                `</div>
                <h5 class="text-warning">${recensione.rating} <i class="bi bi-star-fill"></i></h5>
                </blockquote>
                <br>
                <figcaption class="blockquote-footer">${recensione.testo}</figcaption>
                <div class="d-grid">
                    <button onclick="location.replace('/bachecaLocale.html?id=${recensione.target.id}');" type="button" class="btn btn-outline-primary">Visualizza locale</button>
                </div>
            </figure>
            
            </div>
        </div>` + div.innerHTML;

    reviews[idReview++] = recensione;
}


let myModal;

function hideReview() {
    myModal.hide();
}

function showEditableReview(index) {

    let modal = document.getElementById('modal');
    modal.setAttribute('data-index', index);
    setFormData($(modal).find('form'), reviews[index]);

    myModal = new bootstrap.Modal($('#modal'), {
        backdrop: 'static',
        keyboard: false,
        focus: true
    });

    myModal.show();
}

function updateReview(form) {

    let index = document.getElementById('modal').getAttribute('data-index');

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UNA RECENSIONE, MODIFICALA CON I NUOVI DATI!
    let recensioneData = reviews[index];
    Object.assign(recensioneData, getFormData(form));
    Object.assign(recensioneData, {stato: 0})

    $.ajax({
        url: `/public/recensione/update/${recensioneData.id}`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(recensioneData),
        success: function (result) {
            refreshReviewsList();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function deleteReview(index) {

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UNA RECENSIONE, ELIMINALA!
    $.ajax({
        url: `/public/recensione/delete/${reviews[index].id}`,
        type: 'DELETE',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            refreshReviewsList();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    refreshReviewsList();
}

function refreshReviewsList() {

    // [CHIAMATA AL BACKEND] - DATO UN CLIENTE RESTITUISCIMI TUTTE LE SUE RECENSIONI
    let recensioneCriteria = {
        clienteId: JSON.parse(sessionStorage.getItem('cliente')).id,
    }

    $.ajax({
        url: `/public/recensione/filter`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(recensioneCriteria),
        success: function (result) {
            document.getElementById("reviews").innerHTML = '';
            idReview = 0;
            $.each(result.data.content, (index, value) => addReview(value));
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}