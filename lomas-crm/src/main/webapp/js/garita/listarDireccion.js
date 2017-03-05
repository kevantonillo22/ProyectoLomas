var app = angular.module('listarDireccion', ['ngSanitize','ngMask']);

app.directive( 'compileData', function ( $compile ) {
  return {
    scope: true,
    link: function ( scope, element, attrs ) {

      var elmnt;

      attrs.$observe( 'template', function ( myTemplate ) {
        if ( angular.isDefined( myTemplate ) ) {
          // compile the provided template against the current scope
          elmnt = $compile( myTemplate )( scope );

            element.html(""); // dummy "clear"

          element.append( elmnt );
        }
      });
    }
  };
});

app.controller('listar', function($scope,$http, $compile) {
	$scope.values = [
        {id: '1', name: 'Av.'},
        {id: '2', name: 'Calle'}
    ];
	$scope.numero ='';
	$scope.numeroCasa ='';
	$scope.avenidaCalle = $scope.values[0];
	
    $scope.textoBotonBuscar = '<i class="fa fa-search"></i> Buscar';
    $scope.disableBuscar = false;
    $scope.claseVerDatos = 'fa fa-pencil-square-o';
    $scope.claseEliminar = 'fa fa-times';
    $scope.disableEliminar = false;
    
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
     
     $scope.claseBotonEditar = 'fa fa-check';
     $scope.disabledBotonEditar = false;
    
    $scope.buscar = function (){
    	var texto = $scope.textoBotonBuscar;
    	$scope.textoBotonBuscar = '<i class="fa fa-spin fa-spinner"></i> Espere';
        $scope.disableBuscar = true;
        
        var parametros = {};
		parametros.operacion = 2;
		parametros.numero = $scope.numeroFiltro;
		parametros.avenidaCalle = $scope.avenidaCalleFiltro !== undefined ? $scope.avenidaCalleFiltro.id : "";
		parametros.numeroCasa = $scope.numeroCasaFiltro;
		
    	$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	$scope.textoBotonBuscar = texto;
            $scope.disableBuscar = false;
        	console.log(response.data);
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		$scope.construirTabla(response.data.data);
	        		//desplegarMensaje("success", response.data.descripcion, 7);
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
        	$scope.textoBotonBuscar = texto;
            $scope.disableBuscar = false;
        	desplegarMensaje("error", response.data, 300);
        });
    }
    
    $scope.construirTabla = function(data){
    	var table = $('#example').DataTable({
	   		 "data":data,
	   		 "preDrawCallback": function( settings ,json) {
	   	
	   		  },
	   		  stateSave: true,
	   		  "bFilter" : false,               
	   		  "bLengthChange": true,
	   		  "bPaginate":true,
	   			"bDestroy": true,
	   			"pagingType": "bootstrap",
	   			"lengthMenu": [[5, 10, 25, 50, 100, -1], [5, 10, 25, 50, 100, "Todos"]],
	   			"iDisplayLength": 25,
	   			"language": {
	   				"zeroRecords": "<center>No hay filas para mostrar</center>",
	   				"lengthMenu": "_MENU_&nbsp;&nbsp;Registros por página",
	   				"info": "Mostrando la página _PAGE_ de _PAGES_",
	   				"infoEmpty": "",
	   				"infoFiltered": "",
	   				"paginate": {
	   					"next": "Siguiente",
	   					"previous": "Anterior"
	   				},
	   				"sProcessing": '<span style="font-weight:bold;">Cargando <i class="fa fa-spinner fa-spin"></i></span>'
	   			},
	   		/*DESCOMENTAR SI SE QUIERE CAMBIAR EL ANCHO DE CADA UNA DE LAS COLUMNAS
	   		 * "columnDefs": 
	   			[
	   			 	{ "width": "5%", "targets": 0 },
	   			 	{ "width": "10%", "targets": 0 },
	   			 	{ "width": "30%", "targets": 0 },
	   			 	{ "width": "20%", "targets": 0 },
	   			 	{ "width": "20%", "targets": 0 },
	   			 	{ "width": "10%", "targets": 0 }
	   			 	
	   			 	
	   			],*/
	   		 "autoWidth": false,
	   		 "columns": 
	   			[
	   			  {
	   					"class": 'clasetest',
	   					"orderable": false,
	   					"data": '',
	   					"defaultContent": '<button title="Opciones" style="border-radius:50%;" class="btn btn-white"><b>+</b></button>',
	   					"visible": true
	   				}, {
	   					"data": "direccion"
	   				}, {
	   					"data": "nombre_titular"
	   				}, {
	   					"data": "telefono"
	   				}, {
	   					"data": "email"
	   				}, {
	   					"data": "label_pago"
	   				}, {
	   					"data": "label_domicilio"
	   				}
	   			],
	   			"initComplete": function (settings, json) {
	   				
	   				$('#example_info').css('display', 'none');
	   				$('#example tbody tr').each(function () {
	   					$(this).find('td:eq(0)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 0
	   					$(this).find('td:eq(1)').css('text-align', 'center'); // Esta instrucción centra el contenido de la columna 1
	   				});
	   				
	   			},
	   			"fnDrawCallback": function( oSettings ) {
	   				//CERRAR TODAS LOS DETALLES DE LAS FILAS PARA CUANDO 
	   				//SE NAVEGUE ENTRE PAGINAS
	   				$("[role='row']").each(function(){
	   					if(typeof table !== "undefined") {
	   					    table.row($(this)).child.hide();
	   					}
	   				}
	   				);
	   		    }
	   		 
	   	 });
	   		
	   	//cuando se de click a controles de paginacion se oculten los botones
	   	$('.pagination li a').click(function(){
	   		$("[role='row']").each(function(){
	   			table.row($(this)).child.hide();
	   			}
	   		);
	   	});
	   		
	   	// Restablece el evento click
	   	$('#example tbody').unbind( "click" );
	   		
	   	//Indispensable para detalle de cada tupla
	   	$('#example tbody').on('click', 'td.clasetest', function () {
	   		var tr = $(this).parents('tr');
	   		var row = table.row(tr);
	   		fila = row;
	   		id=row.data().id;
	   		
	   		$("[role='row']").not(tr).each(function(){
	   				table.row($(this)).child.hide();
	   			}
	   		);
	   		
	   		if (row.child.isShown()) {
	   			// This row is already open - close it
	   			row.child.hide();
	   			tr.removeClass('shown');
	   		} else {
	   			row.child($compile($scope.format(row.data()))($scope)).show();
	   			tr.addClass('shown');
	   		}
	   	});
    }
    
    
    $scope.format = function (d) {
    	$scope.id = d.id_direccion;
    	var ver_datos 	= '<td><button data-ng-disabled="disableVerDatos" data-ng-click="editar(' + d.id_direccion + ')" id="btn-ver" class="btn btn-green" ><i data-ng-class="claseVerDatos"></i> Ver datos </button></td>';
    	var eliminar 	= '<td><button data-ng-disabled="disableEliminar" data-ng-click="eliminar(' + d.id_direccion + ')" id="btn-eliminar" class="btn btn-red" ><i data-ng-class="claseEliminar"></i> Eliminar </button></td>';
    	
    	var descartar = '';
    	
    	var spin 		= '<td><div id="changeSpin"></div></td>';
    	var titulo 		= 'Opciones';
    	
    	return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' + 
    	'<tr>' + 
    	'<td>' + titulo + '</td>' + 
    	'</tr>' + 
    	'<tr>' + 
    	ver_datos +
    	eliminar +
    	descartar +
    	//rechazar +
    	spin + 
    	//'<td><div class="form-group" id="wrapCasoOP" style="margin-bottom:0px;"><select placeholder="holis" class="form-control" id="casoOP"><option value="" disabled selected>Asignado a O.P. de</option><option value="1">4º año </option><option value="2">5º año </option><option value="3">PRC </option><option value="4">Postgrado </option></select></div></td><td><div id="changeSpin"></div></td>' +
    	//'<td><label class="checkbox-inline"><input id="chkCaso" type="checkbox"> Caso Docente</label></td><td><div id="changeSpin2"></div></td>' + 
    	
    	
    	
    	'</tr>' + 
    	'</table>';
    }
    
    $scope.editar = function(id){
    	var texto = $scope.claseVerDatos;
    	$scope.claseVerDatos = 'fa fa-spin fa-spinner';
        $scope.disableVerDatos = true;
        
        var parametros = {};
		parametros.operacion = 2;
		parametros.id_direccion = id;
		
    	$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
            $scope.claseVerDatos = texto;
            $scope.disableVerDatos = false;
        	console.log(response.data);
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		$scope.numero = response.data.data[0].numero_calle;
	        		$scope.avenidaCalle = $scope.selectCombo($scope.values, response.data.data[0].calle_av);
	        		$scope.numeroCasa = response.data.data[0].num_casa;
	        		$scope.familia = response.data.data[0].familia;
	        		$scope.telefono = response.data.data[0].telefono;
	        		$scope.titular = response.data.data[0].nombre_titular;
	        		$scope.email = response.data.data[0].email;
	        		$scope.estadoPago = $scope.selectCombo($scope.estadosPago, response.data.data[0].estado_pago);
	        		$scope.estadoDomicilio = $scope.selectCombo($scope.estadosDomicilio, response.data.data[0].estado_domicilio);
	        		//Variable que almacena el identificador de la tupla seleccionada
	        		
	        		$('#modalModificarDireccion').modal();
	        		//$scope.construirTabla(response.data.data);
	        		//desplegarMensaje("success", response.data.descripcion, 7);
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
        	$scope.claseVerDatos = texto;
            $scope.disableVerDatos = false;
        	desplegarMensaje("error", response.data, 300);
        });
    }
    
    $scope.selectCombo = function(array, val){
    	var ret ;
    	angular.forEach(array, function(obj, key) {
			console.log(obj);
			console.log(val);
			
			if(Number(obj.id) == Number(val))
				ret =  obj;
		});
    	return ret;
    }
    
    $scope.realizarEdicion = function(){
    	
        
    	var texto = $scope.claseBotonEditar;
    	$scope.claseBotonEditar = 'fa fa-spin fa-spinner';
        $scope.disabledBotonEditar = true;
        
        var parametros = {};
		parametros.operacion = 3;
		parametros.id_direccion = $scope.id;
		parametros.numero = $scope.numero;
		parametros.avenidaCalle = $scope.avenidaCalle.id;
		parametros.numeroCasa = $scope.numeroCasa;
		parametros.familia = $scope.familia;
		parametros.telefono = $scope.telefono;
		parametros.titular = $scope.titular;
		parametros.estado_pago = $scope.estadoPago.id;
		parametros.estado_domicilio = $scope.estadoDomicilio.id;
		parametros.email = $scope.email;
		console.log(parametros);
    	$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
            $scope.claseBotonEditar = texto;
            $scope.disabledBotonEditar = false;
        	
            switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		
	        		//$scope.construirTabla(response.data.data);
	        		desplegarMensaje("success", response.data.descripcion, 7);
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
        	$scope.claseBotonEditar = texto;
            $scope.disabledBotonEditar = false;
        	
        	desplegarMensaje("error", response.data, 300);
        });
    }
    
    $scope.eliminar = function(){
    	var id = $scope.id;
    	confirmacionMensaje({
    		accept:function(){
    			var texto = $scope.claseEliminar;
    	    	$scope.claseEliminar = 'fa fa-spin fa-spinner';
    	        $scope.disableEliminar = true;
    	        
    	        var parametros = {};
    			parametros.operacion = 3;
    			parametros.id_direccion = id;
    			parametros.estado = 2;
    			console.log(parametros);
    	    	$http({
    	            method: "POST",
    	            url: "../direccion",
    	            headers: {
    	                'Content-Type': 'application/json; charset=utf-8',
    	                'dataType': 'json'
    	            },
    	            data: parametros
    	        }).then(function mySucces(response) {
    	            $scope.claseEliminar = texto;
    	            $scope.disableEliminar = false;
    	        	
    	            switch(parseInt(response.data.resultado)){
    	        	
    		        	case -1:
    		        		desplegarMensaje("error", response.data.descripcion, 300);
    		        		break;
    		        		
    		        	case -2:
    		        		desplegarMensaje("error", response.data.descripcion, 300);
    		        		break;
    		        		
    		        	case 1:
    		        		//actualizamos la tabla
    		        		$scope.buscar();
    		        		//$scope.construirTabla(response.data.data);
    		        		desplegarMensaje("success", response.data.descripcion, 7);
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
    	        	$scope.claseEliminar = texto;
    	            $scope.disableEliminar = false;
    	        	
    	        	desplegarMensaje("error", response.data, 300);
    	        });
    		},
    		cancel:function(){
    			
    		}
    	});
    	
    }
});