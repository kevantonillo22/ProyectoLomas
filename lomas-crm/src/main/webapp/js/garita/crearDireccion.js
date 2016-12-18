var app = angular.module('crearDireccion', ['ngSanitize']);

app.controller('crear', function($scope,$http) {
	$scope.values = [
        {id: '1', name: 'Av.'},
        {id: '2', name: 'Calle'}
    ];
	$scope.estadosPago = [
          {id: '1', name: 'Paga'},
          {id: '2', name: 'No paga'}
     ];
 	$scope.estadosDomicilio = [
          {id: '1', name: 'Normal'},
          {id: '2', name: 'Alquilada'},
          {id: '3', name: 'Abandonada'}
     ];
    $scope.textoGuardar = '<i class="fa fa-check"></i> Guardar';
    $scope.disableGuardar = false;
    $scope.isAbandonada = false;
    
    $scope.guardar = function(){
    	var texto = $scope.textoGuardar;
    	$scope.textoGuardar = '<i class="fa fa-spin fa-spinner"></i> Espere';
        $scope.disableGuardar = true;
    	var parametros = {};
		parametros.operacion = 1;
		parametros.numero = $scope.numero;
		parametros.avenidaCalle = $scope.avenidaCalle.id;
		parametros.numeroCasa = $scope.numeroCasa;
		parametros.familia = $scope.familia;
		parametros.telefono = $scope.telefono;
		parametros.titular = $scope.titular;
		parametros.estado_pago = $scope.estadoPago.id;
		parametros.estado_domicilio = $scope.estadoDomicilio.id;
		//parametros.identificacion = $scope.identificacion.replace(/ /g, "");
		parametros.email = $scope.email;
		console.log(parametros);
    	$http({
            method: "POST",
            url: "/lomas-crm/direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	$scope.textoGuardar = texto;
            $scope.disableGuardar = false;
        	console.log(response.data);
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		desplegarMensaje("success", response.data.descripcion, 7);
	        		$scope.limpiarFormulario();
	        		break;
	        		
	        	case -100:
	        		//Caduco
	        		$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
	        		break;
	        		
	        	case -101:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        	case -300:
	        		desplegarMensaje("info", response.data.descripcion, 300);
	        		break;
        	
        	}
        }, function myError(response) {
        	$scope.textoGuardar = texto;
            $scope.disableGuardar = false;
        	desplegarMensaje("error", response.data, 300);
        });
    }
    
    $scope.limpiarFormulario = function(){
    	$scope.numero = '';
    	$scope.numeroCasa = '';
    	$scope.familia = '';
    	$scope.telefono = '';
    	$scope.titular = '';
    	$scope.email = '';
    	$scope.formulario.$setPristine();
    }
    
    $scope.cambio = function(){
    	if($scope.estadoDomicilio.id == 3){
	    	$scope.isAbandonada = true;
	    	$scope.familia = '';
	    	$scope.telefono = '';
	    	$scope.titular = '';
	    	$scope.email = '';
	    	$scope.estadoPago = $scope.estadosPago[1];
    	}else{
    		$scope.isAbandonada = false;
    	}
    }
});