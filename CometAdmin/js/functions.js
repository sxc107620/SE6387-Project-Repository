$(document).ready(function(){
    /* --------------------------------------------------------
	Template Settings
    -----------------------------------------------------------*/
    
    var settings =  '<a id="settings" href="#changeSkin" data-toggle="modal">' +
			'<i class="fa fa-gear"></i> Change Skin' +
		    '</a>' +   
		    '<div class="modal fade" id="changeSkin" tabindex="-1" role="dialog" aria-hidden="true">' +
			'<div class="modal-dialog modal-lg">' +
			    '<div class="modal-content">' +
				'<div class="modal-header">' +
				    '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
				    '<h4 class="modal-title">Change Skin</h4>' +
				'</div>' +
				'<div class="modal-body">' +
				    '<div class="row template-skins">' +
					'<a data-skin="skin-blur-violate" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-violate.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-lights" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-lights.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-city" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-city.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-greenish" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-greenish.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-night" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-night.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-blue" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-blue.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-sunny" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-sunny.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-cloth" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-cloth.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-tectile" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-tectile.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-chrome" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-chrome.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-ocean" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-ocean.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-sunset" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-sunset.jpg" alt="">' +
					'</a>' +
					'<a data-skin="skin-blur-yellow" class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-yellow.jpg" alt="">' +
					'</a>' +
					'<a  data-skin="skin-blur-kiwi"class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-kiwi.jpg" alt="">' +
					'</a>' +
					'<a  data-skin="skin-blur-nexus"class="col-sm-2 col-xs-4" href="">' +
					    '<img src="img/skin/skin-nexus.jpg" alt="">' +
					'</a>' +
				    '</div>' +
				'</div>' +
			    '</div>' +
			'</div>' +
		    '</div>';
    $('#main').prepend(settings);
	
	skin = readCookie('style');
	if(skin == null) {
		createCookie('style', 'skin-blur-violate');
		location.reload();
	}
	$('body').attr('id', skin);
    $('body').on('click', '.template-skins > a', function(e){
	e.preventDefault();
	var skin = $(this).attr('data-skin');
	createCookie('style', skin);
	skin = readCookie('style');
	$('body').attr('id', skin);
	$('#changeSkin').modal('hide');
    });
	
	/* --------------------------------------------------------
     Login + Sign up
    -----------------------------------------------------------*/
    (function(){
	$('body').on('click touchstart', '.box-switcher', function(e){
	    e.preventDefault();
	    var box = $(this).attr('data-switch');
	    $(this).closest('.box').toggleClass('active');
	    $('#'+box).closest('.box').addClass('active'); 
		$('.errorMessage').html("");
	});
    })();
	 
    /* --------------------------------------------------------
	Components
    -----------------------------------------------------------*/
    (function(){        
        /* Tab */
		if($('.tab')[0]) {
			$('.tab a').click(function(e) {
			e.preventDefault();
			$(this).tab('show');
			});
		}
		
		/* Tool Tip */
		$('[data-toggle="tooltip"]').tooltip();
    })();

    /* --------------------------------------------------------
	Sidebar + Menu
    -----------------------------------------------------------*/
    (function(){
        /* Menu Toggle */
        $('body').on('click touchstart', '#menu-toggle', function(e){
            e.preventDefault();
            $('html').toggleClass('menu-active');
            $('#sidebar').toggleClass('toggled');
            //$('#content').toggleClass('m-0');
        });
         
        /* Active Menu */
        $('#sidebar .menu-item').hover(function(){
            $(this).closest('.dropdown').addClass('hovered');
        }, function(){
            $(this).closest('.dropdown').removeClass('hovered');
        });

        /* Prevent */
        $('.side-menu .dropdown > a').click(function(e){
            e.preventDefault();
        });
	

    })();
   
    /* --------------------------------------------------------
	Custom Scrollbar
    -----------------------------------------------------------*/
    (function() {
	if($('.overflow')[0]) {
	    var overflowRegular, overflowInvisible = false;
	    overflowRegular = $('.overflow').niceScroll();
	}
    })();

    /* --------------------------------------------------------
	Calendar
    -----------------------------------------------------------*/
    (function(){
	
        //Sidebar
        if ($('#sidebar-calendar')[0]) {
            var date = new Date();
            var d = date.getDate();
            var m = date.getMonth();
            var y = date.getFullYear();
            $('#sidebar-calendar').fullCalendar({
                editable: false,
                events: [],
                header: {
                    left: 'title'
                }
            });
        }

        //Content widget
        if ($('#calendar-widget')[0]) {
            $('#calendar-widget').fullCalendar({
                header: {
                    left: 'title',
                    right: 'prev, next',
                    //right: 'month,basicWeek,basicDay'
                },
                editable: true,
                events: [
                    {
                        title: 'All Day Event',
                        start: new Date(y, m, 1)
                    },
                    {
                        title: 'Long Event',
                        start: new Date(y, m, d-5),
                        end: new Date(y, m, d-2)
                    },
                    {
                        title: 'Repeat Event',
                        start: new Date(y, m, 3),
                        allDay: false
                    },
                    {
                        title: 'Repeat Event',
                        start: new Date(y, m, 4),
                        allDay: false
                    }
                ]
            });
        }

    })();

    /* --------------------------------------------------------
	RSS Feed widget
    -----------------------------------------------------------*/
    (function(){
	if($('#news-feed')[0]){
	    $('#news-feed').FeedEk({
		FeedUrl: 'https://script.google.com/macros/s/AKfycbysqknJ-9VT2oMhPJzRuJi9kvhiNLO08si41oNm-vWJ0yey0Q_U/exec?581725519349088256',
		MaxCount: 5,
		ShowDesc: false,
		ShowPubDate: true,
		DescCharacterLimit: 0
	    });
	}
    })();
	


    
});

