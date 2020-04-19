(function ($) {
    'use strict';
    $(function () {
    	var BASE_URL = 'http://localhost:8080/JSP_To-do_List/main';
        var todoListItem = $('.todo-list');
        var todoListInput = $('.todo-list-input');
        $('.todo-list-add-btn').on("click", function (event) {
            event.preventDefault();

            var item = $(this).prevAll('.todo-list-input').val();
            let jsonData = JSON.stringify({
				"value": item,
				"isChecked": false
            });
            $.ajax({
        	  type: "POST",
        	  url: BASE_URL,
        	  data: jsonData,
        	  success: function(res){
        		  if(!res.success){
        			  alert("Error while adding ticket");
        			  return;
        		  }
                  let newId = res.id;
                  if (item) {
                      todoListItem.append("<li><div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' data-id='" + newId + "' />" + item + "<i class='input-helper'></i></label></div><i class='remove mdi mdi-close-circle-outline' data-id='" + newId + "'></i></li>");
                      todoListInput.val("");
                  }
        	  },
        	  dataType: "json",
        	});
        });

        todoListItem.on('change', '.checkbox', function () {
        	let isChecked = false;
        	let id = $(this).data('id');
            if ($(this).attr('checked')) {
                $(this).removeAttr('checked');
                isChecked = false;
            } else {
                $(this).attr('checked', 'checked');
                isChecked = true;
            }
            
            let _this = this;
            let jsonData = JSON.stringify({
				"ticketId": id,
				"isChecked": isChecked
            });
            $.ajax({
          	  type: "PUT",
          	  url: BASE_URL,
          	  data: jsonData,
          	  success: function(res){
          		  if(!res.success){
          			  alert("Error while Checking ticket");
          			  return;
          		  }
                  $(_this).closest("li").toggleClass('completed');
          	  },
          	  dataType: "json",
          	});
        });

        todoListItem.on('click', '.remove', function () {
        	let id = $(this).data('id');
        	let _this = this;
            $.ajax({
          	  type: "DELETE",
          	  url: BASE_URL + "?ticketId=" + id,
          	  success: function(res){
          		  if(!res.success){
          			  alert("Error while deleting ticket");
          			  return;
          		  }
                  $(_this).parent().remove();
          	  },
          	  dataType: "json",
          	});
        });

    });
})(jQuery);