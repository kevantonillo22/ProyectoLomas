<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="crm_BLL.General_BLL"%>
<%@page import="crm_BE.Sesion_BE"%>
<%@page import="crm_BE.Funciones"%>

<%
	//Valida la sesion
	Sesion_BE sesion = new Sesion_BE();
	String id=request.getParameter("rol");
	String nombre=request.getParameter("nombre");
	
	if(id!=null){
		
		if(session.getAttribute("sesion") == null){
			session.invalidate();
			response.sendRedirect("/lomas-crm/sesionExpirada.jsp");
		}
		else
		{
			sesion = (Sesion_BE) session.getAttribute("sesion");
			if(!General_BLL.tienePermiso(sesion,Funciones.ASIGNAR_FUNCIONES_A_UN_ROL)){
				response.sendRedirect("/lomas-crm/errorpermisos.jsp");
			}
		}
	}else{
		response.sendRedirect("/lomas-crm/errorGeneral.jsp");	
	}

%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Sistema de Gestión- CUB</title>

    <link href="/lomas-crm/css/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/lomas-crm/icons/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/lomas-crm/css/style.css" rel="stylesheet">
    <link href="/lomas-crm/css/plugins.css" rel="stylesheet">
	<link href="/lomas-crm/css/plugins/messenger/messenger.css" rel="stylesheet"> 	
	<link href="/lomas-crm/css/plugins/messenger/messenger-theme-future.css" rel="stylesheet">
	<!-- ESTILOS PERSONALIZADOS-->
	<style type="text/css">
	</style>
</head>
<body>
    <div id="wrapper">
        <!-- begin TOP NAVIGATION -->
        <nav class="navbar-top" role="navigation">

            <!-- begin BRAND HEADING -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle pull-right" data-toggle="collapse" data-target=".sidebar-collapse">
                    <i class="fa fa-bars"></i> Menú
                </button>
                <div class="navbar-brand">
                    <a href="/lomas-crm/index.jsp">
                        <img src="/lomas-crm/img/logo_home.png" class="img-responsive" alt="">
                    </a>
                </div>
            </div>
            <!-- end BRAND HEADING -->

            <div class="nav-top">

                <!-- begin LEFT SIDE WIDGETS -->
                <ul class="nav navbar-left">
                    <li class="tooltip-sidebar-toggle">
                        <a href="#" id="sidebar-toggle" data-toggle="tooltip" data-placement="right" title="" data-original-title="Barra lateral">
                            <i class="fa fa-bars"></i>
                        </a>
                    </li>
                    <!-- You may add more widgets here using <li> -->
                </ul>
                <!-- end LEFT SIDE WIDGETS -->

                <!-- begin MESSAGES/ALERTS/TASKS/USER ACTIONS DROPDOWNS -->
               <ul class="nav navbar-right">
					<!-- begin USER ACTIONS DROPDOWN -->
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-user"></i> <i
							class="fa fa-caret-down"></i>
					</a>
						<ul class="dropdown-menu dropdown-user">
							<li>
                                <a class="logout_open" href="#logout" data-popup-ordinal="0" id="open_22839500">
                                    <i class="fa fa-sign-out"></i> Cerrar sesión
                                </a>
                            </li>
						</ul> <!-- /.dropdown-menu --></li>
				</ul>
                <!-- /.nav -->
                <!-- end MESSAGES/ALERTS/TASKS/USER ACTIONS DROPDOWNS -->

            </div>
            <!-- /.nav-top -->
        </nav>
        <!-- /.navbar-top -->
        <!-- end TOP NAVIGATION -->

        <!-- begin SIDE NAVIGATION -->
        <nav class="navbar-side" role="navigation">
            <div class="navbar-collapse sidebar-collapse collapse">
                <ul id="side" class="nav navbar-nav side-nav">
                    <!-- begin SIDE NAV USER PANEL -->
                    <li class="side-user hidden-xs">
						<img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
						<p class="welcome">
							<i class="fa fa-key"></i> Inició sesión como
						</p>
						<p class="name tooltip-sidebar-logout"><%=sesion.se_nombres%>
							<span class="last-name"><%=sesion.se_apellidos%></span> <a
								style="color: inherit" class="logout_open" href="#logout"
								data-toggle="tooltip" data-placement="top" title=""
								data-popup-ordinal="1" id="open_61613468"
								data-original-title="Salir"><i class="fa fa-sign-out"></i></a>
						</p>
						<div class="clearfix"></div></li>
        		<%= sesion.se_menu %>
                </ul>
                <!-- /.side-nav -->
            </div>
            <!-- /.navbar-collapse -->
        </nav>
        <!-- /.navbar-side -->
        <!-- end SIDE NAVIGATION -->

        <!-- begin MAIN PAGE CONTENT -->
        <div id="page-wrapper" class="">

            <div class="page-content page-content-ease-in">

                <!-- begin PAGE TITLE ROW -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="page-title">
                            <h1>
                                Modificar rol
                                <small></small>
                            </h1>
                            <ol class="breadcrumb">
                                <li><i class="fa fa-dashboard"></i>  <a href="/lomas-crm/index.jsp">Inicio</a>
                                </li>
                                <li class="active">Modificar rol</li>
                            </ol>
                        </div>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /.row -->
                <!-- end PAGE TITLE ROW -->
  <div class="row">
                <div class="dhe-example-section" id="ex-2-1">
        
        <div class="dhe-example-section-content">

            <!-- BEGIN: XHTML for example 2.1 -->
	
            <div id="ejemplo">

                <p><button class="input-button btn btn-default" id="btn-guardar"></button></p><div class="column left first col-lg-6"><h4>Funciones disponibles</h4>
                
                <!-- Menu desplegable -->
                
									<select id="categorias" class="btn btn-white btn-block" style="text-align:center;">
										
							
									</select>


									<!-- FIN Menu desplegable -->
                
                
			<ul id="no_asignadas" class="sortable-list ui-sortable"
				style="min-height: 200px; padding: 15px; border-style: solid; border-radius: 8px; border-width: 1px; background-color: #D6D6D6;">
				
				
			</ul>
		</div>

                <div class="column left col-lg-6">
