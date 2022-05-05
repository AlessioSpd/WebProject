// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME LOCALE...
    if (sessionStorage.getItem('locale')) {

        let localeData = JSON.parse(sessionStorage.getItem('locale'));
        $('#benvenuto').html('Benvenuto '+localeData.nome);

        // STAMPO I DATI DEL MIO PROFILO
        setFormData('#ilMioProfilo', localeData);

    } 
    else location.replace('/index.html');

});

function saveIlMioProfilo(form) {

    let data = getFormData(form);
    let localeData = JSON.parse(sessionStorage.getItem('locale'));
    Object.assign(localeData, data);

    $.ajax({
        url: `/public/locale/update/${localeData.id}`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(localeData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('locale', JSON.stringify(result.data));
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
    let localeData = JSON.parse(sessionStorage.getItem('locale'));

    $.ajax({
        url: `/public/locale/changePassword/${localeData.id}`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('locale', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Password aggiornata');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}