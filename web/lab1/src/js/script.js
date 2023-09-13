function createArrayFromRange(x, z, step = 1) {
    let result = [];

    for (let i = x; i <= z; i += step) {
        result.push(i);
    }

    return result;
}

function isFloat(val) {
    let floatRegex = /^-?\d+(?:[.,]\d*?)?$/;
    if (!floatRegex.test(val)) {
        return false;
    }

    if (isNaN(parseFloat(val))) {
        return false;
    }

    return true;
}

function validateX() {
    const ERR_MESSAGE = "Выберите корректное значение координаты X.";
    let raw = document.getElementById("x").value;

    if (!isFloat(raw)) {
        alert(ERR_MESSAGE);
        return false;
    }

    let x = parseFloat(raw);

    if (!createArrayFromRange(-5, 3).includes(x)) {
        alert(ERR_MESSAGE);
        return false;
    }

    return true;
}

function validateY() {
    const ERR_MESSAGE = "Введите корректное значение координаты Y: (-5; 3).";
    let raw = document.getElementById("y").value;

    if (!isFloat(raw)) {
        alert(ERR_MESSAGE);
        return false;
    }

    let y = parseFloat(raw);

    if (y < -5 || y > 3) {
        alert(ERR_MESSAGE);
        return false;
    }

    return true;
}

function validateR() {
    const ERR_MESSAGE = "Выберите корректное значение радиуса R.";
    let raw = document.getElementById("r").value;

    if (!isFloat(raw)) {
        alert(ERR_MESSAGE);
        return false;
    }

    let r = parseFloat(raw);

    if (!createArrayFromRange(1, 3, 0.5).includes(r)) {
        alert(ERR_MESSAGE);
        return false;
    }

    return true;
}

function validateForm() {
    if (validateX() && validateY() && validateR()) {
        return true;
    }

    return false;
}

function validateAndSend() {
    if (!validateForm()) {
        return false;
    }

    let formData = new FormData(document.querySelector('form'));

    fetch(
        'php/check_point.php?' + new URLSearchParams(formData),
        {
            method: 'GET'
        },
    )
    .then(response => response.text())
    .then(data => {
        document.getElementById('results').innerHTML = data;
    })
    .catch(error => {
        console.error('Ошибка при отправке данных на сервер: ', error);
    });

    // Предотвращаем отправку формы через обычный HTTP-запрос
    return false;
}