<h4>El rol <%= nombre %> tiene las siguientes funciones</h4>

<button type="button" class="btn btn-white btn-block">Arrastre aquí las funciones</button>

									<ul  id="asignadas"class="sortable-list ui-sortable" style="min-height: 200px; padding: 15px; background-color: #FFFFFF; border-style: dashed; border-radius: 8px; border-width: 1px;">
									</ul>

								</div>

            

            </div>

            <div class="clearer">&nbsp;</div>

            <!-- END: XHTML for example 2.1 -->

        </div>
    </div>
                    </div>

            </div>
            <!-- /.page-content -->

        </div>
        <!-- /#page-wrapper -->
        <!-- end MAIN PAGE CONTENT -->

    </div>
    <!-- VENTANA DE CONFIRMACIÓN DE LOGOUT -->
    <div id="logout">
        <div class="logout-message">
            <img class="img-circle" alt="" style="background: whitesmoke; background-image: url('<%=sesion.se_ruta_foto%>'); width: 150px; height: 150px; background-size: cover; background-repeat: no-repeat; background-position: center center;">
            <h3>
                <i class="fa fa-sign-out text-green"></i> ¿Seguro que desea salir?
            </h3>
            <p>De click en "Salir" para cerrar sesión.</p>
            <ul class="list-inline">
                <li>
                    <a href="/lomas-crm/salir"  class="btn btn-green">
                        <strong>Salir</strong>
                    </a>
                </li>
                <li>
                    <button class="logout_close btn btn-green">Cancelar</button>
                </li>
            </ul>
        </div>
    </div>
    <!-- GLOBAL SCRIPTS -->
    <script src="/lomas-crm/js/jquery-1.11.0.min.js"></script>
    <script src="/lomas-crm/js/plugins/bootstrap/bootstrap.min.js"></script>
    <script src="/lomas-crm/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/lomas-crm/js/plugins/popupoverlay/jquery.popupoverlay.js"></script>
    <script src="/lomas-crm/js/plugins/popupoverlay/defaults.js"></script>
    <script type="text/javascript" src="/lomas-crm/js/jquery-ui-1.10.4.min.js"></script>
    <script src="/lomas-crm/js/plugins/messenger/messenger.min.js"></script>
