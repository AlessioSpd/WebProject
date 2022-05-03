let idProduct = 0, products = {};

function addProduct(nome = "", descrizione = "", prezzo = 0.0) {
    document.getElementById("products").innerHTML = `
    <div class="row p-3 g-2" id="product${idProduct}">
        <div class="col-12 col-md-5 col-lg-3">
                <input placeholder="Nome" name="nome" type="text" class="form-control shadow-sm" value="${nome}" oninput="editProduct(this, ${idProduct})" required>
        </div>
        <div class="col-12 col-md-7 col-lg-5">
            <input placeholder="Descrizione" name="descrizione" type="text" class="form-control shadow-sm" value="${descrizione}" oninput="editProduct(this, ${idProduct})" required>
        </div>
        <div class="col-9 col-lg-3">
            <div class="input-group">
                <span class="input-group-text">€</span>
                <input placeholder="Prezzo" name="prezzo" type="number" step="0.01" class="form-control shadow-sm" min="0" value="${prezzo}" oninput="editProduct(this, ${idProduct})" required>
            </div>
            
        </div>
        <div class="col-3 col-lg-1">
            <div class="text-end">
                <button type="button" class="btn btn-outline-danger" onclick="removeProduct(${idProduct})"><i class="bi bi-x-lg"></i></button>
            </div>
        </div>
    </div>` + document.getElementById("products").innerHTML;
    products[idProduct++] = {nome: nome, descrizione: descrizione, prezzo: prezzo};
}

function editProduct(element, id) {
    products[id][element.name] = isNaN(element.value) ? element.value : parseFloat(element.value);
    element.setAttribute('value', element.value);
}

function removeProduct(id) {
    delete products[id];
    document.getElementById("product" + id).remove();
}

function saveMenu() {

    let localeData = JSON.parse(sessionStorage.getItem('locale'));
    Object.assign(localeData, {menu: JSON.stringify(products)});

    $.ajax({
        url: `/public/locale/update/${localeData.id}`,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(localeData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('locale', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Menù aggiornato');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}