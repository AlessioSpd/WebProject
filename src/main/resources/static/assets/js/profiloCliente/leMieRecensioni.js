// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME CLIENTE...
    if (sessionStorage.getItem('cliente')) {

        let clienteData = JSON.parse(sessionStorage.getItem('cliente'));
        $('#benvenuto').html('Benvenuto '+clienteData.nome);

        // PRENDO TUTTE LE MIE RECENSIONI
        let recensioneCriteria = {
            clienteId : clienteData.id
        }

        $.ajax({
            url: `/public/recensione/filter`,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(recensioneCriteria),
            success: function (result) {
                // STAMPO LE RECENSIONI DEL CLIENTE
                $.each(result.data.content, (index, value) => addReview(value));
            },
            error: function (request, error) {
                toast(TOAST.ERROR, request.responseJSON.exception);
                console.log(request, error);
            }
        });

    }
    else location.replace('/index.html');

});