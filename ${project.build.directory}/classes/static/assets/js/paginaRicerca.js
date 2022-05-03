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

    //prendo i dati dall url
    var url = new URL(window.location.href);
    var tag = url.searchParams.get("tag");
    var luogo = url.searchParams.get("luogo");
    var rating = url.searchParams.get("rating");

    var data = {};
    if(tag){
        data["tag"] = tag;
        document.getElementById("inputTag").value = tag;
    }
    if(luogo){
        data["luogo"] = luogo;
        document.getElementById("inputLuogo").value = luogo;
    }
    if(rating && rating !== '0'){ //se è 0 non filtriamo per rating
        data["media"] = rating;
        document.getElementById("ratingSelector").value = rating;
    }

    //JQuery
    $.ajax({
        url: `/public/locale/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result.data.content)
                for(var i = 0; i < result.data.content.length; ++i){
                    addCard(result.data.content[i]);
                }
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Errore ' + request.responseJSON.exception);
        }
    });
});

//mi passo anche l'id cosi possiamo passarlo alla bachecaLocale
function addCard(locale){
    let div = document.getElementById('resultCards');

    let foto = "assets/img/placeholder.png";
    $.each(JSON.parse(locale.foto), (index, value) => {foto = value.foto; return})

    div.innerHTML = `<div class="col">
                         <div class="card shadow-sm m-auto" style="max-width: 300px;">
                             <img src=${foto} class="card-img-top" style="height: 200px;">
                             <div class="card-body overflow-auto">
                                 <h5 class="card-title">${locale.nome}</h5>
                                 <p class="card-text">${locale.luogo}</p>
                                 <p class="card-text"><i class="bi bi-star">${locale.media}</i></p>
                                 <div class="d-grid">
                                    <button onclick="location.replace('/bachecaLocale.html?id=${locale.id}')"
                                        type="submit" class="btn btn-outline-primary">Visualizza locale
                                    </button>
                                 </div>
                             </div>
                         </div>
                     </div>` + div.innerHTML;
}