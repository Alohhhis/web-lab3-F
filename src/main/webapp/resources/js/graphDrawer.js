const canvas = document.querySelector("#canvas");
const ctx = canvas.getContext("2d");
const side = canvas.width;
const xAxisLabel = "X";
const yAxisLabel = "Y";

let Rvalue;
let xAxisScale;
let yAxisScale;
const k = 5;

function draw() {
    if (canvas.getContext) {
        ctx.fillStyle = "rgb(0,0,0)";

        let canvasWidth = canvas.width;
        let canvasHeight = canvas.height;

        xAxisScale = canvasWidth / 8;
        yAxisScale = canvasHeight / 8;

        let originX = canvasWidth / 2;
        let originY = canvasHeight / 2;

        ctx.beginPath();
        ctx.moveTo(0, originY);
        ctx.lineTo(canvasWidth, originY);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(originX, 0);
        ctx.lineTo(originX, canvasHeight);
        ctx.stroke();

        ctx.font = '14px Arial';
        ctx.fillText(xAxisLabel, canvas.width - 15, canvas.height / 2 - 5);
        ctx.fillText(yAxisLabel, canvas.width / 2 + 5, 15);

        for (let i = -canvas.width / 2; i < canvas.width / 2; i += xAxisScale) {
            let scalePos = axesToCanvasCoordinates(i, 0, canvas);
            ctx.beginPath();
            ctx.moveTo(scalePos.x, scalePos.y - 5);
            ctx.lineTo(scalePos.x, scalePos.y + 5);
            ctx.stroke();
            ctx.fillText(rescaleXAxesCoordinate(i), scalePos.x - 10, scalePos.y + 20);
        }

        for (let j = -canvas.height / 2; j < canvas.height / 2; j += yAxisScale) {
            let scalePos = axesToCanvasCoordinates(0, j, canvas);
            ctx.beginPath();
            ctx.moveTo(scalePos.x - 5, scalePos.y);
            ctx.lineTo(scalePos.x + 5, scalePos.y);
            ctx.stroke();
            ctx.fillText(rescaleYAxesCoordinate(j), scalePos.x + 10, scalePos.y + 5);
        }

    }
}

function axesToCanvasCoordinates(xAxes, yAxes, canvas) {

    let originX = canvas.width / 2;
    let originY = canvas.height / 2;


    let canvasX = originX + xAxes;
    let canvasY = originY - yAxes;

    return {x: canvasX, y: canvasY};
}

function rescaleXAxesCoordinate(coordinate) {
    return coordinate / xAxisScale;
}

function rescaleYAxesCoordinate(coordinate) {
    return coordinate / yAxisScale;
}

function scaleXAxesCoordinate(coordinate) {
    return coordinate * xAxisScale;
}

function scaleYAxesCoordinate(coordinate) {
    return coordinate * yAxisScale;
}

function drawShapesByR(r) {
    r = parseFloat(r.replace(",", "."));
    console.log(r, typeof r);
    if (canvas.getContext) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        draw();

        // draw square
        let startPointInAxes = {x: 0, y: 0};
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);

        let endPointInAxes = {x: r / 2, y: -r};
        let endScaledPointInAxes = {
            x: scaleXAxesCoordinate(endPointInAxes.x),
            y: scaleYAxesCoordinate(endPointInAxes.y)
        };

        ctx.fillStyle = "#00b4d8";

        ctx.beginPath();
        ctx.fillRect(startPointInCanvas.x, startPointInCanvas.y, endScaledPointInAxes.x, -endScaledPointInAxes.y);

        // draw triangle
        let secondTrianglePointInAxes = {x: r, y: 0};
        let thirdTrianglePointInAxes = {x: 0, y: r / 2};
        ctx.fillStyle = "#00b4d8";
        drawTriangle(ctx, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes);

        // draw 1/4 circle
        ctx.fillStyle = "#00b4d8";
        let calculatedRadius = scaleXAxesCoordinate(r);
        let mirrorStartAngle = 3 * Math.PI / 2;
        let mirrorEndAngle = Math.PI;

        ctx.beginPath();
        ctx.arc(startPointInCanvas.x, startPointInCanvas.y, calculatedRadius, mirrorStartAngle, mirrorEndAngle, true);
        ctx.closePath();
        ctx.fill();

        // draw missing triangle
        let secondTrianglePointInAxesM = {x: -r, y: 0};
        let thirdTrianglePointInAxesM = {x: 0, y: r};
        drawTriangle(ctx, startPointInAxes, secondTrianglePointInAxesM, thirdTrianglePointInAxesM);
        drawDots(ctx, r);
    }
}