/* --------------------------------------------------------
Date Time Widget
-----------------------------------------------------------*/
(function(){
    var monthNames = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];
    var dayNames= ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"]

    // Create a newDate() object
    var newDate = new Date();

    // Extract the current date from Date object
    newDate.setDate(newDate.getDate());

    // Output the day, date, month and year
    $('#date').html(dayNames[newDate.getDay()] + " " + newDate.getDate() + ' ' + monthNames[newDate.getMonth()] + ' ' + newDate.getFullYear());

    setInterval( function() {

        // Create a newDate() object and extract the seconds of the current time on the visitor's
        var seconds = new Date().getSeconds();

        // Add a leading zero to seconds value
        $("#sec").html(( seconds < 10 ? "0":"" ) + seconds);
    },1000);

    setInterval( function() {

        // Create a newDate() object and extract the minutes of the current time on the visitor's
        var minutes = new Date().getMinutes();

        // Add a leading zero to the minutes value
        $("#min").html(( minutes < 10 ? "0":"" ) + minutes);
    },1000);

    setInterval( function() {

        // Create a newDate() object and extract the hours of the current time on the visitor's
        var hours = new Date().getHours();

        // Add a leading zero to the hours value
        $("#hours").html(( hours < 10 ? "0" : "" ) + hours);
    }, 1000);
	})();
	
