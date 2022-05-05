let idReservation = 0, reservations = {};

let STATES = {
    REFUSED: -1,
    PENDING: 0,
    ACCEPTED: 1
};

function addReservation(prenotazione) {
    document.getElementById("reservations").innerHTML = `
        <tr id="reservation${idReservation}">
            <td>${prenotazione.source.nome}</td>
            <td>${prenotazione.source.cognome}</td>
            <td>${prenotazione.source.telefono}</td>
            <td>${prenotazione.data}</td>
            <td>${prenotazione.numeroPersone}</td>
            <td id=${"reservationAction" + idReservation}>` +
                (prenotazione.stato === STATES.ACCEPTED ?
                `<p class="text-success">Accettato</p>` :
                (prenotazione.stato === STATES.REFUSED ?
                `<p class="text-danger">Rifiutato</p>` :
                `<button type="button" class="btn btn-outline-danger" onclick="saveReservation(${idReservation}, STATES.REFUSED);"><i class="bi bi-x-lg"></i></button>
                <button type="button" class="btn btn-outline-success" onclick="saveReservation(${idReservation}, STATES.ACCEPTED);"><i class="bi bi-check-lg"></i></button>`)) +
            `</td>
        </tr>` + document.getElementById("reservations").innerHTML;
    reservations[idReservation++] = prenotazione;
}

function saveReservation(id, stato) {

    let prenotazioneData = reservations[id];
    Object.assign(prenotazioneData, {stato: stato});

    $.ajax({
       url: `/public/prenotazione/update/${prenotazioneData.id}`,
       type: 'POST',
       dataType: 'json',
       contentType: "application/json; charset=utf-8",
       data: JSON.stringify(prenotazioneData),
       success: function (result) {
           refreshReservationsTable();
           notificationMail("prenotazione" + stato, prenotazioneData.target.nome, prenotazioneData.source.email);
       },
       error: function (request, error) {
           toast(TOAST.ERROR, request.responseJSON.exception);
           console.log(request, error);
       }
    });
}

function refreshReservationsTable() {

    let prenotazioneCriteria = {
       localeId : JSON.parse(sessionStorage.getItem('locale')).id,
       stato : $('#inputReservationsFilter').val()
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

