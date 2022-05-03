let interval;

// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME LOCALE...
    if (sessionStorage.getItem('locale')) {

        let localeData = JSON.parse(sessionStorage.getItem('locale'));
        $('#benvenuto').html('Benvenuto '+localeData.nome);

        // STAMPO LA TABELLA DELLE PRENOTAZIONI
        refreshReservationsTable();

        // STAMPO LA TABELLA DELLE ORDINAZIONI
        refreshOrdersTable();

        interval = setInterval(() => {
            refreshReservationsTable();
            refreshOrdersTable();
        }, 10000);



        $("#inputReservationsSearch").on("input", function () {
            let value = $(this).val().toLowerCase();
            $("#reservations tr").filter(function () {
                $(this).toggle($(this).clone().find('p').remove().end().text().replace(/ /g, '').toLowerCase().indexOf(value) > -1);
            });
        });

        $("#inputReservationsFilter").on('change', function () {
            refreshReservationsTable();
            resetTimer();
        });

        $("#inputOrdersSearch").on("input", function () {
            let value = $(this).val().toLowerCase();
            $("#orders tr").filter(function () {
                $(this).toggle($(this).clone().find('p').remove().end().text().replace(/ /g, '').toLowerCase().indexOf(value) > -1);
            });
        });

        $("#inputOrdersFilter").on('change', function () {
            refreshOrdersTable();
            resetTimer();
        });
    }
    else location.replace('/index.html');

});

window.addEventListener("beforeunload", function () {
    clearInterval(interval);
});

function resetTimer(){
    clearInterval(interval);
    interval = setInterval(() => {
        refreshReservationsTable();
        refreshOrdersTable();
    }, 10000);
}