/* --------------------------------------------------------
Custom Code
-----------------------------------------------------------*/

	//Forget Password
	$("#resPass").click(function(e) {
		e.preventDefault();
		regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		email = $("#resetEmail").val();
		errorList = '';
		if(regex.test(email) == 0) errorList = errorList + "Invalid email address";
		if(errorList != '') {
			$('.errorMessage').html("<div class='alert alert-danger' role='alert'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button> <strong>"+errorList+"</strong><br/></div>");
		} else {
			$.post("index.php",{ mail: email }, function(){
				location.reload();
			});
		}
		
	})
	
	//Edit Profile
	$("#imgInfo").on('submit',(function(e) {
		e.preventDefault();
		errorList = '';
		pass = $("#newPassChange").val();
		rpass = $("#newCPassChange").val();
		imgtType = $('#displayPic')[0].files[0];
		if(typeof(imgtType) != 'undefined')
		if(imgtType.type != 'image/jpeg' && imgtType.type != 'image/png' && imgtType.type != 'image/gif') errorList = errorList + "<li>Upload a proper image.</li>";
		if(pass != rpass) errorList = errorList + "<li>Passwords don't match </li>";
		if(errorList != '') {
			$('.errorMessage').html("<div class='alert alert-danger' role='alert'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button> <strong><ul>"+errorList+"</ul></strong><br/></div>");
		} else {
			$.ajax({
				url: "uploadimage.php", // Url to which the request is send
				type: "POST",             // Type of request to be send, called as method
				data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
				contentType: false,       // The content type used when sending data to the server.
				cache: false,             // To unable request pages to be cached
				processData:false,        // To send DOMDocument or non processed data file it is set to false
				success: function(data)   // A function to be called if request succeeds
				{
					//console.log("Done");
					location.reload();
				}
			});
		}
	}));

	//Cookies
	function createCookie(name,value,days) {
		var days = 365;
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
		document.cookie = name+"="+value+expires+"; path=/";
	}

	function readCookie(name) {
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for(var i=0;i < ca.length;i++) {
			var c = ca[i];
			while (c.charAt(0)==' ') c = c.substring(1,c.length);
			if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
		}
		return null;
	}
	
	//sort list
	$("#adminList a").sort(asc_sort).appendTo('#adminList');
	$("#driverList a").sort(asc_sort).appendTo('#driverList');
	$("#cabList a").sort(asc_sort).appendTo('#cabList');
	
	
	
	
	//pageName
	pageName = location.pathname.split('/').slice(-1)[0];
	
	
	//load details in modal box - admin, driver
	$('#modalDetails').on('show.bs.modal', function(e) {
		$('.errorMessage').html("");
		var name = $(e.relatedTarget).html();
		var email = $(e.relatedTarget).data('email');
		var type = $(e.relatedTarget).data('type');
		var date = $(e.relatedTarget).data('date');
		
		var status = $(e.relatedTarget).data('status');
		var latitude = $(e.relatedTarget).data('latitude');
		var longitude = $(e.relatedTarget).data('longitude');
		var route = $(e.relatedTarget).data('route');
		var capacity = $(e.relatedTarget).data('capacity');
		var passengers = $(e.relatedTarget).data('passengers');
		
		if(type == "admin" || type == "driver") {
		$(e.currentTarget).find('.modal-title').html(name+"'s Details"+"<span id='dtype' data-type='"+type+"' data-email='"+email+"' data-name='"+name+"'></span>");
		$(e.currentTarget).find('.modal-body').html("<table class='tile table table-bordered table-striped'><tbody><tr><td>User Name</td><td>"+name+"</td></tr><tr><td>Email</td><td>"+email+"</td></tr><tr><td>Status</td><td>"+type+"</td></tr><tr><td>Join Date</td><td>"+date+"</td></tr></tbody></table>");
		if(type == "admin")
		{
			if($('#cAdmin').data('type') == 'superadmin') {
				$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='deleteUser' value='Delete' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
			}
				else $(e.currentTarget).find('.modal-footer').html("<button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
		}
		else
			$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='deleteUser' value='Delete' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
		} else {
			$(e.currentTarget).find('.modal-title').html(name+"'s Details<span id='snum' data-number='"+name+"'></span>");
			$(e.currentTarget).find('.modal-body').html("<table class='tile table table-bordered table-striped'><tbody><tr><td>Shuttle's Number</td><td>"+name+"</td></tr><tr><td>Shuttle's Status</td><td>"+status+"</td></tr><tr><td>Shuttle's Type</td><td>"+type+"</td></tr><tr><td>Shuttle's Location</td><td>"+latitude+", "+longitude+"</td></tr><tr><td>Shuttle's Current Route</td><td>"+route+"</td></tr><tr><td>Shuttle's Current Capacity</td><td>"+capacity+"</td></tr><tr><td>Used by</td><td>"+passengers+" Students</td></tr></tbody></table>");
			$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='deleteShuttle' value='Delete' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
		}
	});
	
	//load create modal box - admin, driver
	$('#modalCreate').on('show.bs.modal', function(e) {
		$('.errorMessage').html("");
		var type = $(e.relatedTarget).data('type');
		var num = $(e.relatedTarget).data('name');
		if(type == "admin" || type == "driver") {
		$(e.currentTarget).find('.modal-title').html("Create new "+type+"<span id='ctype' data-type='"+type+"'></span>");
		scheduler='';
		//scheduler = "<tr><td>Schedule</td><td><select class='form-control'><option selected='' disabled='' hidden='' value=''>Monday</option><option>Monday</option><option>Tuesday</option><option>Wednesday</option><option>Thursday</option><option>Friday</option></select>";
		//scheduler = scheduler + "<input class='startTime input-sm form-control' placeholder='Start Time' value='' data-default=''><input class='endTime input-sm form-control' placeholder='End Time' value='' data-default=''></td></tr>";
		$(e.currentTarget).find('.modal-body').html("<form role='form'><table class='tile table table-bordered table-striped'><tbody><tr><td>User Name</td><td><input class='input-sm form-control' id='newName' type='text' autocomplete='off' maxlength='20'></td></tr><tr><td>Email</td><td><input class='input-sm form-control' id='newMail' type='text' ></td></tr><tr><td>Password</td><td><input type='password' id='newPass' class='input-sm form-control' name='password' id='password' ></td></tr><tr><td>Confirm Password</td><td><input type='password' id='newCPass' class='input-sm form-control' ></td></tr>"+scheduler+"</tbody></table>");
		$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='createNew' value='Save' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button></form>");
		} else {
		$(e.currentTarget).find('.modal-title').html("Create new shuttle entry");
		$(e.currentTarget).find('.modal-body').html("<table class='tile table table-bordered table-striped'><tbody><tr><td>Shuttle's Number</td><td><input class='input-sm form-control' id='newNumber' type='text' autocomplete='off' maxlength='3' onkeypress='return event.charCode >= 48 && event.charCode <= 57'></td></tr><tr><td>Shuttle's Type</td><td><select class='form-control input-sm' id='newShuttleType'><option selected='true' style='display:none;' value=''>Choose a shuttle type</option><option value='7-seater'>7-seater</option><option value='9-seater'>9-seater</option></select></td></tr></tbody></table>");
		$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='createNewShuttle' value='Save' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button></form>");	
		}
	});
	
	//Clock
	$(document.body).on('focus',".startTime", function(){
		$(this).clockpicker();
	});
	
	$(document.body).on('focus',".endTime", function(){
		$(this).clockpicker();
	});
	
	$(document).on( "click", "#createNewShuttle", function() {
		errorList = '';
		number = $("#newNumber").val();
		type = $("#newShuttleType").val();
		if(number.length == 0 || type.length == 0 ) errorList = errorList + "<li>Please fill all the boxes</li>";
			if(errorList != '') {
				$('.errorMessage').html("<div class='alert alert-danger' role='alert'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button> <strong><ul>"+errorList+"</ul></strong><br/></div>");
			} else {
				$.post("users.php",{ n: number, shuttle: type }, function(){
					$('.modal-body').html("<h3 class='block-title'>New shuttle Added</h3>");
					$('.modal-footer').html("<button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
					$("#cabList").append("<a data-toggle='modal' data-name='"+number+"' data-status='off-duty' data-type='"+type+"' data-latitude='' data-longitude='' data-route='' data-capacity='' data-passengers='' href='#modalDetails' class='list-group-item'>"+number+"</a>");
					$("#cabList a").sort(asc_sort).appendTo('#cabList');
					
				});
			}
	});
	
	$(document).on( "click", "#createNew", function() {
		errorList = '';
		regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		uList = Array();
		eList = Array();
		type = $("#ctype").data('type');
		username = $("#newName").val();
		email = $("#newMail").val();
		pass = $("#newPass").val();
		rpass = $("#newCPass").val();
		//if(type == 'admin') {
			$("#adminList a").each(function() {
				eList.push($(this).data('email'));
				uList.push($(this).data('name'));
			});
		//} else {
			$("#driverList a").each(function() {
				eList.push($(this).data('email'));
				uList.push($(this).data('name'));
			});
		//}
			if(jQuery.inArray(username, uList)!==-1) errorList = errorList + "<li>Username already exists</li>";
			if(jQuery.inArray(email, eList)!==-1) errorList = errorList + "<li>Email already exists</li>";
			if(regex.test(email) == 0) errorList = errorList + "<li>Invalid email address</li>";
			if(pass != rpass) errorList = errorList + "<li>Passwords don't match </li>";
			if(username.length == 0 || email.length == 0 || pass.length == 0 || rpass.length == 0 ) errorList = errorList + "<li>Please fill all the boxes</li>";
			if(errorList != '') {
				$('.errorMessage').html("<div class='alert alert-danger' role='alert'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button> <strong><ul>"+errorList+"</ul></strong><br/></div>");
			} else {
				$.post("users.php",{ uname: username, mail: email, p: pass, t: type }, function(){
					$('.modal-body').html("<h3 class='block-title'>New user Added</h3>");
					$('.modal-footer').html("<button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
					if(type == 'admin') {
						$("#adminList").append("<a data-toggle='modal' data-email='"+email+"' data-type='admin' data-date='"+new Date().toISOString().slice(0, 19).replace('T', ' ')+"' data-name='"+username+"' href='#modalDetails' class='list-group-item'>"+username[0].toUpperCase() + username.substring(1)+"</a>");
						$("#adminList a").sort(asc_sort).appendTo('#adminList');
					} else {
						$("#driverList").append("<a data-toggle='modal' data-email='"+email+"' data-type='driver' data-date='"+new Date().toISOString().slice(0, 19).replace('T', ' ')+"' data-name='"+username+"' href='#modalDetails' class='list-group-item'>"+username[0].toUpperCase() + username.substring(1)+"</a>");
						$("#driverList a").sort(asc_sort).appendTo('#driverList');
					}
				});
			}
	});
	
	$(document).on( "click", "#deleteUser", function() {
		$('.modal-body').html("<h3 class='block-title'>Are you sure you want to perform this action?</h3>");
		$('.modal-footer').html("<button type='button' class='btn btn-sm' id='sureDelete'>Yes</button><button type='button' class='btn btn-sm' data-dismiss='modal'>No</button>");
	});
	
	$(document).on( "click", "#deleteShuttle", function() {
		$('.modal-body').html("<h3 class='block-title'>Are you sure you want to perform this action?</h3>");
		$('.modal-footer').html("<button type='button' class='btn btn-sm' id='sureDeleteShuttle'>Yes</button><button type='button' class='btn btn-sm' data-dismiss='modal'>No</button>");
	});
	
	$(document).on( "click", "#sureDeleteShuttle", function() {
		name = $("#snum").data('number');
		$.post("users.php",{ snum: name }, function(){
			$('.modal-body').html("<h3 class='block-title'>"+name+" was deleted from the system.</h3>");
			$('.modal-footer').html("<button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
			$("#cabList a").filter(function() { return $.text([this]) === name; }).remove();
		});
	});
	
	$(document).on( "click", "#sureDelete", function() {
		email = $("#dtype").data('email');
		name = $("#dtype").data('name');
		type = $("#dtype").data('type');
		$.post("users.php",{ e: email, status: type }, function(){
			$('.modal-body').html("<h3 class='block-title'>"+name+" was deleted from the system.</h3>");
			$('.modal-footer').html("<button type='button' class='btn btn-sm' data-dismiss='modal'>Close</button>");
			if(type == 'admin') 
				$("#adminList a").filter(function() { return $.text([this]) === name; }).remove();
			else
				$("#driverList a").filter(function() { return $.text([this]) === name; }).remove();
		});
	});
	
	$("#openEditor").click(function(e) {
		cList = Array();
		nList = Array();
		errorList = '';
		selectedColor = $("#selected-color").val();
		name = $("#newRoute").val();
		$(".tab-pane div").each(function() {
			cList.push($(this).data('color'));
			nList.push($(this).data('rname'));
		});
		if(jQuery.inArray(selectedColor, cList)!==-1) errorList = errorList + "<li>Color is already taken by a different route</li>";
		if(jQuery.inArray(name, nList)!==-1) errorList = errorList + "<li>Route name exists</li>";
		if(selectedColor.length == 0 || name.length == 0) errorList = errorList + "<li>Please fill all the boxes</li>";
			if(errorList != '') {
				$('.errorMessage').html("<div class='alert alert-danger' role='alert'><button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button> <strong><ul>"+errorList+"</ul></strong><br/></div>");
			} else {
				$('#modalCreateRouteDialog').modal('hide');
				$('#modalCreateRoute').modal('show');
			}
	});
	
	$("#saveMap").click(function(e) {
		selectedColor = $("#selected-color").val();
		name = $("#newRoute").val();
		$.post("routes.php",{ n: name, s: selectedColor, p: encodeStringPoints, l: encodeStringLine, c: encodeStringCurve }, function(){
			location.reload();
		});
	});
	
	$('#modalConfirm').on('show.bs.modal', function(e) {
		$(e.currentTarget).find('.modal-title').html("Confirmation Box");
		$(e.currentTarget).find('.modal-body').html("Are you sure that you want to delete this route?");
		$(e.currentTarget).find('.modal-footer').html("<input type='submit' class='btn btn-sm' id='deleteRoute' value='Yes' /> <button type='button' class='btn btn-sm' data-dismiss='modal'>No</button></form>");	
	});
	
	$(document).on( "click", "#deleteRoute", function() {
		id = $(".tab-pane.active div").data('rid');
		$.post("routes.php",{ i: id }, function(){
			location.reload();
		});
	});
	
	function asc_sort(a, b){
		return ($(b).text()) < ($(a).text()) ? 1 : -1;    
	}
	
	//Google Map
	var directionsDisplay;
	var directionsService = new google.maps.DirectionsService();
	map= new Array();
	var editMap;
	var rowCount = $("#routeCount").data('count');
	undo = new Array();
	redo = new Array();
	loadCurve = new Array();
	var encodeStringCurve;
	var encodeStringLine = '';
	var encodeStringPoints;
	var poly = [];
	var lineCounter = -1;
	
	function plotter() {
		var mapOptions = {
			panControl: true, //enable pan Control
            zoomControl: true, //enable zoom control
            scaleControl: true, // enable scale control
            mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
		}
	  
	  for(i=0; i<rowCount; i++) {
	  map[i] = new google.maps.Map(document.getElementById(i+'_Map'), mapOptions);
	  lines = $("#"+i+"_Map").data('lines');
	  slines = lines.split('+');
	  curves = $("#"+i+"_Map").data('curves');
	  savepoints = $("#"+i+"_Map").data('savepoints');
	  color = $("#"+i+"_Map").data('color');
	  
		if (savepoints.length != 0) {
			points = [];
			points = google.maps.geometry.encoding.decodePath(savepoints);
			for(j=0; j<points.length; j++) {
				var marker = new google.maps.Marker({
					position: points[j],
					map: map[i],
					animation: google.maps.Animation.DROP, //bounce animation
					icon: "img/map/marker.png" //custom pin icon
				});
			}
		}
		groupPoints = [];
		if (lines.length != 0) {
			$.each(slines, function( index, value ) {
				if(value.length != 0) {
					var linePath = new google.maps.Polyline({
						path: google.maps.geometry.encoding.decodePath(value),
						geodesic: true,
						strokeColor: color,
						strokeOpacity: 1.0,
						strokeWeight: 2,
						map: map[i]
					});
					$.each(linePath.getPath().getArray(), function( index, value ) {
							groupPoints.push(value);
					});
				}
			});
		}
		if (curves.length != 0) {
			var curvePath = new google.maps.Polyline({
				path: google.maps.geometry.encoding.decodePath(curves),
				geodesic: true,
				strokeColor: color,
				strokeOpacity: 1.0,
				strokeWeight: 2,
				map: map[i]
			});
			if(groupPoints.length == 0) groupPoints = curvePath.getPath().getArray();
			else {
				$.each(curvePath.getPath().getArray(), function( index, value ) {
					groupPoints.push(value);
				});
			}
		}
		var bounds = new google.maps.LatLngBounds();
		for (var n = 0; n < groupPoints.length ; n++){
			bounds.extend(groupPoints[n]);
		}
		map[i].fitBounds(bounds);
	  }
	}

	function initialize() {
		plotter();
	}
	
	function tracker() {
		plotter();
	}
	
	function mapEditor() {
		var UTD = new google.maps.LatLng(32.98594891, -96.7509511);
		var mapOptions = {
			zoom: 17,
			center: UTD,
			panControl: true, //enable pan Control
            zoomControl: true, //enable zoom control
            scaleControl: true, // enable scale control
			mapTypeControlOptions:{ 
	        		style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
	        },
            mapTypeId: google.maps.MapTypeId.ROADMAP // google map type
		}
            
		editMap = new google.maps.Map(document.getElementById('editMap'), mapOptions);
		
	}
	
	$('.mapTabs').on('shown.bs.tab', function(e) {
		initialize();
    });
	
	
	$('#modalCreateRoute').on('show.bs.modal', function(e) {
		setTimeout( function(){google.maps.event.trigger(editMap, "resize");} , 400);
	});
	
	$("#drag").click(function(e) {
		google.maps.event.clearListeners(editMap, 'click');
		editMap.setOptions({ draggableCursor: 'url(http://maps.google.com/mapfiles/openhand.cur), move' });
	});
	

		
	$("#line").click(function(e) {
		tempLineHolder = [];
		selectedColor = $("#selected-color").val();
		google.maps.event.clearListeners(editMap, 'click');
		editMap.setOptions({ draggableCursor: 'crosshair' });
		list = new Array();
		lineCounter++;
		poly[lineCounter] = new google.maps.Polyline({
			map: editMap,
			geodesic: true,
			strokeColor: selectedColor,
			strokeOpacity: 1.0,
			strokeWeight: 2
		});
		google.maps.event.addListener(editMap, "click", drawLine);
	});
	
	listCurvePts = new Array();
	$("#curve").click(function(e) {
		tempLineHolder = [];
		google.maps.event.clearListeners(editMap, 'click');
		editMap.setOptions({ draggableCursor: 'crosshair' });
		google.maps.event.addListener(editMap, "click", function(event) {
			var lat = event.latLng.lat();
			var lng = event.latLng.lng();
			listCurvePts.push(lat+","+lng);
			if(listCurvePts.length > 1) drawCurve(listCurvePts[listCurvePts.length-2], listCurvePts[listCurvePts.length-1]);
		});
	});
	
	$("#eraser").click(function(e) {
		
	});
	
	//temp code
	//$('#modalCreateRoute').modal('show');

	var tempLineHolder = [];
	var tempCurveHolder = [];
	var listCurvePtsHolder = [];
	var curveStack = [];
	var mapLength = [];
	$("#undo").click(function(e) {
		if(undo.length != 0) {
			var type = undo.pop();
			redo.push(type);
			if(type == 'Line' && lineHolder[lineCounter].j.length >= 1) {
				tempLineHolder.push(lineHolder[lineCounter].j.pop());
		
				poly[lineCounter].setPath(lineHolder[lineCounter].j);
				//encodeStringLine = google.maps.geometry.encoding.encodePath(linesArray);
				encodeStringLine = '';
				$.each(lineHolder, function( index, value ) {
					encodeStringLine = encodeStringLine + "+" + google.maps.geometry.encoding.encodePath(value)
				});
			}
			if(type == 'Line' && lineHolder[lineCounter].j.length == 1) {
				tempLineHolder.push(lineHolder[lineCounter].j.pop());
				mapLength[lineCounter] = tempLineHolder.length;
				
				if(lineCounter >= 0) lineCounter--;
				
				//encodeStringLine = google.maps.geometry.encoding.encodePath(linesArray);
				encodeStringLine = '';
				$.each(lineHolder, function( index, value ) {
					encodeStringLine = encodeStringLine + "+" + google.maps.geometry.encoding.encodePath(value)
				});
			}
			if(type == 'Curve' && curveStack.length >= 1) {
				temp = curveStack.pop();
				listCurvePtsHolder.push(listCurvePts.pop());
				tempCurveHolder.push(temp);
				temp.setMap(null);
				var encodeCurve = [];
				$.each(curveStack, function( index, value ) {
					encodeCurve.push.apply(encodeCurve, value.getPath().getArray());
					//console.log(encodeCurve);
				});
				encodeStringCurve = google.maps.geometry.encoding.encodePath(encodeCurve);
			}
		}		
	});
	
	$("#redo").click(function(e) {
		if(redo.length != 0) {
			var type = redo.pop(); 
			undo.push(type);
			
			if(type == 'Line' && tempLineHolder.length == mapLength[lineCounter]) {
				lineHolder[lineCounter].j.push(tempLineHolder.pop());
				poly[lineCounter].setPath(lineHolder[lineCounter].j);
				lineCounter++;
				//encodeStringLine = google.maps.geometry.encoding.encodePath(linesArray);
				$.each(lineHolder, function( index, value ) {
					encodeStringLine = encodeStringLine + "+" + google.maps.geometry.encoding.encodePath(value)
				});

			}
			
			if(type == 'Line' && tempLineHolder.length >= 1) {
				if(lineCounter == -1) lineCounter = 0;
				lineHolder[lineCounter].j.push(tempLineHolder.pop());
				poly[lineCounter].setPath(lineHolder[lineCounter].j);
				//encodeStringLine = google.maps.geometry.encoding.encodePath(linesArray);
				$.each(lineHolder, function( index, value ) {
					encodeStringLine = encodeStringLine + "+" + google.maps.geometry.encoding.encodePath(value)
				});

			} 
			if(type == 'Curve' && tempCurveHolder.length >= 1) {
				temp = tempCurveHolder.pop();
				listCurvePts.push(listCurvePtsHolder.pop());
				curveStack.push(temp);
				temp.setMap(editMap);
				var encodeCurve = [];
				$.each(curveStack, function( index, value ) {
					encodeCurve.push.apply(value.getPath().getArray());
				});
				encodeStringCurve = google.maps.geometry.encoding.encodePath(encodeCurve);
			}
		}
	});
	
	$("#clear").click(function(e) {
		tempLineHolder = [];
		tempCurveHolder = [];
		mapEditor();
	});

	$("#marker").click(function(e) {
		google.maps.event.clearListeners(editMap, 'click');
		editMap.setOptions({ draggableCursor: 'crosshair' });
		google.maps.event.addListener(editMap, 'click', addSavePoints);
	});
	
	var markers = [];
	var ponits = new google.maps.MVCArray;
	function addSavePoints(event) {
		ponits.push(event.latLng);
        encodeStringPoints = google.maps.geometry.encoding.encodePath(ponits);
		undo.push("Points");
		var marker = new google.maps.Marker({
			position: event.latLng, //map Coordinates where user right clicked
			map: editMap,
			draggable:true, //set marker draggable 
			animation: google.maps.Animation.DROP, //bounce animation
			icon: "img/map/pin_green.png" //custom pin icon
		});
		markers.push(marker);

		google.maps.event.addListener(marker, 'click', function() {
			marker.setMap(null);
			for (var i = 0, I = markers.length; i < I && markers[i] != marker; ++i);
			markers.splice(i, 1);
			ponits.removeAt(i);
			encodeStringPoints = google.maps.geometry.encoding.encodePath(ponits);
        });
		
		google.maps.event.addListener(marker, 'dragend', function() {
			for (var i = 0, I = markers.length; i < I && markers[i] != marker; ++i);
			ponits.setAt(i, marker.getPosition());
			encodeStringPoints = google.maps.geometry.encoding.encodePath(ponits);
			undo.push("Points");
			//console.log(ponits);
        });
	}
	
	var linesArray = new google.maps.MVCArray;
	var lineHolder = new Array();
	function drawLine(event) {
		var encoder = new google.maps.MVCArray;
		linesArray = poly[lineCounter].getPath();
		linesArray.push(event.latLng);
		lineHolder[lineCounter] = linesArray;
		encodeStringLine = '';
		$.each(lineHolder, function( index, value ) {
			encodeStringLine = encodeStringLine + "+" + google.maps.geometry.encoding.encodePath(value)
		});
		
		//console.log(encodeStringLine);
		//encodeStringLine = google.maps.geometry.encoding.encodePath(linesArray);
		tempLineHolder = [];
		tempCurveHolder = [];
		undo.push("Line");
	}
	
	
	function drawCurve(from, to) {
		selectedColor = $("#selected-color").val();
		tempLineHolder = [];
		tempCurveHolder = [];
		from = from.split(',');
		to = to.split(',');
		var service = new google.maps.DirectionsService();
		var poly = new google.maps.Polyline({
			map: editMap,
			geodesic: true,
			strokeColor: selectedColor,
			strokeOpacity: 1.0,
			strokeWeight: 2
	});
		var src = new google.maps.LatLng(parseFloat(from[0]), parseFloat(from[1]));
        var des = new google.maps.LatLng(parseFloat(to[0]), parseFloat(to[1]));
		service.route({
			origin: src,
			destination: des,
			travelMode: google.maps.DirectionsTravelMode.DRIVING
		}, function (result, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				var curvesArray = new google.maps.MVCArray;
				for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
					curvesArray.push(result.routes[0].overview_path[i]);
					loadCurve.push(result.routes[0].overview_path[i]);
				}
				encodeStringCurve = google.maps.geometry.encoding.encodePath(loadCurve);
				poly.setPath(curvesArray);
				curveStack.push(poly);
				undo.push("Curve");
			}
			
		});
	}
	
	var shuttleMarkers = [];
	var infowindow = [];
	var prevLt = [];
	var prevLn = [];
	function sse() {
		var image = 'img/car.png';
		var imageI = 'img/carI.png';
		if(typeof(EventSource) !== "undefined") {
			var source = new EventSource("./php/includes/sse.php");
			source.onmessage = function(event) {
				shuttleLocations = jQuery.parseJSON(event.data);
				/*if(shuttleMarkers.length != 0) {
					for(i=0; i<shuttleMarkers.length; i++)
						shuttleMarkers[i].setMap(null);
				}*/
				
				for(var count in shuttleLocations) {
					/*if((prevLt[count] != shuttleLocations[count].Latitude) && (prevLn[count] != shuttleLocations[count].Longitude)) {*/
						if(typeof(shuttleMarkers[count]) != 'undefined') shuttleMarkers[count].setMap(null);
						var shuttle = new google.maps.LatLng(shuttleLocations[count].Latitude, shuttleLocations[count].Longitude);
						cap = shuttleLocations[count].Capacity+"/"+shuttleLocations[count].Type;
						var options = {
							position: shuttle,
							disableAutoPan: true
						};
						infowindow[count] = new google.maps.InfoWindow(options);
						if(shuttleLocations[count].Status == 'on-duty') {
							var contentString = 
								'<div id="infowindow">' +
								'Capacity: ' + cap +
								'</div>';
							shuttleMarkers[count] = new google.maps.Marker({
								icon: image,
								zIndex:  google.maps.Marker.MAX_ZINDEX + 1, 
								position: shuttle,
								map: map[(shuttleLocations[count].Route)-1]
							});
						} else {
							var contentString = 
								'<div id="infowindow">' + shuttleLocations[count].Status +	'</div>';
							shuttleMarkers[count] = new google.maps.Marker({
								icon: imageI,
								zIndex:  google.maps.Marker.MAX_ZINDEX + 1,
							    position: shuttle,
								map: map[(shuttleLocations[count].Route)-1]
							});
						}
						/*console.log(map[(shuttleLocations[count].Route)-1].getBounds());*/
							
						
						infowindow[count].setContent(contentString);
						infowindow[count].open(map[(shuttleLocations[count].Route)-1], shuttleMarkers[count]);
						google.maps.event.addListener(infowindow[count], 'domready', function(){
							$(".gm-style-iw").next("div").hide();
						});
						
						/*prevLt[count] = shuttleLocations[count].Latitude;
						prevLn[count] = shuttleLocations[count].Longitude;
					}*/
				}
			};
		} 
	}
	
	
	if(pageName == "routes.php") {
		google.maps.event.addDomListener(window, 'load', initialize);
		google.maps.event.addDomListener(window, 'load', mapEditor);
	}
	
	if(pageName == "track.php") {
		google.maps.event.addDomListener(window, 'load', tracker);
		sse();
	}
	
