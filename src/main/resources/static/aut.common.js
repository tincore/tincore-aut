var Aut = function(){
	return {
		options : {
			titleBase : '',
		},
		
		initPre : function($parent) {
		},
		initPost : function($parent) {
			$parent.find(".modal").each(function() {
				var $this = $(this);
				if ($this.find(".error").length	){
					$this.modal();
				}
			});

			$('.form-group .error').parent('.form-group').addClass('has-error')

		}

	}
}();