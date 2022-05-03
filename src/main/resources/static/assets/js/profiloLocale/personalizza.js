// AL CARICAMENTO DELLA PAGINA
window.addEventListener('load', function () {

    // SE SONO AUTENTICATO COME LOCALE...
    if (sessionStorage.getItem('locale')) {

        let localeData = JSON.parse(sessionStorage.getItem('locale'));
        $('#benvenuto').html('Benvenuto '+localeData.nome);

        // STAMPO LA BIOGRAFIA
        setFormData('#biografiaLocale', localeData);

        // STAMPO I TAGS
        $.each(localeData.tag.split(",").filter(Boolean), (index, value) => {addTag(value)});

        // STAMPO LE FOTO
        $.each(JSON.parse(localeData.foto), (index, value) => {addPhoto(value.foto)});

        // STAMPO IL MENU
        $.each(JSON.parse(localeData.menu), (index, value) => {addProduct(value.nome, value.descrizione, value.prezzo)});

        document.getElementById('inputPhotos').addEventListener('change', function () {
              [...this.files].forEach( file => { image_to_base64(file).then(base64 => reduce_image_file_size(base64).then(photo => addPhoto(photo))) });
            this.value = null;
        });

        document.getElementById('inputTag').addEventListener('keyup', function (e) {
            if (e.key === ' ') {
                if (this.value.trim().length === 0 || this.value.indexOf(' ') !== (this.value.length - 1)) this.value = this.value.replaceAll(' ', '');
                else {
                    this.value.trim().split(' ').forEach(word => addTag(word));
                    this.value = "";
                }
            }
        });
    }
    else location.replace('/index.html');

});

function saveBiography(form) {

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
            toast(TOAST.SUCCESS, 'Biografia aggiornata');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}