function drawTriangle(ctx, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes) {
    if (canvas.getContext) {
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);
        let secondScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(secondTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(secondTrianglePointInAxes.y)
        }
        let thirdScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(thirdTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(thirdTrianglePointInAxes.y)
        };
        let secondTrianglePointInCanvas = axesToCanvasCoordinates
        (secondScaledTrianglePointInAxes.x, secondScaledTrianglePointInAxes.y, canvas);
        let thirdScaledTrianglePointInCanvas = axesToCanvasCoordinates
        (thirdScaledTrianglePointInAxes.x, thirdScaledTrianglePointInAxes.y, canvas);

        ctx.beginPath();
        ctx.moveTo(startPointInCanvas.x, startPointInCanvas.y);
        ctx.lineTo(secondTrianglePointInCanvas.x, secondTrianglePointInCanvas.y);
        ctx.lineTo(thirdScaledTrianglePointInCanvas.x, thirdScaledTrianglePointInCanvas.y);
        ctx.fill();
    }
}

canvas.addEventListener("click", handleCanvasClick);

function handleCanvasClick(event) {
    const rect = canvas.getBoundingClientRect();
    const xCanvas = event.clientX - rect.left;
    const yCanvas = event.clientY - rect.top;

    const xAxes = (xCanvas - canvas.width / 2) / xAxisScale;
    const yAxes = -(yCanvas - canvas.height / 2) / yAxisScale;

    console.log(`Кликнуто на точку (${xAxes}, ${yAxes})`);
    let tempR = parseFloat(document.getElementById('valForm:r_input').value.replace(",", "."));
    console.log(tempR, typeof tempR);
    if (!tempR) {
        alert("R none");
        return;
    }

    const r = tempR;
    drawPoint(xAxes, yAxes, tempR);
    draw();
    try {
        clickSender([
            {name: 'x', value: xAxes},
            {name: 'y', value: yAxes},
            {name: 'r', value: tempR}
        ])
        console.log("send");
    } catch (e) {
        console.log("wtf");
    }

}

function check(x, y, r) {
    //квадрат
    if (x >= 0 && y <= 0 && x <= r / 2 && y >= -r) {
        return true;
    } else
        //треугольник
    if (x >= 0 && y >= 0 && x <= r  && y <= -1/2 * x + r/2) {
        return true;
    } else
        //четверть круга
    if (x <= 0 && y >= 0 && ((x * x + y * y) <= r * r)) return true;
    else return false;
}

function drawPoint(x, y, r) {
    let scaledPoint = {x: scaleXAxesCoordinate(x), y: scaleYAxesCoordinate(y)};
    let pointOnCanvas = axesToCanvasCoordinates(scaledPoint.x, scaledPoint.y, canvas);
    let res = check(x, y, r);
    if (res) {
        color = "red";
    } else color = "white";
    ctx.beginPath();
    ctx.arc(pointOnCanvas.x, pointOnCanvas.y, 4, 0, Math.PI * 2);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.stroke();
    ctx.closePath();
}

function drawDots(ctx, r) {

    let table = document.getElementById("resultTable");

    for (let i = 1; i < table.rows.length; i++) {
        let tableR = table.rows[i].cells[2].innerText;
        if (parseFloat(tableR) == r) {
            console.log("r = const");
            let tableX = table.rows[i].cells[0].innerText;
            let tableY = table.rows[i].cells[1].innerText;
            let tableWasHit = table.rows[i].cells[3].innerText;
            let color;
            if (tableX !== "" && tableY !== "") {
                let scaledPoint = {x: scaleXAxesCoordinate(tableX), y: scaleYAxesCoordinate(tableY)};
                let pointOnCanvas = axesToCanvasCoordinates(scaledPoint.x, scaledPoint.y, canvas);
                if (tableWasHit === "true") {
                    color = "red";
                } else color = "white";
                ctx.beginPath();
                ctx.arc(pointOnCanvas.x, pointOnCanvas.y, 4, 0, Math.PI * 2);
                ctx.fillStyle = color;
                ctx.fill();
                ctx.stroke();
                ctx.closePath();
            }
        }
    }
    draw();
}

draw();