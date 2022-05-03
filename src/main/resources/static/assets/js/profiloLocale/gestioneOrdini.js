let idOrder = 0, orders = {};

function addOrder(ordine) {
    document.getElementById("orders").innerHTML = `
    <tr id="order${idOrder}">
        <td>${ordine.source.nome}</td>
        <td>${ordine.source.cognome}</td>
        <td>${ordine.source.telefono}</td>
        <td>${ordine.orario}</td>
        <td>
            <button type="button" class="btn btn-outline-primary" onclick="showOrder(${idOrder});""><i class="bi bi-info-lg"></i></button>
        </td>
        <td id=${"orderAction" + idOrder}>` +
            (ordine.stato === STATES.ACCEPTED ?
            `<p class="text-success">Accettato</p>` :
            (ordine.stato === STATES.REFUSED ?
            `<p class="text-danger">Rifiutato</p>` :
            `<button type="button" class="btn btn-outline-danger" onclick="updateOrder(${idOrder}, STATES.REFUSED);"><i class="bi bi-x-lg"></i></button>
            <button type="button" class="btn btn-outline-success" onclick="updateOrder(${idOrder}, STATES.ACCEPTED);"><i class="bi bi-check-lg"></i></button>`)) +
        `</td>
    </tr>` + document.getElementById("orders").innerHTML;
    orders[idOrder++] = ordine;
}


let myModal;

function hideOrder() {
    myModal.hide();
    resetTimer();
}

function showOrder(index) {

    clearInterval(interval);
    let modal = document.getElementById('modal');
    setFormData($(modal).find('form'), orders[index]);

    myModal = new bootstrap.Modal($('#modal'), {
        backdrop: 'static',
        keyboard: false,
        focus: true
    });

    myModal.show();
}

function updateOrder(id, stato) {

    clearInterval(interval);
    let ordineData = orders[id];
    Object.assign(ordineData, {stato: stato});

    $.ajax({
        url: `/public/ordine/update/${ordineData.id}`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(ordineData),
        success: function (result) {
            notificationMail("ordine" + stato, ordineData.target.nome, ordineData.source.email);
            refreshOrdersTable();
            resetTimer();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function refreshOrdersTable() {

    let ordineCriteria = {
        localeId : JSON.parse(sessionStorage.getItem('locale')).id,
        stato : $('#inputOrdersFilter').val()
    }

    $.ajax({
        url: `/public/ordine/filter`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(ordineCriteria),
        success: function (result) {
            document.getElementById("orders").innerHTML = '';
            idOrder = 0;
            $.each(result.data.content, (index, value) => addOrder(value));
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}