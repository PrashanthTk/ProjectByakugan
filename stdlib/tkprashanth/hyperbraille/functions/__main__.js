const request = require("request");
/**
* Given a rectangle whose breadth is 3024 pixels
* Look for these: y-(height/2) < 2880 => py
* finaly=py/45
* finalx=x/45
*
*/
//localStorage.setItem('PrevCell','5;5');
function rendermap(x,y,width,height)
{
  var table=document.getElementById('mytable');
  var finalx=x;
  var width=w;
  var height=h;
  if (finalx>3024)
{
  finalx-=1008;
}
  
//var y=document.getElementById('y').value;
var py=y-(height/2);
if (py<3024)
{
  console.log(py)
  finaly=Math.floor(py/378);
  finalx=Math.floor(finalx/378);
}
else
{
  finalx=4;
  finaly=0;
}
var row=table.rows[8-finaly];
row.cells[finalx].innerHTML=1;
row.cells[finalx].style.backgroundColor="blue";
localStorage.setItem('PrevCell', finalx+';'+(8-finaly));
}

module.exports = async (x=0,y=0,width=0,height=0,context) => {

  return `Position is ${width}`;
};

