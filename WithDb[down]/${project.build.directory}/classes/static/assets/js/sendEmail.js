//clientToMe: template_ri4p27r
//meToClient: template_mctc0ly

//da inserire nell html
// <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/@emailjs/browser@3/dist/email.min.js"></script>

window.addEventListener('load', function () {
    emailjs.init("user_vDZ5dkSf9DWb6sYCVZXy0");
});

function notificationMail(type, sender, receiver){ //meToClient

    console.log(type, sender, receiver);

    let message;
    if(type === "ordine") message = "Hai ricevuto un nuovo ordine da parte di " + sender;
    if(type === "prenotazione") message = "Hai ricevuto una nuova prenotazione da parte di " + sender;
    if(type === "ordine-1") message = "L' ordine che hai effettuato a " + sender + " è stato rifiutato, controlla sul tuo profilo.";
    if(type === "ordine1") message = "L' ordine che hai effettuato a " + sender + " è stato accettato, controlla sul tuo profilo.";
    if(type === "prenotazione-1") message = "La prenotazione che hai effettuato a " + sender + " è stata rifiutata, controlla sul tuo profilo.";
    if(type === "prenotazione1") message = "La prenotazione che hai effettuato a " + sender + " è stata accettata, controlla sul tuo profilo.";
    if(type === "toBlack") message = "Per il tuo comportamento scorretto abbiamo deciso di metterti in blacklist, d'ora in avanti per effettuare un ordine o una prenotazione ti sarà richiesto di pagare una mora!";
    if(type === "toWhite") message = "Ti sei comportato bene, abbiamo deciso di toglierti dalla blacklist, non farcene pentire! Bentornato!";
    if(type === "review-1") message = "Ci dispiace ma la tua recensione non è stata accettata!";
    if(type === "review1") message = "Hai una nuova recensione da parte di: " + sender;
    if(type === "reviewClient") message = "La tua recensione è stata accettata!";

    let temP = {
        client_mail: receiver,
        message: message
    }

    emailjs.send('service_oxrdi3s','template_mctc0ly', temP).then(function (res){
        console.log("success", res.status);
    });
}

function verificationMail(clientMail, token){ //meToClient
    let temP = {
        client_mail: clientMail,
        message: "Benvenuto su Tidy, clicca qui per verificare il tuo account: http://localhost:8080/verificationPage.html?token=" + token
    }

    emailjs.send('service_oxrdi3s','template_mctc0ly', temP).then(function (res){
        console.log("success", res.status);
    });
}

function helpMail(){ //clientToMe
    let temP = {
        message : document.getElementById("message").value,
        client_mail : document.getElementById("clientEmail").value
    }

    emailjs.send('service_oxrdi3s','template_ri4p27r', temP).then(function (res){
        console.log("success", res.status);
    });

    document.getElementById("clientEmail").value = "";
    document.getElementById("message").value = "";
}