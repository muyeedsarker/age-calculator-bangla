const $ = (id) => document.getElementById(id);

function todayDate() {
  const now = new Date();
  return { year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate() };
}

function daysInMonth(year, month) {
  return new Date(Date.UTC(year, month, 0)).getUTCDate();
}

function toUTCDate(year, month, day) {
  return new Date(Date.UTC(year, month - 1, day));
}

function showMessage(message) {
  $('message').textContent = message;
  $('result').hidden = true;
}

function calculate() {
  const day = Number.parseInt($('day').value, 10);
  const month = Number.parseInt($('month').value, 10);
  const year = Number.parseInt($('year').value, 10);
  const today = todayDate();

  const validNumbers = Number.isInteger(day) && Number.isInteger(month) && Number.isInteger(year);
  const validDate = validNumbers && year >= 1 && year <= 9999 && month >= 1 && month <= 12 && day >= 1 && day <= daysInMonth(year, month);

  if (!validDate) {
    showMessage('সঠিক জন্মতারিখ দিন।');
    return;
  }

  const birth = toUTCDate(year, month, day);
  const current = toUTCDate(today.year, today.month, today.day);
  if (birth > current) {
    showMessage('জন্মতারিখ ভবিষ্যতের হতে পারবে না।');
    return;
  }

  let years = today.year - year;
  let months = today.month - month;
  let days = today.day - day;

  if (days < 0) {
    months -= 1;
    const previousMonth = today.month === 1 ? 12 : today.month - 1;
    const previousYear = today.month === 1 ? today.year - 1 : today.year;
    days += daysInMonth(previousYear, previousMonth);
  }

  if (months < 0) {
    years -= 1;
    months += 12;
  }

  const totalDays = Math.round((current - birth) / 86400000);
  $('years').textContent = years;
  $('months').textContent = months;
  $('days').textContent = days;
  $('totalDays').textContent = totalDays.toLocaleString('bn-BD');
  $('message').textContent = '';
  $('result').hidden = false;
}

function fillToday() {
  const today = todayDate();
  $('day').value = today.day;
  $('month').value = today.month;
  $('year').value = today.year;
  $('message').textContent = '';
  $('result').hidden = true;
}

$('calculate').addEventListener('click', calculate);
$('today').addEventListener('click', fillToday);
[$('day'), $('month'), $('year')].forEach((input) => input.addEventListener('keydown', (event) => {
  if (event.key === 'Enter') calculate();
}));
