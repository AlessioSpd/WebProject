window.addEventListener('load', function () {

    console.log("session storage" + sessionStorage.getItem('admin'));
    // SE SONO AUTENTICATO
    if (sessionStorage.getItem('admin')) {
        let amministratoreData = JSON.parse(sessionStorage.getItem('admin'));
        $('#benvenuto').html('Benvenuto ' + amministratoreData.email);
        // STAMPO I DATI DEL MIO PROFILO
        setFormData('#ilMioProfilo', amministratoreData);
    }
    else location.replace('/loginAdmin.html');

    $('#confirmationSection').hide();
});

function saveIlMioProfilo(form) {

    let data = getFormData(form);
    let amministratoreData = JSON.parse(sessionStorage.getItem('admin'));
    Object.assign(amministratoreData, data);

    $.ajax({
        url: `/admin/changeMail/${amministratoreData.id}`,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(amministratoreData),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(JSON.stringify(result));
            sessionStorage.setItem('admin', JSON.stringify(result));
            toast(TOAST.SUCCESS, 'Profilo aggiornato');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Email gia esistente, o non valida');
            console.log(request, error);
        }
    });
    return false;
}

function changePassword(form) {

    let data = getFormData(form);
    let amministratoreData = JSON.parse(sessionStorage.getItem('admin'));

    $.ajax({
        url: `/admin/check/${data['oldPassword']}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(result) {
                $.ajax({
                    url: `/amdin/changePassword/${amministratoreData.id}`,
                    type: "PUT",
                    dataType: "json",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    success: function (result) {
                        console.log("risultati cambio password" + JSON.stringify(result));
                        sessionStorage.setItem('admin', JSON.stringify(result));
                        toast(TOAST.SUCCESS, 'Password aggiornata');
                    },
                    error: function (request, error) {
                        toast(TOAST.ERROR, request.responseJSON.exception);
                        console.log(request, error);
                    }
                });
            }
            else{
                toast(TOAST.ERROR, 'vecchia password non corretta!');
            }
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });
    document.getElementById('newPas').value = "";
    document.getElementById('oldPas').value = "";

    return false;
}

function checkMyPass(pass){
    let myMail =  JSON.parse(sessionStorage.getItem('admin')).email;

    let data = {};
    data['email'] = myMail;
    data['password'] = pass;

    $.ajax({
        url: `/public/amministratore/filter`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log("la password è giusta");
            return true;
        },
        error: function (request, error) {
            toast(TOAST.ERROR, 'Password inserita non valida');
            console.log(request, error);
            return false;
        }
    });

    return false;
}

function gestioneAdmin(){
    let email = document.getElementById("emailInput").value; //mail dell account da cancellare
    let pass = document.getElementById("passInput").value; //la mia password

    if(!pass || !email) return false;

    //controllo la mia password
    if(checkMyPass(pass)) return false;

    $.ajax({
        url: `/public/amministratore/get/${email}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            (result.data ? loadConfirmForm(result.data.id, true) : loadConfirmForm('null', false));
        },
        error: function (request, error) {
        }
    });
}

function loadConfirmForm(idAdmin, exist){
    console.log("1id = " + idAdmin);
    document.getElementById("emailInput").readOnly = true;
    document.getElementById("passInput").readOnly = true;
    let div = document.getElementById('confirmationItem');
    div.innerHTML = ``;
    div.innerHTML = (exist ? `<label class="form-label" id="confirmLabel">Esiste un admin con questi dati, eliminarlo?</label>
                              <button type="submit" class="btn btn-outline-primary" onclick="this.form.confirmType = 'cancel'; this.form.adminID = 'null'; this.form.moveType = 'null';">Annulla</button>
                              <button type="submit" class="btn btn-outline-danger" onclick="this.form.confirmType = 'confirm'; this.form.adminID = '${idAdmin}'; this.form.moveType = 'pop';">Conferma</button>`
                            : ` <label class="form-label">Password per il nuovo admin</label>
                                <input name="password" type="password" class="form-control shadow-sm" required id="passInput">  
                                <label class="form-label" id="confirmLabel">I dati inseriti non esistono, creare un Admin?</label>
                                <button type="submit" class="btn btn-outline-primary" onclick="this.form.confirmType = 'cancel'; this.form.adminID = 'null';  this.form.moveType = 'null';">Annulla</button>
                                <button type="submit" class="btn btn-outline-danger" onclick="this.form.confirmType = 'confirm'; this.form.adminID = 'null'; this.form.moveType = 'push';">Conferma</button>`
    ) + div.innerHTML;

    $('#confirmationSection').show();

    return false;
}

function confirmAction(form, confirmType, idAdmin, moveType){
    let data = getFormData(form);
    console.log("2id = " + idAdmin);

    if(confirmType === 'cancel'){
        document.getElementById("emailInput").innerHTML = '';
        document.getElementById("passInput").innerHTML = '';
        $('#confirmationSection').hide();
    }else{
        if(moveType === 'pop'){
            console.log("entro nel pop con id = " + idAdmin);
            $.ajax({
                url: `/public/amministratore/deleteRow/${idAdmin}`,
                type: 'DELETE',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    console.log(result.data);
                    toast(TOAST.SUCCESS, 'Eliminazione Admin completata.');
                },
                error: function (request, error) {
                    toast(TOAST.ERROR, request.responseJSON.exception);
                    console.log(request, error);
                }
            });
        }else{
            $.ajax({
                url: `/public/amministratore/save`,
                type: "POST",
                dataType: "json",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    toast(TOAST.SUCCESS, 'Creazione Admin completata.');
                },
                error: function (request, error) {
                    toast(TOAST.ERROR, request.responseJSON.exception);
                    console.log(request, error);
                }
            });
        }
    }

    //Da cambiare con
    $('#confirmationSection').hide();
    document.getElementById("emailInput").readOnly = false;
    document.getElementById("passInput").readOnly = false;
    document.getElementById("emailInput").innerHTML = '';
    document.getElementById("passInput").innerHTML = '';

    return false;
}