/**
* Given a rectangle whose breadth is 3024 pixels
* Look for these: y-(height/2) < 2880 => py
* finaly=py/45
* finalx=x/45
*0-378 is square 1, 379 is square 2
*/
var values=[
[465,1806,560,1500],// Pic5
[2433,1398,1100,2450]
]
localStorage.setItem('PrevCell','5;5');
function trial1()
{
  $('#inputimg').attr('src','Pic5.JPG');
  function renderBounded()
  {
     $('#boundedimg').attr('src','Pic5Bounded.JPG')
  }
  window.setTimeout(renderBounded, 2000);
  hypermapper(465,1806,560,1500)

}
function trial2()
{
  $('#inputimg').attr('src','Pic3.JPG');
  function renderBounded()
  {
     $('#boundedimg').attr('src','Pic3Bounded.JPG')
  }
  window.setTimeout(renderBounded, 2000);
  
  hypermapper(2433,1398,1100,2450)

}
function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                
                reader.onload = function (e) {
                    $('#inputimg')
                        .attr('src', e.target.result);
                };
              
              

                reader.readAsDataURL(input.files[0]);
            }
        }
        function readURL2(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                
                reader.onload = function (e) {
                    $('#boundedimg')
                        .attr('src', e.target.result);
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
function hypermapper(x,y,w,h)
{
//0<x,y,<4032
document.getElementById('title').innerHTML="HyperBraille Mapper"
prev=localStorage.getItem('PrevCell');
var finalx=x;
var width=w;
var height=h;
var table=document.getElementById('mytable');
table.rows[prev.split(';')[1]].cells[prev.split(';')[0]].innerHTML=0;
table.rows[prev.split(';')[1]].cells[prev.split(';')[0]].style.backgroundColor="white";
//var height=document.getElementById('height').value;
//var width=document.getElementById('width').value;
//var finalx=document.getElementById('x').value;
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
/*const lib = require('lib');

lib.username.bestTrekChar['@0.2.1']({name: 'spock'}, function (err, result) {

  if (err) {
    // handle it
  }

  // do something with result

});*/