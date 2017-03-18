var app = angular.module('Ingreso', ['ngSanitize','ngMask', 'ngAnimate']);

app.filter("trustUrl", ['$sce', function ($sce) {
    return function (recordingUrl) {
        return $sce.trustAsResourceUrl(recordingUrl);
    };
}]);

app.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
});

app.directive('imageonload', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('load', function() {
            	if(element[0].id == 'camaritaDocumento')
            		scope.callbackLoadImageDocumento();
            	else if(element[0].id == 'camaritaRostro')
            		scope.callbackLoadImageRostro();
            	else if(element[0].id == 'camaritaPlaca')
            		scope.callbackLoadImagePlaca();
            	else if(element[0].id == 'camaritaTamanioNormalDocumento')
            		scope.callbackLoadNphDocumento();
            	else if(element[0].id == 'camaritaTamanioNormalRostro')
            		scope.callbackLoadNphRostro();
            	else if(element[0].id == 'camaritaTamanioNormalPlaca')
            		scope.callbackLoadNphPlaca();
            	
            });
            element.bind('error', function(){
                //alert('image could not be loaded');
            });
        }
    };
});

app.controller('controladorIngreso', ['$scope', '$http', function($scope,$http) {
	var hostServerCamara = '192.168.1.13';
	$scope.imgDefault = '../imagenes?t=1&f=default.png';
	$scope.imgCamara = '../imagenes?t=1&f=camara.png';
	
	
	
	//documento
	$scope.isShowDoc = false;
	$scope.classBtnDoc = 'fa fa-play';
	$scope.isDisabledBtnDoc = false;
	
	//Rostro
	$scope.isShowRostro = false;
	$scope.classBtnRostro = 'fa fa-play';
	$scope.isDisabledBtnRostro = false;
	
	//Placa
	$scope.isShowPlaca = false;
	$scope.classBtnPlaca = 'fa fa-play';
	$scope.isDisabledBtnPlaca = false;
	
	$scope.iniciadaCamaraDoc = false;
	$scope.iniciadaCamaraRostro = false;
	$scope.iniciadaCamaraPlaca = false;
	
	$scope.isShowCapturadas = false;
	$scope.ingresos = [];
	
	$scope.values = [
         {id: '1', name: 'Av.'},
         {id: '2', name: 'Calle'}
     ];
	
	$scope.claseBotonBuscar = 'fa fa-search';
	$scope.disabledBuscar = false;
	
	$scope.claseBotonAlmacenar = 'fa fa-floppy-o';
	$scope.disableAlmacenar = true;
	$scope.disableAlmacenarCaptura= true;
	
	$scope.avenidaCalle = $scope.values[0];
	
	window.URL = window.URL || window.webkitURL;
	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia ||
	function() {
	    alert('Su navegador no soporta navigator.getUserMedia().');
	};

	//Este objeto guardará algunos datos sobre la cámara
	window.datosVideo = {
	    'StreamVideo': null,
	    'url': null
	};
	
	$scope.inicializarVaribles = function(){
		
		var parametros = {};
		parametros.id = 16;
		parametros.operacion = 7;
		$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		
	        		hostServerCamara = response.data.data[0].valor;
	        		
	        		$scope.imgServerCamaraDocumento = 'http://' + hostServerCamara + ':8081';
	        		$scope.imgServerCamaraRostro = 'http://' + hostServerCamara + ':8082';
	        		$scope.imgServerCamaraPlaca = 'http://' + hostServerCamara + ':8083';
	        		
	        		$scope.cargaNphDocumento = false;
	        		$scope.cargaNphRostro = false;
	        		$scope.cargaNphPlaca = false;
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
        	desplegarMensaje("error", response.data, 300);
        });
		
		
		
		
		
	}
	
	$scope.callbackLoadImageDocumento = function(){
		$scope.iniciadaCamaraDoc = true;
		$scope.$apply();
	}
	
	$scope.callbackLoadImageRostro = function(){
		$scope.iniciadaCamaraRostro = true;
		$scope.$apply();
	}
	
	$scope.callbackLoadImagePlaca = function(){
		$scope.iniciadaCamaraPlaca = true;
		$scope.$apply();
	}
	
	
	$scope.callbackLoadNphDocumento = function(){
		$scope.cargaNphDocumento = true;
		$scope.$apply();
		if($scope.cargaNphDocumento && $scope.cargaNphRostro && $scope.cargaNphPlaca){
			$scope.capturarImagen();
			$scope.cargaNphDocumento = false;
			$scope.cargaNphRostro = false;
			$scope.cargaNphPlaca = false;
		}
		
	}
	
	$scope.callbackLoadNphRostro = function(){
		$scope.cargaNphRostro = true;
		$scope.$apply();
		if($scope.cargaNphDocumento && $scope.cargaNphRostro && $scope.cargaNphPlaca){
			$scope.capturarImagen();
			$scope.cargaNphDocumento = false;
			$scope.cargaNphRostro = false;
			$scope.cargaNphPlaca = false;
		}
	}
	
	$scope.callbackLoadNphPlaca = function(){
		$scope.cargaNphPlaca = true;
		$scope.$apply();
		if($scope.cargaNphDocumento && $scope.cargaNphRostro && $scope.cargaNphPlaca){
			$scope.capturarImagen();
			$scope.cargaNphDocumento = false;
			$scope.cargaNphRostro = false;
			$scope.cargaNphPlaca = false;
		}
	}
	
	$scope.limpiar = function(){
		$scope.numero = '';
		$scope.placa = '';
		$scope.numeroCasa = '';
		$scope.textoFamilia='';
		$scope.isShowCapturadas = false;
		$scope.disableAlmacenar = true;
		$scope.disableAlmacenarCaptura= true;
		$scope.formulario.$setPristine();
	}
	
	$scope.cambioTexto = function(){
		$scope.disableAlmacenar = true;
		$scope.textoFamilia='';
	}
	
	$scope.cargarTabla= function(){
		var parametros = {};
		parametros.in_rownum = 10;
		parametros.operacion = 5;
		$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		
	        		//$scope.construirTabla(response.data.data);
	        		$scope.ingresos = response.data.data;
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
        	$scope.claseBotonAlmacenar = texto;
            $scope.disableAlmacenar = false;
        	desplegarMensaje("error", response.data, 300);
        });
	}
	
	$scope.iniciarCamaraDocumento = function(){
		var clase = $scope.classBtnDoc;
		$scope.classBtnDoc = 'fa fa-spin fa-spinner';
		$scope.isDisabledBtnDoc = true;
		$scope.iniciadaCamaraDoc = false;
		navigator.getUserMedia({
	        'audio': false,
	        'video': true
	    }, function(streamVideo) {
	        datosVideo.StreamVideo = streamVideo;
	        datosVideo.url = window.URL.createObjectURL(streamVideo);
	        $scope.isShowDoc = true;
	        $scope.camaraDocumento = datosVideo.url;
	        $scope.classBtnDoc = clase;
			$scope.isDisabledBtnDoc = false;
			$scope.iniciadaCamaraDoc = true;
	        $scope.$apply() ;
	      	
	    }, function() {
	    	$scope.classBtnDoc = clase;
			$scope.isDisabledBtnDoc = false;
			$scope.$apply() ;
	        alert('No fue posible obtener acceso a la cámara.');
	    });
	}
	
	$scope.iniciarCamaraRostro = function(){
		var clase = $scope.classBtnRostro;
		$scope.classBtnRostro = 'fa fa-spin fa-spinner';
		$scope.isDisabledBtnRostro = true;
		$scope.iniciadaCamaraRostro = false;
		navigator.getUserMedia({
	        'audio': false,
	        'video': true
	    }, function(streamVideo) {
	        datosVideo.StreamVideo = streamVideo;
	        datosVideo.url = window.URL.createObjectURL(streamVideo);
	        $scope.isShowRostro = true;
	        $scope.camaraRostro = datosVideo.url;
	        $scope.classBtnRostro = clase;
			$scope.isDisabledBtnRostro= false;
			$scope.iniciadaCamaraRostro = true;
	        $scope.$apply() ;
	      	
	    }, function() {
	    	$scope.classBtnRostro = clase;
			$scope.isDisabledBtnRostro = false;
			$scope.$apply();
	        alert('No fue posible obtener acceso a la cámara.');
	    });
	}
	
	$scope.iniciarCamaraPlaca = function(){
		var clase = $scope.classBtnPlaca;
		$scope.classBtnPlaca= 'fa fa-spin fa-spinner';
		$scope.isDisabledBtnPlaca= true;
		$scope.iniciadaCamaraPlaca = false;
		navigator.getUserMedia({
	        'audio': false,
	        'video': true
	    }, function(streamVideo) {
	        datosVideo.StreamVideo = streamVideo;
	        datosVideo.url = window.URL.createObjectURL(streamVideo);
	        $scope.isShowPlaca = true;
	        $scope.camaraPlaca = datosVideo.url;
	        $scope.classBtnPlaca = clase;
			$scope.isDisabledBtnPlaca= false;
			$scope.iniciadaCamaraPlaca = true;
	        $scope.$apply() ;
	      	
	    }, function() {
	    	$scope.classBtnPlaca = clase;
			$scope.isDisabledBtnPlaca = false;
			$scope.$apply();
	        alert('No fue posible obtener acceso a la cámara.');
	    });
	}
	
	$scope.capturarImagen = function(){
		
	    var oCamaraDoc, oFotoDoc, oContextoDoc, wDoc, hDoc;
	    var oCamaraRostro, oFotoRostro, oContextoRostro, wRostro, hRostro;
	    var oCamaraPlaca, oFotoPlaca, oContextoPlaca, wPlaca, hPlaca;
	    
	    /*Tamanio normal*/
	    
	    oCamaraDoc = document.getElementById("camaritaTamanioNormalDocumento");
	    oFotoDoc = document.getElementById("fotoDocOriginal");
	    oContextoDoc = oFotoDoc.getContext("2d");
	    oContextoDoc.drawImage(oCamaraDoc, 0, 0, 400, 327);
	    
	    oCamaraRostro = document.getElementById("camaritaTamanioNormalRostro");
	    oFotoRostro = document.getElementById("fotoRostroOriginal");
	    oContextoRostro = oFotoRostro.getContext("2d");
	    oContextoRostro.drawImage(oCamaraRostro, 0, 0, 400, 327);
	    
	    oCamaraPlaca = document.getElementById("camaritaTamanioNormalPlaca");
	    oFotoPlaca = document.getElementById("fotoPlacaOriginal");
	    oContextoPlaca = oFotoPlaca.getContext("2d");
	    oContextoPlaca.drawImage(oCamaraPlaca, 0, 0, 400, 327);
	    
	    
	    oCamaraDoc = document.getElementById("camaritaDocumento");
	    oFotoDoc = document.getElementById("fotoDoc");
	    wDoc = oCamaraDoc.clientWidth;
	    hDoc = oCamaraDoc.clientHeight;
	    oFotoDoc.setAttribute("width",wDoc);
	    oFotoDoc.setAttribute("height",hDoc);
	    oCamaraDoc = document.getElementById("camaritaTamanioNormalDocumento");
	    oContextoDoc = oFotoDoc.getContext("2d");
	    oContextoDoc.drawImage(oCamaraDoc, 0, 0, wDoc, hDoc);
	    
	    oCamaraRostro = document.getElementById("camaritaRostro");
	    oFotoRostro = document.getElementById("fotoRostro");
	    wRostro = oCamaraRostro.clientWidth;
	    hRostro = oCamaraRostro.clientHeight;
	    oFotoRostro.setAttribute("width",wRostro);
	    oFotoRostro.setAttribute("height",hRostro);
	    oCamaraRostro = document.getElementById("camaritaTamanioNormalRostro");
	    oContextoRostro = oFotoRostro.getContext("2d");
	    oContextoRostro.drawImage(oCamaraRostro, 0, 0, wRostro, hRostro);
	    
	    oCamaraPlaca = document.getElementById("camaritaPlaca");
	    oFotoPlaca = document.getElementById("fotoPlaca");
	    wPlaca = oCamaraPlaca.clientWidth;
	    hPlaca = oCamaraPlaca.clientHeight;
	    oFotoPlaca.setAttribute("width",wPlaca);
	    oFotoPlaca.setAttribute("height",hPlaca);
	    oCamaraPlaca = document.getElementById("camaritaTamanioNormalPlaca");
	    oContextoPlaca = oFotoPlaca.getContext("2d");
	    oContextoPlaca.drawImage(oCamaraPlaca, 0, 0, wPlaca, hPlaca);
	    
	    $scope.isShowCapturadas = true;
	    $scope.disableAlmacenarCaptura= false;
	    $scope.disabledCapturar = false;
	    $scope.$apply();
	}
	
	$scope.getSnapshotFromServer = function(){
		$scope.disabledCapturar = true;
		$scope.imgServerNphCamaraDocumento = 'http://' + hostServerCamara +'/cgi-bin/nph-mjgrab?1&decache=' + Math.random();
	    $scope.imgServerNphCamaraRostro = 'http://' + hostServerCamara +'/cgi-bin/nph-mjgrab?2&decache=' + Math.random();
	    $scope.imgServerNphCamaraPlaca = 'http://' + hostServerCamara +'/cgi-bin/nph-mjgrab?3&decache=' + Math.random();
	}
	
	$scope.buscar = function (){
    	var texto = $scope.claseBotonBuscar;
    	$scope.claseBotonBuscar = 'fa fa-spin fa-spinner';
        $scope.disableBuscar = true;
        
        var parametros = {};
		parametros.operacion = 2;
		parametros.numero = $scope.numero;
		parametros.avenidaCalle = $scope.avenidaCalle.id;
		parametros.numeroCasa = $scope.numeroCasa;
		
    	$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	$scope.claseBotonBuscar = texto;
            $scope.disableBuscar = false;
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		if(response.data.data.length == 0){
	        			$scope.claseEncontrada = 'familiaNoEncontrada';
	        			$scope.textoFamilia='No registrada <i class="fa fa-times"></i>';
	        			$scope.textoFam = 'Familia';
	        			$scope.disableAlmacenar = true;
	        		}else{
	        			$scope.claseEncontrada = 'familiaEncontrada';
	        			$scope.textoFamilia= response.data.data[0].familia + ' <i class="fa fa-check"></i>';
	        			$scope.id_direccion= response.data.data[0].id_direccion;
	        			$scope.disableAlmacenar = false;
	        		}
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
        	$scope.claseBotonBuscar = texto;
            $scope.disableBuscar = false;
        	desplegarMensaje("error", response.data, 300);
        });
    }
	
	$scope.almacenar = function(){
		var texto = $scope.claseBotonAlmacenar;
    	$scope.claseBotonAlmacenar = 'fa fa-spin fa-spinner';
        $scope.disableAlmacenar = true;
        
    	
		//Se crea un objeto para ser enviado al servidor 
		var canDoc = document.getElementById("fotoDocOriginal");
		
		
		var canRostro = document.getElementById("fotoRostroOriginal");
		var canPlaca = document.getElementById("fotoPlacaOriginal");
		$scope.imgServerCamaraDocumento = '';
		var parametros = {};
		parametros.archivoDoc = canDoc.toDataURL("image/png");
		parametros.archivoRostro = canRostro.toDataURL("image/png");
		parametros.archivoPlaca = canPlaca.toDataURL("image/png");
		parametros.placa = $scope.placa;
		parametros.id_direccion = $scope.id_direccion
		parametros.operacion = 4;
		
		$http({
            method: "POST",
            url: "../direccion",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'dataType': 'json'
            },
            data: parametros
        }).then(function mySucces(response) {
        	$scope.claseBotonAlmacenar = texto;
            $scope.disableAlmacenar = false;
        	switch(parseInt(response.data.resultado)){
        	
	        	case -1:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case -2:
	        		desplegarMensaje("error", response.data.descripcion, 300);
	        		break;
	        		
	        	case 1:
	        		
	        		//$scope.construirTabla(response.data.data);
	        		$scope.limpiar();
	        		desplegarMensaje("success", response.data.descripcion, 7);
	        		$scope.cargarTabla();
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
        	$scope.claseBotonAlmacenar = texto;
            $scope.disableAlmacenar = false;
        	desplegarMensaje("error", response.data, 300);
        });
	}
	
	
	
	
	var init = function () {
		$scope.cargarTabla();
		$scope.inicializarVaribles();
	};
	
	init();
	
}]);
