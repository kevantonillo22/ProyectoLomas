function deshabilitarControles(listaControles){
	listaControles.forEach(function(selector){
		$(selector).prop("disabled","disabled");
	});
}

function habilitarControles(listaControles){
	listaControles.forEach(function(selector){
		$(selector).prop("disabled","");
	});
}