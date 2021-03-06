$(document).ready(function () {

    $('#characterLeft').text('60 characters left');
    $('#message').keyup(function () {
        var max = 60;
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

    $('#deleteModal').on('show.bs.modal', function (event) {
        var tweetId = $(event.relatedTarget).data('tweet-id');
        var commentId = $(event.relatedTarget).data('comment-id');
        $('#deleteId').on('click', function () {
            window.location.href = "/comment/delete/" + tweetId + "/" + commentId;
        })
    });

})