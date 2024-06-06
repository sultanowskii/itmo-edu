
let r = 1;

const switchDisabledRButton = () => {
  document.getElementById('r-coord-input').childNodes.forEach(rButton => {
    rButton.disabled = rButton.value === r.toString();
  });
}

const getTableValues = (tableRows) => {
  return [...tableRows].map(row => row.innerText);
}

const init = () => {
  const xInTable = getTableValues(document.getElementsByClassName('table-x'));
  const yInTable = getTableValues(document.getElementsByClassName('table-y'));
  const rInTable = getTableValues(document.getElementsByClassName('table-r'));
  const hitInTable = getTableValues(document.getElementsByClassName('table-hit'));
  const createdAtInTable = document.getElementsByClassName('table-created-at');

  for (let i = 0; i < xInTable.length; i++) {
    addPoint(xInTable[i], yInTable[i], rInTable[i], hitInTable[i] === 'true');
    const createdAtTimestamp = Number(createdAtInTable[i].innerText);
    const date = new Date(createdAtTimestamp);
    createdAtInTable[i].innerText = date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  }

  const plot = new Plot(r, points);
  document.getElementById('coords-form:r-coord-input-real').value = r;
  switchDisabledRButton();

  document.getElementById('r-coord-input').childNodes.forEach(rButton => {
    rButton.addEventListener('click', (event) => {
      event.preventDefault();
      r = event.target.value;
      switchDisabledRButton();
      plot.r = r;
      document.getElementById('coords-form:r-coord-input-real').value = r;
    });
  });
}

window.onload = () => {
  init();
}
