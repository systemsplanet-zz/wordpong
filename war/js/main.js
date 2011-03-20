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

/*
,
		error:function(x,e){
			if(x.status==0){
			alert('Network unavailable.');
			}else if(x.status==403){
			alert('access is denied.');
			}else if(x.status==404){
			alert('Requested URL not found.');
			}else if(x.status==500){
			alert('Internel Server Error.');
			}else if(e=='parsererror'){
			alert('Error.\nParsing JSON Request failed.');
			}else if(e=='timeout'){
			alert('Request Time out.');
			}else {
			alert('Unknow Error.\n'+x.responseText);
			}
	
*/
$.ajaxSetup(
	{	cache:false,
		error:function(x,e){
			if(x.status==0){
				alert('Network unavailable.');
			}else if(x.status==403){
				alert('access is denied.');
			}else if(x.status==404){
				alert('Requested URL not found.');
			}else if(x.status==500){
				alert('Internel Server Error.');
			}else if(e=='parsererror'){
				alert('Error.\nParsing JSON Request failed.');
			}else if(e=='timeout'){
				alert('Request Time out.');
			}else {
				alert('Unknow Error.\n'+x.responseText);
			}
		}
	}
);
 
$(document).ajaxSuccess(function(e, xhr, settings, exception) {
    $('#js-status').text('On Line').removeClass('down').addClass('up');
});

$(document).ajaxError(function(e, xhr, settings, exception) {
    $('#js-status').text('Off Line').removeClass('up').addClass('down');
});
