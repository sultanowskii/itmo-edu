const updateDatetime = () => {
    fetch(
        'http://worldtimeapi.org/api/timezone/Europe/Moscow'
    )
        .then((response) => response.json())
        .then((data) => {
            const date = new Date(data.datetime);
            document.getElementById('day').innerText = formatDatetimeElement(date.getDate());
            document.getElementById('month').innerText = formatDatetimeElement(date.getMonth() + 1);
            document.getElementById('year').innerText = formatDatetimeElement(date.getFullYear());
            document.getElementById('hours').innerText = formatDatetimeElement(date.getHours());
            document.getElementById('minutes').innerText = formatDatetimeElement(date.getMinutes());
            document.getElementById('seconds').innerText = formatDatetimeElement(date.getSeconds());
        });
}

const formatDatetimeElement = (value) => {
    const result = value.toString();
    return result.length === 1 ? '0' + result : result;
}

window.onload = () => {
    updateDatetime();
    setInterval(updateDatetime, 13000);
};