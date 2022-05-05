const simpleTypes = ['color', 'date', 'email', 'hidden', 'month', 'number', 'password', 'range',
    'tel', 'text', 'time', 'url', 'week'];

function getFormData(form) {
    let data = {};

    $.each($(form).find(':input'), (index, element) => {

        if (simpleTypes.includes(element.type) || element.tagName.toLowerCase() === 'textarea') data[element.name] = element.value;
        else if(element.type === 'datetime-local') data[element.name] = element.value.replace('T', ' ');
        else if (element.type === 'checkbox') data[element.name] = element.checked;
        else if (element.type === 'radio' && element.checked) data[element.name] = element.value;
        else if (element.tagName.toLowerCase() === 'select') data[element.name] = element.options[element.selectedIndex].value;

    });

    return data;
}

function setFormData(form, data) {
    $.each(data, (index, value) => {

        $.each($(form).find(':input[name=' + index + ']'), (index2, element) => {

            if (element.type === 'radio') element.checked = (element.value === value);
            else if (element.type === 'checkbox') element.checked = value;
            else if(element.type === 'datetime-local') {
                element.value = value.replace(' ', 'T');
                element.min = value.replace(' ', 'T');
            }
            else if (simpleTypes.includes(element.type) || element.tagName.toLowerCase() === 'textarea' || element.tagName.toLowerCase() === 'select') element.value = value;

        });
    });
}