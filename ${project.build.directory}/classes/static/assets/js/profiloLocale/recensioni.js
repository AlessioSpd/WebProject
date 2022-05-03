// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {


    // SE SONO AUTENTICATO COME LOCALE...
    if (sessionStorage.getItem('locale')) {

        let localeData = JSON.parse(sessionStorage.getItem('locale'));
        $('#benvenuto').html('Benvenuto ' + localeData.nome);

        $('#accettaRecensioni').prop('checked', localeData.accettaRecensioni);

        // PRENDO TUTTE LE MIE RECENSIONI
        let recensioneCriteria = {
            localeId: localeData.id
        }

        $.ajax({
            url: `/public/recensione/filter`,
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(recensioneCriteria),
            success: function (result) {
                // STAMPO LE RECENSIONI DEL LOCALE
                $.each(result.data.content, (index, value) => addReview(value));
            },
            error: function (request, error) {
                toast(TOAST.ERROR, request.responseJSON.exception);
                console.log(request, error);
            }
        });

        this.document.getElementById('accettaRecensioni').addEventListener('change', (event) => {

            let localeData = JSON.parse(sessionStorage.getItem('locale'));
            Object.assign(localeData, {accettaRecensioni: event.currentTarget.checked});

            $.ajax({
                url: `/public/locale/update/${localeData.id}`,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(localeData),
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    sessionStorage.setItem('locale', JSON.stringify(result.data));
                    toast(TOAST.SUCCESS, 'Scelta aggiornata');
                },
                error: function (request, error) {
                    toast(TOAST.ERROR, request.responseJSON.exception);
                    console.log(request, error);
                }
            });

        });

    } else location.replace('/index.html');

});

function addReview(recensione) {
    document.getElementById("reviews").innerHTML = `
        <div class="row mt-3">
            <div class="col-12">
                <figure class="p-3 shadow-sm bg-white rounded">
                    <blockquote class="blockquote">
                        <p>${recensione.source.nome + ' ' + recensione.source.cognome} <span class="text-warning">${recensione.rating} <i class="bi bi-star-fill"></i></span></p>
                    </blockquote>
                    <br>
                    <figcaption class="blockquote-footer">${recensione.testo}</figcaption>
                </figure>
            </div>
        </div>` + document.getElementById("reviews").innerHTML;
}