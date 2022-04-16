$(document).ready(function () {

    $.validator.addMethod("caracteres", function(value, element) {
        return this.optional(element) || /(^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ,.\-\/+=@_ ]*$)/.test(value);
    }, "Ingrese solo caracteres validos");

    $("#chat").validate({
        rules: {
            comentario: {
                required: true,
                caracteres: ""
            }
        },
        messages:{
            comentario:{
                required: "El mensaje no debe estar vacío."
            }
        }
    });
});