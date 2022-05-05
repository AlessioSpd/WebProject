TOAST = {
    INFO: {
        COLOR: '#0d6efd',
        TITLE: 'Info'
    },
    WARNING: {
        COLOR: '#ffc107',
        TITLE: 'Attenzione'
    },
    ERROR: {
        COLOR: '#dc3545',
        TITLE: 'Errore'
    },
    SUCCESS: {
        COLOR: '#198754',
        TITLE: 'Successo'
    }
}

function toast(toastType, toastMessage) {
    let toast = document.getElementById('toast');
    toast.querySelector('.toast-header').style.backgroundColor = toastType.COLOR;
    toast.querySelector('.toast-header strong').innerHTML = toastType.TITLE;
    toast.querySelector('.toast-body').innerHTML = toastMessage;
    new bootstrap.Toast(toast).show();
}