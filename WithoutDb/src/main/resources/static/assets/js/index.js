let sectionEmail, sectionPassword, sectionRegister;

let userType, userData;

window.addEventListener('load', function () {

    if (sessionStorage.getItem('cliente')) {
        $('#btnToAccedi').hide();
        $('#btnToProfilo').attr("href", "/profiloCliente/iMieiDatiPersonali.html");
        userData = JSON.parse(sessionStorage.getItem('cliente'));
        userType = 'cliente';
        toast(TOAST.SUCCESS, 'Benvenuto ' + userData.nome + ' ' + userData.cognome);
    }
    else if (sessionStorage.getItem('locale')) {
        $('#btnToAccedi').hide();
        $('#btnToProfilo').attr("href", "profiloLocale/iMieiDatiPersonali.html");
        userData = JSON.parse(sessionStorage.getItem('locale'));
        userType = 'locale';
        toast(TOAST.SUCCESS, 'Benvenuto ' + userData.nome);
    }
    else {
        $('#btnToProfilo').hide();
        sectionEmail = document.querySelector('#sectionEmail');
        sectionPassword = document.querySelector('#sectionPassword');
        sectionRegister = {
            'cliente': document.querySelector('#sectionRegisterCustomer'),
            'locale': document.querySelector('#sectionRegisterLocal')
        };

        // da sectionPassword a sectionEmail
        document.getElementById('backFromSectionPassword').addEventListener('click', function () {
            sectionPassword.classList.remove('show', 'active');
            sectionEmail.classList.add('show', 'active');
        });
        toast(TOAST.INFO, 'Accesso non effettuato');
    }

});

// da sectionRegister a sectionEmail
function backFromSectionRegister() {
    sectionRegister[userType].classList.remove('show', 'active');
    sectionEmail.classList.add('show', 'active');
}

// da sectionEmail a sectionPassword/sectionRegister
function checkEmail(form, type) {
    userType = type;
    let email = getFormData(form).email;

    // chiamata al backend secondo il valore 'userType' - ajax
    $.ajax({
        url: `/public/${userType}/exists/${email}`,
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sectionEmail.classList.remove('show', 'active');
            if (result.data) sectionPassword.classList.add('show', 'active');
            else {
                sectionRegister[userType].classList.add('show', 'active');
                toast(TOAST.INFO, 'E-mail non registrata, crea un account');
            }
            sectionPassword.querySelector('input[name="email"]').value = sectionRegister[userType].querySelector('input[name="email"]').value = email;
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}

function saveUser(form) {

    let data = getFormData(form);

    let randomToken = (Math.random() + 1).toString(36).substring(1);
    data["token"] = randomToken;
    data["verify"] = "false";

    $.ajax({
        url: `/public/${userType}/save`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            sectionRegister[userType].classList.remove('show', 'active');
            sectionPassword.classList.add('show', 'active');
            verificationMail(data['email'], data['token']);
            toast(TOAST.SUCCESS, 'Registrazione completata, accedi al link che ti abbiamo inviato sulla tua mail per verificare il tuo account.');
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    });

    return false;
}

function checkPassword(form) {
    let data = getFormData(form);

    $.ajax({
        url: `/public/${userType}/login`,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            if(!result.data.verify) {
                toast(TOAST.INFO, "È necessario verificare l'account prima di fare login");
            }else {
                sessionStorage.setItem(userType, JSON.stringify(result.data));
                location.reload();
            }
        },
        error: function (request, error) {
            toast(TOAST.ERROR, request.responseJSON.exception);
            console.log(request, error);
        }
    })

    return false;
}