<script src="/lomas-crm/js/plugins/messenger/messenger-theme-future.js"></script>
    <!-- Logout Notification Box -->
    
    <!-- /#logout -->
    <!-- Logout Notification jQuery -->
    <script src="/lomas-crm/js/plugins/popupoverlay/logout.js"></script>
    <!-- HISRC Retina Images -->

    <!--script src="/lomas-crm/js/plugins/hisrc/hisrc.js"></script-->

    <!-- PAGE LEVEL PLUGIN SCRIPTS -->

    <!-- THEME SCRIPTS -->
    <script src="/lomas-crm/js/flex.js"></script>

    <!--Llamada asíncrona al servidor-->
    <script type="text/javascript">
    
    $(function() {
		Messenger.options = {
			extraClasses : 'messenger-fixed messenger-on-top',
			theme : 'future'
		};
	});

    
    	var no_asignadas;
    
 		divCadena = '<div class="col-lg-4" style="position: fixed;    top: 50%;    left: 50%;    margin-top: -102px;    margin-left: -240px;">'
		+ '<div class="portlet portlet-gray">'
		+ '<div class="portlet-heading">'
		+ '<div class="portlet-title">'
		+ '<h4>Debe iniciar sesión</h4>'
		+ '</div>'
		+ '<div class="clearfix"></div>'
		+ '</div>'
		+ '<div id="defaultPortlet" class="panel-collapse collapse in">'
		+ '<div class="portlet-body">'
		+
		'<p>Para acceder a esta página debe iniciar sesión.</p>'
		+ '<a href="/lomas-crm/ingreso.jsp"><button class="btn btn-default">Iniciar sesión</button></a>'
		+ '</div>' + '</div>  ' + '</div>' + '</div>';

		//Llenar los campitos en el div lbl-advertencia
		var id =<%out.print(id);%>;
    $(document).ready(function()
   	{
	    //Deshabilitar el boton y mostrar animacion de cargado
	   	$("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	    $("#btn-guardar").prop("disabled",true);
	    //Obtener el id que ya viene validado
	    
	    //Llenar los datos que lleva la peticion
	    datos={"rol":id,"asignadas":false};
	    
	   	$.post('/lomas-crm/funcion',datos,callback,'json');
	    function callback(respuesta)
	    {
	    	
	    	
	    	
	    	
	    	
	    	//console.log(respuesta.no_asignadas);
	    	no_asignadas=respuesta.no_asignadas;
			
	    	
	    	

	    	
	    	respuesta.asignadas.forEach(function(nodo) {
	    		$('#asignadas').append('<li id="'+nodo.funcion+'" class="sortable-item btn btn-white btn-block">'+nodo.descripcion+'</li>');
	    	});
	    	
	    	
	    	//Agregar a la lista de padres
	    	respuesta.no_asignadas.forEach(function(nodo) {
	    		
	    		if(nodo.padre==0){
	    			$("#categorias").append('<option value="'+nodo.funcion+'">'+nodo.descripcion+'</option>');
	    		}
	    		
	    	});
	    	//Agregar los hijitos del primer padre
	   

	    	  	respuesta.no_asignadas.forEach(function(nodo) {
	    	  		if(nodo.padre==$("#categorias").val()){
	    	  			
	    	  			$('#no_asignadas').append('<li id="'+nodo.funcion+'" class="sortable-item btn btn-white btn-block">'+nodo.descripcion+'</li>');
	    	  		}
	    		
	    		
	    	});
	    	
	    	//Agregar los eventos de cambio
	    	$('#categorias').change(eventoCambio);
	    	
	    	$("#asignadas").bind("append", function() { console.log('Hello, world!'); });
	    	
	    	function eventoCambio(e){
	    		$('#no_asignadas').empty();
	    		no_asignadas.forEach(function(nodo) {
	    			encontrada=estaAsignada(nodo.funcion);
	    	  		if((nodo.padre==$("#categorias").val()) && (!encontrada)){
	    	  			$('#no_asignadas').append('<li id="'+nodo.funcion+'" class="sortable-item btn btn-white btn-block">'+nodo.descripcion+'</li>');
	    	  		}
	    		});
	    		
	    		
	    	}
	    	
	    	
	    	 
	    	
	    	
	    	$("#btn-guardar").html('Guardar');
	        $("#btn-guardar").prop("disabled",false);	
	    }

    }
    );
    
    
  	function estaAsignada( id){
  		var encontrada=false;
  		
  		 $( "#asignadas li" ).each(function( index ) {
  			 //console.log('evaluando '+id+' contra '+$( this ).attr('id'));
  			 if(parseInt(id)==parseInt($( this ).attr('id'))){
 			
 				encontrada=true;
 				
  			 }  
       	});
  	
  		return encontrada;
  	}

    // Get items
    function getItems(exampleNr)
    {
        var columns = [];

        $(exampleNr + ' ul.sortable-list').each(function(){
            columns.push($(this).sortable('toArray').join(','));                
        });

        return columns.join('|');
    }



    // Render items
    function renderItems(items)
    {
        var html = '';
    
        var columns = items.split('|');
        
        for ( var c in columns )
        {
            html += '<div class="column left';

            if ( c == 0 )
            {
                html += ' first';
            }

            html += '"><ul class="sortable-list">';

            if ( columns[c] != '' )
            {
                var items = columns[c].split(',');
                console.log("renderizar ogw");
                for ( var i in items )
                {
                    html += '<li class="sortable-item" id="' + items[i] + '">Sortable item ' + items[i] + '</li>';
                }
            }

            html += '</ul></div>';
        }

        $('#example-2-4-renderarea').html(html);
       
    }

    // Example 2.1: Get items
    $('#ejemplo .sortable-list').sortable({
        connectWith: '#ejemplo .sortable-list'
    });
    
    
	$('#btn-guardar').click(function(e){
		
	    advertenciaHTML='';
	    //Validación de campos
	    var error =0;
	   
	    if (error==0)
	    {
	        //Campos validados exitosamente
	        //Evitar que presionen el boton 2 veces
	        $("#btn-guardar").html(' Espere <i class="fa fa-spinner fa-spin"></i>');
	        $("#btn-guardar").prop("disabled",true);
	        //Se crea un objeto para ser enviado al servidor 
	        
	        var datos = {q:getItems('#ejemplo'),rol:id};
	       	$.post('/lomas-crm/rol_funcion',datos,callback,'json');
	        function callback(respuesta)
	        {
	            //Se obtiene la respuesta del servidor y se muestra la pagina
	            $("#btn-guardar").prop("disabled",false);
	            $("#btn-guardar").html('Guardar');
	            console.log(respuesta);
	            if(respuesta.resultado=="1"){
	                advertenciaHTML=obtenerAdvertencia('info',respuesta.descripcion);
	            }
	            else{
	                
	            	if(respuesta.resultado=="-100"){
	            		// Sesión caducó
						$('#sesionCaducada').modal({"backdrop":"static","keyboard":false});
	            	}else if(respuesta.resultado=="-101"){
	            		window.location.href="/lomas-crm/errorpermisos.jsp";
	            	}else{
	            		
	            		obtenerAdvertencia('error',respuesta.descripcion);
	            	}
	            	
	            	
	            }
	            
	            
	        }
	        
	
	
	    }
	

	
	e.preventDefault();
  }
  );
	function obtenerAdvertencia(clase, mensaje) {
		  Messenger().post({
  message: mensaje,
  type: clase,
  showCloseButton: true
});

}
</script>

<!--Finaliza la llamada asíncrona al servidor-->

<!-- Mirrored from themes.startbootstrap.com/flex-admin-v1.2/blank.html by HTTrack Website Copier/3.x [XR&CO'2013], Mon, 14 Apr 2014 05:33:19 GMT -->







	<!-- Sesión caducada -->
	<div class="modal fade" id="sesionCaducada" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">Sesión expirada</h4>
				</div>
				<div class="modal-body">La sesión ha expirado. Debe iniciar sesión de nuevo para continuar trabajando</div>
				<div class="modal-footer">
					<a href="/lomas-crm/ingreso.jsp">
						<button type="button" class="btn btn-primary">Iniciar sesión</button>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Sesión caducada -->

</body>


</html>