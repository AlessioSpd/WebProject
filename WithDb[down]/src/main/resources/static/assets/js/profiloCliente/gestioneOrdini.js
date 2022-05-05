let idOrder = 0, orders = {};

const STATES = {
    REFUSED: -1,
    PENDING: 0,
    ACCEPTED: 1
}

function addOrder(ordine) {

    let div = document.getElementById('orders');

    div.innerHTML = `
    <div class="col mt-3">
        <div class="card shadow-sm m-auto">` +
        (ordine.stato === STATES.PENDING ?
            `<div class="text-end p-3">
                <button type="button" class="btn btn-outline-warning" onclick="showEditableOrder(${idOrder});"><i class="bi bi-pencil"></i></button>
                <button type="button" class="btn btn-outline-danger" onclick="deleteOrder(${idOrder});"><i class="bi bi-trash"></i></button>
           </div>` : ``) +
        `<div class="blockquote card-header d-flex flex-row justify-content-between">
            <h5 class="mb-0">${ordine.target.nome}</h5>` +
            (ordine.stato === STATES.ACCEPTED ?
            `<span class="badge bg-success">Accettata</span>` :
            (ordine.stato === STATES.REFUSED ?
            `<span class="badge bg-danger">Rifiutata</span>` :
            `<span class="badge bg-primary">In attesa...</span>`)) +
            `</div>
        <div class="card-body">
                <p type="text" >Info: ${ordine.info}</p>
                <p type="text" >Data e ora: ${ordine.orario.replace('T', ' ')}</p>
                <div class="d-grid">
                    <button onclick="location.replace('/bachecaLocale.html?id=${ordine.target.id}');" type="button" class="btn btn-outline-primary">Visualizza locale</button>
                </div>
            </div>
        </div>
    </div>` + div.innerHTML;

    orders[idOrder++] = ordine;
}


let myModal;

function hideOrder() {
    myModal.hide();
    resetTimer();
}

function showEditableOrder(index) {

    clearInterval(interval);
    let modal = document.getElementById('modal');
    modal.setAttribute('data-index', index);
    setFormData($(modal).find('form'), orders[index]);

    myModal = new bootstrap.Modal($('#modal'), {
        backdrop: 'static',
        keyboard: false,
        focus: true
    });

    myModal.show();
}

function updateOrder(form) {

    let index = document.getElementById('modal').getAttribute('data-index');

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UN ORDINE, MODIFICALO CON I NUOVI DATI!
    let ordineData = orders[index];
    Object.assign(ordineData, getFormData(form));

    $.ajax({
        url: `/public/ordine/update/${ordineData.id}`,
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(ordineData),
        success: function (result) {
            refreshOrdersList();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
}

function deleteOrder(index) {

    clearInterval(interval);

    // [CHIAMATA AL BACKEND] - DATO L'ID DI UN ORDINE, ELIMINALO!
    $.ajax({
        url: `/public/ordine/delete/${orders[index].id}`,
        type: 'DELETE',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            refreshOrdersList();
            resetTimer();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    refreshOrdersList();
}

function refreshOrdersList() {

    // [CHIAMATA AL BACKEND] - DATO UN CLIENTE RESTITUISCIMI TUTTI I SUOI ORDINI
    let ordineCriteria = {
        clienteId: JSON.parse(sessionStorage.getItem('cliente')).id,
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