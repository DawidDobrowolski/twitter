$(document).ready(function () {

    $( window ).load('click', function(){
        var max = 1500;
        var len = $('#message').val().length;
        var ch = max - len;
        $('#characterLeft').text(ch + ' characters left');
    });


    $('#characterLeft').text('1500 characters left');
    $('#message').keyup(function () {
        var max = 1500;
        var len = $(this).val().length;
        if (len > max) {
            $('#characterLeft').text('You have reached the limit');
            $('#characterLeft').addClass('red');
            $('form :submit').attr("disabled", "disabled");       }
        else {
            var ch = max - len;
            $('#characterLeft').text(ch + ' characters left');
            $('#characterLeft').removeClass('red');
            $('form :submit').removeAttr("disabled");
        }
    });

})