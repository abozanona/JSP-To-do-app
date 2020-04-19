(function ($) {
    'use strict';
    $(function () {
        var todoListItem = $('.todo-list');
        var todoListInput = $('.todo-list-input');
        $('.todo-list-add-btn').on("click", function (event) {
            event.preventDefault();

            var item = $(this).prevAll('.todo-list-input').val();
            console.log("ADD", item);
            if (item) {
                todoListItem.append("<li><div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' />" + item + "<i class='input-helper'></i></label></div><i class='remove mdi mdi-close-circle-outline'></i></li>");
                todoListInput.val("");
            }

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

            $(this).closest("li").toggleClass('completed');
            console.log('CHECKED', id, isChecked);
        });

        todoListItem.on('click', '.remove', function () {
            $(this).parent().remove();
        	let id = $(this).data('id');
            console.log('REMOVED', id);
        });

    });
})(jQuery);