const $=id=>document.getElementById(id);
function localDate(){const d=new Date();return new Date(d.getFullYear(),d.getMonth(),d.getDate())}
function daysInMonth(y,m){return new Date(y,m,0).getDate()}
function show(msg){$('message').textContent=msg;$('result').hidden=true}
function calculate(){
 const d=+$('day').value,m=+$('month').value,y=+$('year').value;
 const birth=new Date(y,m-1,d),today=localDate();
 if(!d||!m||!y||m<1||m>12||d<1||d>daysInMonth(y,m)||birth>today){show('সঠিক জন্মতারিখ দিন।');return}
 let years=today.getFullYear()-y, months=today.getMonth()-(m-1), days=today.getDate()-d;
 if(days<0){months--;const prev=new Date(today.getFullYear(),today.getMonth(),0);days+=prev.getDate()}
 if(months<0){years--;months+=12}
 const total=Math.floor((today-birth)/86400000);
 $('years').textContent=years;$('months').textContent=months;$('days').textContent=days;$('totalDays').textContent=total;
 $('message').textContent='';$('result').hidden=false;
}
$('calculate').addEventListener('click',calculate);
$('today').addEventListener('click',()=>{const d=localDate();$('day').value=d.getDate();$('month').value=d.getMonth()+1;$('year').value=d.getFullYear()});
if('serviceWorker' in navigator) window.addEventListener('load',()=>navigator.serviceWorker.register('sw.js').catch(()=>{}));
