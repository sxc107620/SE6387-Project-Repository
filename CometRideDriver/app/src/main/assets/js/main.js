var clock;
var capacity = 0;

	// Instantiate a counter
	clock = new FlipClock($('.clock'), 00, {
		clockFace: 'Counter'
	});

	// Attach a click event to a button a increment the clock
	$('.increment').click(function() {
		inc();
	});

	// Attach a click event to a button a decrement the clock
	$('.decrement').click(function() {
		dec();
	});

	$('.reset').click(function() {
		res();
	});

	$('.btn-toggle').click(function() {
		$(this).find('.btn').toggleClass('active');

		if ($(this).find('.btn-warning').size() > 0) {
			$(this).find('.btn').toggleClass('btn-warning');
		}

		$(this).find('.btn').toggleClass('btn-default');

		if ($(this).find('.active').html() == "OFF-Duty") {
			$("#count").hide();
			clock.reset();
			capacity = 0;
			statusUpdate(0);
		} else {
			$("#count").show();
			statusUpdate(1);
		}
	});


function inc() {
	if($('#count').css('display') != 'none') {
		if (capacity != 8) {
			clock.increment();
			capacity++;
			capacityUpdate(capacity);
			passengerOn();
		}
	}
}

function dec() {
	if($('#count').css('display') != 'none') {
		if (capacity != 0) {
			clock.decrement();
			capacity--;
			capacityUpdate(capacity);
		}
	}
}

function res() {
	clock.reset();
	capacity = 0;
	capacityUpdate(capacity);
}

function screenOff() {
	$("#count").hide();
}

function screenOn() {
	$("#count").show();
}

function capacityUpdate(toast) {
  AndroidFunction.currentCapacity(toast);
}

function statusUpdate(toast) {
  AndroidFunction.currentStatus(toast);
}

function passengerOn() {
  AndroidFunction.incrementPressed();
}
 
