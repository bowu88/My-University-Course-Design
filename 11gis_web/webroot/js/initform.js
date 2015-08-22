function DateSelector(selYear, selMonth, selDay) 
{ 
  this.selYear=selYear; 
  this.selMonth=selMonth; 
  this.selDay=selDay; 
  this.selYear.Group=this; 
  this.selMonth.Group=this; 
  if (window.document.all!=null)
  { 
    this.selYear.attachEvent("onchange",DateSelector.Onchange); 
    this.selMonth.attachEvent("onchange",DateSelector.Onchange); 
  } 
  else
  { 
    this.selYear.addEventListener("change",DateSelector.Onchange,false); 
    this.selMonth.addEventListener("change",DateSelector.Onchange,false); 
  } 
  if(arguments.length==4)
  {
     this.InitSelector(arguments[3].getFullYear(),arguments[3].getMonth()+1,arguments[3].getDate()); 
  } 
  else if(arguments.length == 6)
  {
     this.InitSelector(arguments[3],arguments[4],arguments[5]); 
  } 
  else 
  { 
    var dt = new Date(); 
    this.InitSelector(dt.getFullYear(),dt.getMonth()+1,dt.getDate()); 
  } 
} 
DateSelector.prototype.MinYear=1900; 
DateSelector.prototype.MaxYear=(new Date()).getFullYear(); 
DateSelector.prototype.InitYearSelect=function () 
{ 
  for(var i=this.MaxYear;i>=this.MinYear;i--) 
  { 
    var op=window.document.createElement("option"); 
    op.value=i; 
    op.innerHTML=i; 
    this.selYear.appendChild(op); 
  } 
} 
DateSelector.prototype.InitMonthSelect=function() 
{ 
  for (var i=1;i<13;i++) 
  { 
    var op = window.document.createElement("OPTION");
    op.value = i; 
    op.innerHTML = i; 
    this.selMonth.appendChild(op); 
  } 
} 
DateSelector.DaysInMonth = function (year, month) 
{ 
  var date=new Date(year,month,0); 
  return date.getDate(); 
}
DateSelector.prototype.InitDaySelect=function() 
{ 
  var year = parseInt(this.selYear.value); 
  var month = parseInt(this.selMonth.value); 
  var daysInMonth=DateSelector.DaysInMonth(year,month); 
  this.selDay.options.length=0; 
  for (var i=1;i<=daysInMonth;i++) 
  { 
    var op = window.document.createElement("option"); 
    op.value = i; 
    op.innerHTML = i; 
    this.selDay.appendChild(op); 
  } 
} 
DateSelector.Onchange=function(e) 
{ 
  var selector=window.document.all!=null?e.srcElement:e.target; 
  selector.Group.InitDaySelect(); 
} 
DateSelector.prototype.InitSelector=function (year,month,day) 
{ 
  this.selYear.options.length = 0; 
  this.selMonth.options.length = 0; 
  this.InitYearSelect(); 
  this.InitMonthSelect(); 
  this.selYear.selectedIndex = this.MaxYear - year; 
  this.selMonth.selectedIndex = month - 1; 
  this.InitDaySelect(); 
  this.selDay.selectedIndex = day - 1; 
} 
function pageInit(birthday)
{
	if(birthday=="")
		birthday="1990-1-1";
	var str=birthday.split("-");
	var year=str[0],month=str[1],day=str[2];
  var selYear = window.document.getElementById("selYear"); 
  var selMonth = window.document.getElementById("selMonth"); 
  var selDay = window.document.getElementById("selDay"); 
  new DateSelector(selYear,selMonth,selDay,year,month,day);
}
function dosubmit(){
	makeBirthday();
	var btn=document.getElementById("submit");
	btn.disabled="disabled";
	return true;
}
function makeBirthday(){
	var year=document.getElementById("selYear").value;
	var month=document.getElementById("selMonth").value;
	var day=document.getElementById("selDay").value;
	if(month<10)
		month="0"+month;
	if(day<10)
		day="0"+day;
	var birthday=year+"-"+month+"-"+day;
	
	var input=document.createElement("input");
	input.type="hidden";
	input.name="birthday";
	input.value=birthday;
	
	document.getElementById("sampleform").appendChild(input);
}
function makeDirection(){
	var directions=document.getElementsByName=("pre");
	var string="";
	for(var i=0;i<directions.length;i++){
		if(directions[i].checked==true){
			alert(directions[i].value);
		}
	}
	alert(directions.length);
	
}