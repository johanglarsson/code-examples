$(document).ready(function () {
    updateImages()

    modal()

    setInterval(function () {
        updateImages()
        console.log("Hit")
    }, 30000)
});


function updateImages() {
    NProgress.start()
    $.get("images", function (fragment) {
        $("#images").replaceWith(fragment)
        NProgress.done();
    })
}

function modal() {
    $('#aboutModal').on('shown.bs.modal', function () {
        $('#aboutModal').trigger('focus')
    })
}