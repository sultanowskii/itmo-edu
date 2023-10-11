function timestampToLocalDatetime(timestamp) {
    return new Date(timestamp * 1000).toLocaleString();
}

const OUTSIDE_WHITESPACE_RATIO = 0.1;
const canvasStyling = {
    colors: {
        axis: "#0f92be",
        shapes: "#0f92be",
        hit: "#388e3c",
        miss: "#c62828",
    },
    lineWidths: {
        grid: 1,
        axis: 2,
        point: 1,
    },
    pointLineDashes: [5, 5],
    shapesAlpha: 0.6,
    gridAlpha: 0.1,
};
const canvasElement = document.getElementById("areacheck-canvas");
const canvasRError = document.getElementById("canvas-r-error");
let previousPoints = [];

function getRValue() {
    var radioButtons = document.getElementsByName('r');

    for (var i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            return radioButtons[i].value;
        }
    }
}

function addEntryToTable(data) {
    let tableElement = document.getElementById("result-table");

    let datetime = timestampToLocalDatetime(parseInt(data.timestamp));

    let html = `<tr>
                    <td>${data.x}</td>
                    <td>${data.y}</td>
                    <td>${data.r}</td>
                    <td>${data.isInside}</td>
                    <td class="timestamp">${datetime}</td>
                </tr>`;

    tableElement.insertAdjacentHTML("beforeend", html);
}

canvasElement.addEventListener("click", async (event) => {
    const r = getRValue();
    if (!r) {
        canvasRError.textContent = "R is required";
        return;
    }
    canvasRError.textContent = "";
    const target = /** @type {HTMLCanvasElement} */ (event.currentTarget);
    const rect = target.getBoundingClientRect();

    const ratioX = (2 * (event.clientX - rect.left)) / rect.width - 1;
    const ratioY = -1 * ((2 * (event.clientY - rect.top)) / rect.height - 1);
    const ratioXRelativeToR = ratioX / (1 - 2 * OUTSIDE_WHITESPACE_RATIO);
    const ratioYRelativeToR = ratioY / (1 - 2 * OUTSIDE_WHITESPACE_RATIO);
    const x = ratioXRelativeToR * r;
    const y = ratioYRelativeToR * r;

    const formData = new FormData();
    formData.append("x", String(Math.round(x)));
    formData.append("y", String(y));
    formData.append("r", String(r));
    formData.append("responseType", "json");

    fetch("controller?" + new URLSearchParams(formData), { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            previousPoints.push(data);
            drawCanvas(canvasElement, data.r, previousPoints);
            addEntryToTable(data);
        })
        .catch(error => {
            console.error('Ошибка при отправке данных на сервер: ', error);
        });
});

function drawCanvas(canvas, r, points) {
    const ctx = canvas.getContext("2d");
    if (ctx == null) return;
    const width = canvas.width;
    const height = canvas.height;
    ctx.clearRect(0, 0, width, height);

    // Background grid
    ctx.globalAlpha = canvasStyling.gridAlpha;
    ctx.strokeStyle = canvasStyling.colors.axis;
    ctx.lineWidth = canvasStyling.lineWidths.grid;
    ctx.beginPath();
    const gridCells = 6;
    for (let i = 1; i < gridCells; i++) {
        ctx.moveTo(0, (width * i) / gridCells);
        ctx.lineTo(height, (width * i) / gridCells);
        ctx.moveTo((height * i) / gridCells, 0);
        ctx.lineTo((height * i) / gridCells, width);
    }
    ctx.stroke();
    ctx.closePath();

    const translateX = width * OUTSIDE_WHITESPACE_RATIO;
    const translateY = height * OUTSIDE_WHITESPACE_RATIO;
    const scale = 1 - 2 * OUTSIDE_WHITESPACE_RATIO;
    ctx.translate(translateX, translateY);
    ctx.scale(scale, scale);

    ctx.globalAlpha = canvasStyling.shapesAlpha;
    ctx.fillStyle = canvasStyling.colors.shapes;

    // Top-left square
    ctx.fillRect(0, height / 2, width / 2, height / 4);

    // Top-right arc
    ctx.beginPath();
    ctx.moveTo(width / 2, height / 2);
    ctx.arc(width / 2, height / 2, width / 2, 0, -Math.PI / 2, true);
    ctx.closePath();
    ctx.fill();

    // R value label
    ctx.globalAlpha = 1;
    const fontSize = (height / 15) * scale;
    ctx.font = `${fontSize}px sans-serif`;
    ctx.fillText(r ? String(r) : "R", width, fontSize + height / 2);
    ctx.globalAlpha = canvasStyling.shapesAlpha;

    // Bottom-right triangle
    ctx.beginPath();
    ctx.moveTo(width / 2, height / 2);
    ctx.lineTo(width / 2, (height * 1) / 4);
    ctx.lineTo((width * 1) / 4, height / 2);
    ctx.closePath();
    ctx.fill();

    // X and Y axis
    ctx.resetTransform();

    ctx.globalAlpha = 1;
    ctx.strokeStyle = canvasStyling.colors.axis;
    ctx.lineWidth = canvasStyling.lineWidths.axis;
    ctx.beginPath();
    ctx.moveTo(0, height / 2);
    ctx.lineTo(width, height / 2);
    ctx.moveTo(width / 2, 0);
    ctx.lineTo(width / 2, height);
    ctx.stroke();
    ctx.closePath();

    if (r) {
        for (let point of points) {
            const { x, y, r, isInside, timestamp } = point;
            const pointWidth = 10;
            ctx.fillStyle = isInside
                ? "green"
                : "red";
            ctx.strokeStyle = ctx.fillStyle;
            ctx.setLineDash(canvasStyling.pointLineDashes);
            ctx.lineWidth = canvasStyling.lineWidths.point;
            const dotX =
                ((x / r) * width * (1 - 2 * OUTSIDE_WHITESPACE_RATIO)) / 2 +
                width / 2;
            const dotY =
                ((-y / r) * height * (1 - 2 * OUTSIDE_WHITESPACE_RATIO)) / 2 +
                height / 2;
            ctx.fillRect(
                dotX - pointWidth / 2,
                dotY - pointWidth / 2,
                pointWidth,
                pointWidth,
            );
            ctx.beginPath();
            ctx.moveTo(dotX, 0);
            ctx.lineTo(dotX, height);
            ctx.moveTo(0, dotY);
            ctx.lineTo(width, dotY);
            ctx.stroke();
            ctx.closePath();
        }
    }

    ctx.setLineDash([]);
    ctx.resetTransform();
}

drawCanvas(canvasElement, null, null);