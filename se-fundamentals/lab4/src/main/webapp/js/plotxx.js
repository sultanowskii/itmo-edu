class Point {
  constructor(x, y, r, hit) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.hit = hit;
  }

  _calculateCoordOnPlot(coord, plotR) {
    return (coord / this.r) * plotR;
  }

  getCoordsOnPlot(plotR) {
    return [
      this._calculateCoordOnPlot(this.x, plotR),
      this._calculateCoordOnPlot(this.y, plotR),
    ];
  }
}

class Plot {

  _r = 1;

  _width = 400;

  _height = 400;

  _cursorPosition = {
    x: null,
    y: null,
  };

  _points = [];

  _chart = null;

  _colors = {
    area: '#5E81AC',
    missPoint: '#BF616A',
    hitPoint: '#A3BE8C',
  };

  _pointSizePx = 10;

  _xValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5];

  constructor(r, points) {
    this._r = r;
    this._points = points;
    this._draw();

    document
      .getElementById("plot")
      .addEventListener("click", () => this._updateForm());
  }

  set r(value) {
    this._r = value;
    this._draw();
  }

  get _options() {
    const r = this._r;
    return {
      target: "#plot",
      width: this._width,
      height: this._height,
      xAxis: { domain: [-3, 3] },
      yAxis: { domain: [-3, 3] },
      grid: true,
      data: [
        {
          fn: `-x + ${r}/2`,
          closed: true,
          skipTip: true,
          range: [0, r/2],
          color: this._colors.area,
        },
        {
          fn: `${r}`,
          closed: true,
          skipTip: true,
          range: [-r, 0],
          color: this._colors.area,
        },
        {
          fn: `-sqrt(${r}^2 - x^2)`,
          closed: true,
          skipTip: true,
          range: [0, r],
          color: this._colors.area,
        },
        this._getPointsOptions('hit'),
        this._getPointsOptions('miss'),
      ],
    };
  }

  _convertCoords(coords) {
    let { x, y } = coords;

    const lastBound = this._xValues[this._xValues.length - 1];
    if (x > lastBound) {
      x = lastBound
    } else {
      for (const coordBound of this._xValues) {
        if (x <= coordBound) {
          x = coordBound;
          break;
        }
      }
    }

    this._cursorPosition = { x, y };
  }

  _getPointsOptions(type) {
    let points, color;
    if (type === 'hit') {
      points = this._points.filter(point => point.hit);
      color = this._colors.hitPoint
    } else {
      points = this._points.filter(point => !point.hit);
      color = this._colors.missPoint;
    }

    return {
      points: points.map(point => point.getCoordsOnPlot(this._r)),
      fnType: "points",
      graphType: "scatter",
      color: color,
      attr: {
        "stroke-width": `${this._pointSizePx}px`,
      },
    };
  }

  _draw() {
    this._chart = functionPlot(this._options);

    this._chart
      .on("mousemove", (coords) => this._convertCoords(coords));
  }

  _updateForm() {
    document.getElementsByName('coords-form:x-coord-input').forEach(option => option.removeAttribute('checked'));
    document.getElementsByName('coords-form:x-coord-input').forEach(option => {
      if (option.value.toString() === this._cursorPosition.x.toString()) {
        option.setAttribute('checked', 'checked');
      }
    });
    document.getElementById('coords-form:y-coord-input').value = this._cursorPosition.y;
  }

  destroy() {
    document.getElementsByClassName('function-plot')[0].remove();
  }
}

const points = [];

const addPoint = (x, y, r, hit) => {
  points.push(new Point(x, y, r, hit));
}
