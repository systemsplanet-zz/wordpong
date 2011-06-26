//JS DEBUG TIPS http://msdn.microsoft.com/en-us/scriptjunkie/ee819093
//console.log(this.name);
//console.group("TEST") // group in output window
//console.dir(this)     //dump object this
//console.trace()       // dump stact trace
//console.groupEnd();
//debugger 
//DEBUG: add 'this' to watch window to see who caused the event
//console.time('find content');  console.timeEnd('find content');
//console.profile();  console.profileEnd();

// Add array.shuffle() method to randomize the contents of an array
Array.prototype.shuffle = function() {
var s = [];
while (this.length) s.push(this.splice(Math.random() * this.length, 1)[0]);
while (s.length) this.push(s.pop());
return this;
}

$.ajaxSetup(
	{	cache:false,
		error:function(x,e){
     		$.mobile.pageLoading(true);//close the wait icon
			if(x.status==0){
				alert('Network unavailable.');
			}else if(x.status==403){
				alert('Login expired.');
			}else if(x.status==404){
				alert('Requested URL not found.');
			}else if(x.status==500){
				alert('Internel Server Error.');
			}else if(x.status==503){
				alert('Service Unavailable.');
				window.location.href = "/err/infrastructure_unavailable.html"
			}else if(e=='parsererror'){
				alert('Error.\nParsing JSON Request failed.');
			}else if(e=='timeout'){
				alert('Request Time out.');
			}else {
				alert('Unknow Error.\n'+x.responseText);
			}
			if (x.status!=503) {
				window.location.reload(true);
			}
		}
	}
);
 
$(document).ajaxSuccess(function(e, xhr, settings, exception) {
    $('.wp-status').text('On Line').removeClass('down').addClass('up');
});

$(document).ajaxError(function(e, xhr, settings, exception) {
    $('.wp-status').text('Off Line').removeClass('up').addClass('down');
});
