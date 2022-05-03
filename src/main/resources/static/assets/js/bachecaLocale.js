let products = {}
let locale = {}

window.addEventListener('load', function () {

    $('#btnToProfilo').hide();

    if (sessionStorage.getItem('cliente')) {
        $('#btnToProfilo').show();
        $('#btnToProfilo').attr("href", "/profiloCliente/iMieiDatiPersonali.html");
    }
    else if (sessionStorage.getItem('locale')) {
        $('#btnToProfilo').show();
        $('#btnToProfilo').attr("href", "profiloLocale/iMieiDatiPersonali.html");
    }

    let url = new URL(window.location.href);
    let localeId = url.searchParams.get("id");

    let data = {};
    if(!localeId) {
        window.location.href = "/index.html";
    }
    data["id"] = localeId;

    //JQuery
    $.ajax({
        url: `/public/locale/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result.data.content.length == 0) {
                window.location.href = "/404.html";
            }
            locale = result.data.content[0];
            setDateMin();
            populateLocal(result.data.content[0]);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
});

function populateLocal(data) {
    document.getElementById('titolo_locale').innerText = data.nome
    document.getElementById('tag').innerText = (data.tag) ? data.tag : "";
    document.getElementById('descrizione').innerText = (data.descrizione) ? data.descrizione : "";
    document.getElementById('rating').innerText = (data.media) ? data.media + " Stelle" : "";
    document.getElementById('luogo').innerText = data.luogo;
    if(data.accetta_ordini == false)
        document.getElementById('orderButton').disabled = true
    populateMenu(JSON.parse(data.menu), data.accetta_ordini);
    getRecensioni();
    populateCarousel(JSON.parse(data.foto));
}

function populateMenu(menu, canOrder) {
    let menuTab = document.getElementById('body-menu-tab');
    if(menu.length == 0) {
        menuTab.innerHTML = '<p style="text-align: center"> Nessun piatto proposto dal locale </p>';
        return;
    }
    $.each((menu), (index, item) => {
        menuTab.insertAdjacentHTML('beforeend', `
        <div class="row" id="rowPiatto">
          <div class="col col-md-6">
            <figure class="p-3 mb-5">
                <blockquote class="blockquote">
                    <p id="nomePiatto-${index}">${item.nome}</p>
                </blockquote>
                <figcaption class="blockquote-footer">
                    ${item.descrizione}
                </figcaption>
            </figure>
           </div>
           <div class="col col-md-3">
             <figure class="p-2 mb-2">
               <blockquote class="blockquote">
                 <p id="prezzoPiatto-${index}">€ ${item.prezzo}</p>
                </blockquote>
             </figure>
           </div>
           <div class="col col-md-3">
             <figure class="p-2 mb-2">
               <blockquote class="blockquote">
                 <input type="number" min="0" max="20" value="0" id="qtyPiatto-${index}" onchange="buildOrder(this, ${index})" ${(canOrder == false) ? "disabled" : ""} >
                </blockquote>
             </figure>
           </div>
        </div>`)
    })
}

function buildOrder(el, id) {
    if(!(id in products)) {
        products[id] = [];
        products[id].push(document.getElementById("nomePiatto-" + id).textContent);
        products[id].push(el.value);
    } else {
        if(el.value == 0) {
            delete products[id];
        } else {
            products[id].pop();
            products[id].push(el.value);
        }
    }

    let totaleDOM = document.getElementById("totale");
    let totale = 0;
    Object.keys(products).forEach((element) => {
        totale = parseFloat(totale) + parseFloat(document.getElementById("prezzoPiatto-"+element).innerText.split(" ")[1]) * parseInt(products[element][1]);
    });
    totaleDOM.value = totale;
}

function ordina(el) {

    if(sessionStorage.getItem('cliente') == null) {
        toast(TOAST.ERROR, 'Non puoi ordinare senza prima effettuare il login come cliente.');
        return;
    }
    let user = JSON.parse(sessionStorage.getItem('cliente'));
    let ordine = getFormData(el);
    if(0 == ordine.totale) {
        toast(TOAST.ERROR, 'Il tuo ordine è vuoto.');
        return;
    }

    ordine["dettagli"] = JSON.stringify(products);
    ordine["stato"] = 0
    ordine["source"] = user;
    ordine["target"] = locale;

    $.ajax({
        url: `/public/ordine/save`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(ordine),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);
            toast(TOAST.SUCCESS, 'Ordine effettuato con successo! Vai a <a href="/profiloCliente/iMieiOrdini.html">I miei ordini</a>');
            if(user.blacklist)
                setTimeout(() => {toast(TOAST.WARNING, 'Sei un cliente diffidato, pagherai un euro in più!');}, 3000);
            notificationMail("ordine", user.email, locale.email);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
}

function prenota(el) {

    if(sessionStorage.getItem('cliente') == null) {
        toast(TOAST.ERROR, 'Non puoi prenotare senza prima effettuare il login come cliente.');
        return;
    }
    let user = JSON.parse(sessionStorage.getItem('cliente'));
    let prenotazione = getFormData(el);
    prenotazione["stato"] = 0
    prenotazione["source"] = user;
    prenotazione["target"] = locale;

    $.ajax({
        url: `/public/prenotazione/save`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(prenotazione),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);
            toast(TOAST.SUCCESS, 'Prenotazione effettuata con successo! Attendi che venga accettata dal locale! Vai a <a href="/profiloCliente/leMiePrenotazioni.html">Le mie prenotazioni</a>');
            if(user.blacklist)
                setTimeout(() => {toast(TOAST.WARNING, 'Sei un cliente diffidato, pagherai un euro in più!');}, 3000);
            notificationMail("prenotazione", user.email, locale.email);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
}

function getRecensioni() {
    let url = new URL(window.location.href);
    let localeId = url.searchParams.get("id");


    let data = {};
    data["target"] = localeId;
    data["stato"] = 1;

    //JQuery
    $.ajax({
        url: `/public/recensione/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            populateRecensioni(result.data.content);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
}

function populateRecensioni(recensioni) {
    let recensioniDiv = document.getElementById('recensioni');
    let recensioniTab = document.getElementById('body-recensione-tab');
    if(recensioni.length == 0) {
        recensioniDiv.innerHTML = '<p style="text-align: center"> Nessuna recensione su questo locale </p>';
    }
    $.each((recensioni), (index, item) => {
        recensioniDiv.insertAdjacentHTML('beforeend', `
                <div class="row">
                   <div class="col"><figure class="p-3 shadow-sm mb-5">
                       <blockquote class="blockquote">
                           <p>${item.source.nome + " " + item.source.cognome}</p>
                       </blockquote>
                       <figcaption>
                            <h5 class="text-warning m-2">${item.rating} <i class="bi bi-star-fill"></i></h5>
                        </figcaption>
                       <figcaption class="blockquote-footer">
                           ${item.testo}
                       </figcaption>
                    </figure></div>
                </div>`)
    })

    if(sessionStorage.getItem('cliente') == null) {
        recensioniTab.insertAdjacentHTML('beforeend', `<p style="text-align: center"> Non puoi lasciare la recensione in quanto non sei autenticato come utente. </p>`);
        return;
    }

    let localeId = locale.id;
    let userId = JSON.parse(sessionStorage.getItem('cliente')).id;

    //JQuery
    $.ajax({
        url: `/public/recensione/canAdd/user/${userId}/locale/${localeId}`,
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result.data == false) {
                recensioniTab.insertAdjacentHTML('beforeend', `<p style="text-align: center"> Non puoi lasciare la recensione in quanto non hai mai effettuato un'ordine o una prenotazione da questo locale oppure l'hai già fatta. </p>`);
                return;
            }
            recensioniTab.insertAdjacentHTML('beforeend', `
                 <figure class="p-3 shadow-sm mb-5">
                    <h5 class="p-3 bg-white rounded-top">Lascia una recensione</h5>
                    <form onsubmit='lasciaRecensione(this);return false'>
                      <div class="form-group">
                        <label for="rating">Votazione</label>
                        <input type="number" class="form-control shadow-sm" id="rating" name="rating" aria-describedby="votoHelp" min="1" max="5" required>
                        <small id="votoHelp" class="form-text text-muted">Inserisci il voto che vuoi dare al locale da 1 a 5.</small>
                      </div>
                      <div class="form-group mt-2">
                        <label class="mb-1" for="testo">Testo</label>
                        <textarea class="form-control shadow-sm" id="testo" name="testo" rows="3" required></textarea>
                      </div>
                      <button type="submit" class="mt-2 btn btn-outline-primary">Invia</button>
                    </form>
                 </figure>`);
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
}

function lasciaRecensione(el) {
    if(!locale.accettaRecensioni) {
        toast(TOAST.ERROR, 'Il locale non accetta recensioni.');
        return;
    }
    let elements = el.elements;
    let recensione ={};
    for(let i = 0 ; i < elements.length ; i++){
        let item = elements.item(i);
        if(item.name != "")
            recensione[item.name] = item.value;
    }
    let user = JSON.parse(sessionStorage.getItem('cliente'));
    if(user.blacklist) {
        toast(TOAST.ERROR, 'I clienti diffidati non possono lasciare recensioni.');
        return;
    }
    recensione["source"] = user;
    recensione["target"] = locale;
    $.ajax({
        url: `/public/recensione/save`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(recensione),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            location.reload();
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
}

function setDateMin() {
    let orarioOrdineDOM = document.getElementById("orarioOrdine");
    let dataPrenotazioneDOM = document.getElementById("dataPrenotazione");
    let d = new Date(new Date().getTime() + 1 * 3600 * 1000);
    d.setTime(d.getTime() + (90 * 60 * 1000));
    let splitted = d.toJSON().split(':');
    let dateMin = splitted[0]+ ":" + splitted[1];
    orarioOrdineDOM.setAttribute("min", dateMin);
    dataPrenotazioneDOM.setAttribute("min",dateMin);
}

function populateCarousel(foto) {
    let carouselInnerDOM = document.getElementsByClassName("carousel-inner")[0];
    console.log(foto);
    let active = true;
    $.each((foto), (index, item) => {
        console.log(index);
        carouselInnerDOM.insertAdjacentHTML('beforeend', `
        <div class="carousel-item ${(active) ? "active" : ""}"><img class="w-100 d-block" style="height: 50vh" src="${item.foto}" alt="Image ${index}"></div>`)
        active = false;
    });
}