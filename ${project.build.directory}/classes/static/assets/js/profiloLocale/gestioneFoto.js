let idPhoto = 0, photos = {};

function addPhoto(foto) {

    if(Object.keys(photos).length < 3) {
        document.getElementById("photos").innerHTML = `
    <div id="photo${idPhoto}" class="shadow-sm me-2 flex-shrink-0 position-relative">
        <img src="${foto}" style="width: 200px; height: 100px;" class="img-thumbnail">
        <button type="button" class="btn btn-danger position-absolute top-0 end-0" onclick="removePhoto(${idPhoto})"><i class="bi bi-x-lg"></i></button>
    </div>` + document.getElementById("photos").innerHTML;
        photos[idPhoto++] = {foto: foto};
    }
}

function removePhoto(id) {
    delete photos[id];
    document.getElementById("photo" + id).remove();
}

function savePhotos() {

    let localeData = JSON.parse(sessionStorage.getItem('locale'));
    Object.assign(localeData, {foto: JSON.stringify(photos)});

    $.ajax({
        url: `/public/locale/update/${localeData.id}`,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(localeData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('locale', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Foto aggiornate');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}