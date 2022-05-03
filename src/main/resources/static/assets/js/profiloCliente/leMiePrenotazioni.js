let interval;

// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME CLIENTE...
    if (sessionStorage.getItem('cliente')) {

        let clienteData = JSON.parse(sessionStorage.getItem('cliente'));
        $('#benvenuto').html('Benvenuto '+clienteData.nome);

        // STAMPO LE MIE PRENOTAZIONI
        refreshReservationsList();

        interval = setInterval(() => {
            refreshReservationsList();
        }, 10000);

    }
    else location.replace('/index.html');

});

window.addEventListener("beforeunload", function () {
    clearInterval(interval);
});

function resetTimer(){
    interval = setInterval(() => {
        refreshReservationsList();
    }, 10000);
}