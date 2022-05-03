let idTag = 0, tags = {};

function addTag(tag) {
    document.getElementById("tags").innerHTML = `
    <div id="tag${idTag}" class="border border-primary shadow-sm p-1 me-2 rounded-pill flex-shrink-0">
        ${tag}
        <button type="button" class="rounded-pill btn btn-danger btn-sm" onclick="removeTag(${idTag})"><i class="bi bi-x-lg"></i></button>
    </div>` + document.getElementById("tags").innerHTML;
    tags[idTag++] = {tag: tag};
}

function removeTag(id) {
    delete tags[id];
    document.getElementById("tag" + id).remove();
}

function saveTags() {
    let data = "";
    $.each(tags, (index, value) => { data += value.tag + ","; });

    let localeData = JSON.parse(sessionStorage.getItem('locale'));
    Object.assign(localeData, {tag: data});

    $.ajax({
        url: `/public/locale/update/${localeData.id}`,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(localeData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sessionStorage.setItem('locale', JSON.stringify(result.data));
            toast(TOAST.SUCCESS, 'Tags aggiornati');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}