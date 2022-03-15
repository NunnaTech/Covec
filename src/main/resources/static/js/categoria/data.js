$(function () {
    var arreglo = [
        {Nombre: "Alumbrado publico"},
        {Nombre: "Delincuencia"},
        {Nombre: "Drenaje"},
    ];

    $(document).ready(function () {
        $.each(arreglo, function( index, value ) {
            var trAppend = '<tr><td>'+ (index+1) +'</td>' +
                '<td>' +value.Nombre+'</td>' +
                '<td><div class="btn-group">\n' +
                '<button style="background-color: #F7BD00" class="btn btn-sm"><span class="material-icons">edit</span></button>\n' +
                '<button style="background-color: #605AD4" class="btn btn-sm"><span class="material-icons">delete</span></button>\n' +
                '</div></td>' +
                '</tr>';
            $('tbody').append(trAppend);
        })
    })
})