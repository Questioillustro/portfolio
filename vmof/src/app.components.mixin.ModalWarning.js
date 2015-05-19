var ModalWarningMixin = {
	
	initializeWarning: function (warningMessage, okCallback, cancelCallback) {
		var self = this;
        
        $('#warning-modal-ok').unbind('click').click(function () {
            okCallback();
        });

		$('#warning-modal-cancel').unbind('click').click(function () {
            cancelCallback();
        });

        $('#warning-modal-text').html(warningMessage);
	},

	warningMessage: function ( ) {
		$('#warning-modal').modal( 'show' );
	},

	alertMessage: function ( message, title ) {
		$('#alert-modal-text').html(message);

		$('#alert-modal-title').html(title);

		$('#alert-modal').modal('show');
	}
}

module.exports = ModalWarningMixin;