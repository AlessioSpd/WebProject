let idReservation = 0, reservations = {};

const STATES = {
    REFUSED: -1,
    PENDING: 0,
    ACCEPTED: 1
}

function addReservation(prenotazione) {

    let div = document.getElementById('reservations');

    div.innerHTML = `
    <div class="col mt-3">
        <div class="card shadow-sm m-auto">` +
        (prenotazione.stato === STATES.PENDING ?
            `<div class="text-end p-3">
                <button type="button" class="btn btn-outline-warning" onclick="showEditableReservation(${idReservation});"><i class="bi bi-pencil"></i></button>
                <button type="button" class="btn btn-outline-danger" onclick="deleteReservation(${idReservation});"><i class="bi bi-trash"></i></button>
           </div>` : ``) +
        `<div class="blockquote card-header d-flex justify-content-between">
            <h5 class="mb-0">${prenotazione.target.nome}</h5>` +
            (prenotazione.stato === STATES.ACCEPTED ?
            `<span class="badge bg-success">Accettata</span>` :
            (prenotazione.stato === STATES.REFUSED ?
            `<span class="badge bg-danger ">Rifiutata</span>` :
            `<span class="badge bg-primary">In attesa...</span>`)) +
            `</div>
        <div class="card-body">
                <p type="text" >Numero persone: ${prenotazione.numeroPersone}</p>
                <p type="text" >Data e ora: ${prenotazione.data.replace('T', ' ')}</p>
                <div class="d-grid">
                    <button onclick="location.replace('/bachecaLocale.html?id=${prenotazione.target.id}');" type="button" class="btn btn-outline-primary">Visualizza locale</button>
                </div>
            </div>
        </div>
    </div>` + div.innerHTML;

    reservations[idReservation++] = prenotazione;
}


let myModal;

function hideReservation() {
    myModal.hide();
    resetTimer();
}

function showEditableReservation(index) {

    clearInterval(interval);
    let modal = document.getElementById('modal');
    modal.setAttribute('data-index', index);
    setFormData($(modal).find('form'), reservations[index]);

    myModal = new bootstrap.Modal(modal, {
        backdrop: 'static',
        keyboard: false,
        focus: true
    });

    myModal.show();

}

function updateReservation(form) {

    let index = document.getElementById('modal').getAttribute('data-index');

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UNA PRENOTAZIONE, MODIFICALA CON I NUOVI DATI!
    let prenotazioneData = reservations[index];
    Object.assign(prenotazioneData, getFormData(form));

    $.ajax({
        url: `/public/prenotazione/update/${prenotazioneData.id}`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(prenotazioneData),
        success: function (result) {
            refreshReservationsList();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function deleteReservation(index) {

    clearInterval(interval);

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UNA PRENOTAZIONE, ELIMINALA!
    $.ajax({
        url: `/public/prenotazione/delete/${reservations[index].id}`,
        type: 'DELETE',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            refreshReservationsList();
            resetTimer();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    refreshReservationsList();
}

function refreshReservationsList() {

    // [CHIAMATA AL BACKEND] - DATO UN CLIENTE RESTITUISCIMI TUTTE LE SUE PRENOTAZIONI
    let prenotazioneCriteria = {
        clienteId: JSON.parse(sessionStorage.getItem('cliente')).id,
        // orderBy: "data",
        // sortDirection: "DESC"
    }

    $.ajax({
        url: `/public/prenotazione/filter`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(prenotazioneCriteria),
        success: function (result) {
            document.getElementById("reservations").innerHTML = '';
            idReservation = 0;
            $.each(result.data.content, (index, value) => addReservation(value));
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}