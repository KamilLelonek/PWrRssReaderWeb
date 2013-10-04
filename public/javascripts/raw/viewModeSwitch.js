(function() {

	var container = document.getElementById('cbp-vm'), optionSwitch = Array.prototype.slice
			.call(container.querySelectorAll('div.cbp-vm-options > a'));

	function init() {
		optionSwitch.forEach(function(el, i) {
			el.addEventListener('click', function(ev) {
				ev.preventDefault();
				switchView(this);
			}, false);
		});
	}

	function switchView(opt) {
		// remove other view classes and any any selected option
		optionSwitch.forEach(function(el) {
			classie.remove(container, el.getAttribute('data-view'));
			classie.remove(el, 'cbp-vm-selected');
		});
		// add the view class for this option
		classie.add(container, opt.getAttribute('data-view'));
		// this option stays selected
		classie.add(opt, 'cbp-vm-selected');
	}

	init();

})();