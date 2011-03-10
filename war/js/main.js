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

$.ajaxSetup({cache:false});
 
$(document).ajaxSuccess(function(e, xhr, settings, exception) {
    $('#js-status').text('On Line').removeClass('down').addClass('up');
});

$(document).ajaxError(function(e, xhr, settings, exception) {
    $('#js-status').text('Off Line').removeClass('up').addClass('down');
});
