function timestampToLocalDatetime(timestamp) {
    return new Date(timestamp * 1000).toLocaleString();
}

function setResultTable() {
    let timestampElements = document.getElementsByClassName("timestamp");
    
    for (let item of timestampElements) {
        let timestamp = parseInt(item.innerHTML);
        item.innerHTML = timestampToLocalDatetime(timestamp);
    }
}

window.onload = setResultTable;
