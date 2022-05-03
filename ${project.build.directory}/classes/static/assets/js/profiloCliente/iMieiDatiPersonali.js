// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME CLIENTE...
    if (sessionStorage.getItem('cliente')) {

        let clienteData = JSON.parse(sessionStorage.getItem('cliente'));
        $('#benvenuto').html('Benvenuto '+clienteData.nome);

        // STAMPO I DATI DEL MIO PROFILO
        setFormData('#ilMioProfilo', clienteData);

    } 
    else location.replace('/index.html');

});

function saveIlMioProfilo(form) {

    let data = getFormData(form);
    let clienteData = JSON.parse(sessionStorage.getItem('cliente'));
    Object.assign(clienteData, data);

    $.ajax({
        url: `/public/cliente/update/${clienteData.id}`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(clienteData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('cliente', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Profilo aggiornato');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}

function changePassword(form) {

    let data = getFormData(form);
    let clienteData = JSON.parse(sessionStorage.getItem('cliente'));

    $.ajax({
        url: `/public/cliente/changePassword/${clienteData.id}`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('cliente', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Password aggiornata');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}