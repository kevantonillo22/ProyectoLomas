var app = angular.module('listarIngreso', ['ngSanitize','ngMask']);

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
	$scope.numeroFiltro ='';
	$scope.numeroCasaFiltro ='';
	$scope.avenidaCalleFiltro = $scope.values[0];
	
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
     
     $scope.anexo = '';
     
     $scope.limpiar = function(){
		$scope.numeroFiltro ='';	
		$scope.numeroCasaFiltro ='';
		$scope.avenidaCalleFiltro = $scope.values[0];
		$scope.fecha = '';
     }
    
     $scope.addZero = function(){
      	if($scope.numeroFiltro.length == 1){
      		if(Number($scope.numeroFiltro) == 0)
      			$scope.numeroFiltro = '';
      		else
      			$scope.numeroFiltro = "0" + $scope.numeroFiltro;
      	}
      }
     
    $scope.buscar = function (){
    	var texto = $scope.textoBotonBuscar;
    	$scope.textoBotonBuscar = '<i class="fa fa-spin fa-spinner"></i> Espere';
        $scope.disableBuscar = true;
        
        var parametros = {};
		parametros.operacion = 6;
		parametros.fecha = $scope.fecha;
		parametros.numeroFiltro = $scope.numeroFiltro;
		parametros.anexo = $scope.anexo;
		parametros.avenidaCalleFiltro = $scope.avenidaCalleFiltro !== undefined ? $scope.avenidaCalleFiltro.id : "";
		parametros.numeroCasaFiltro = $scope.numeroCasaFiltro;
		
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
	   					"data": "in_direccion"
	   				}, {
	   					"data": "in_placa"
	   				}, {
	   					"data": "in_fecha_entrada"
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
    	var placa 	= '<td><img src="../imagenes?t=3&f=' + d.in_imagen_placa + '" style="width:100%"></td>';
    	var rostro 	= '<td><img src="../imagenes?t=3&f=' + d.in_imagen_rostro + '" style="width:100%"></td>';
    	var dpi 	= '<td><img src="../imagenes?t=3&f=' + d.in_imagen_dpi + '" style="width:100%"></td>';
    	
    	
    	var descartar = '';
    	
    	var titulo 		= 'Fotografias';
    	
    	return '<table cellpadding="5" cellspacing="0" border="0" style="width:100%">' + 
    	'<tr>' + 
    	'<td>' + titulo + '</td>' + 
    	'</tr>' + 
    	'<tr>' + 
    	placa +
    	rostro +
    	dpi +
    	descartar +
    	
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