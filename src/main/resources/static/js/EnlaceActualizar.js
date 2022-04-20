const SUPABASE_URL = 'https://uhasyyvztklrrtsxmnyn.supabase.co'
const SUPABASE_ANON_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVoYXN5eXZ6dGtscnJ0c3htbnluIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY1MDMwMTg1MywiZXhwIjoxOTY1ODc3ODUzfQ.W721O1fnskovEvSNHriydGteKI_gn-2lAdYq83AqpwU'
const _supabase = supabase.createClient(SUPABASE_URL, SUPABASE_ANON_KEY);
$(document).ready(function () {

    function uuidv4() {
        return ([1e9] + -43 + -8e3 + -11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    $("#botonActualizar").click(async () => {
        var updatefiles = document.getElementById("imagenUploadactualizar");
        var nombre = $("#uncompleto");
        var telefono = $("#utelefono");
        var email = $("#uemail");
        if (
            nombre != "" &&
            telefono != "" &&
            email != ""
        ) {

            if (updatefiles.files[0] != null) {
                const { data, error } = await _supabase.storage
                    .from("covec")
                    .upload(
                        `public/${uuidv4()}`, updatefiles.files[0]
                    );

                if (data != null) {
                    $("#linkUpdate").val(data.Key);
                } else if (error != null) {
                    new Notify({
                        status: "error",
                        title: "Atención",
                        text: "Ocurrio un error al subir la imagen al servidor",
                        effect: "fade",
                        speed: 300,
                        customClass: "",
                        customIcon: "",
                        showIcon: true,
                        showCloseButton: true,
                        autoclose: false,
                        autotimeout: 3000,
                        gap: 20,
                        distance: 20,
                        type: 1,
                        position: "bottom x-center",
                    });
                }
            }
            $("#actualizar").click();
        } else {
            new Notify({
                status: "warning",
                title: "Atención",
                text: "Es necesario llenar todos los campos",
                effect: "fade",
                speed: 300,
                customClass: "",
                customIcon: "",
                showIcon: true,
                showCloseButton: true,
                autoclose: false,
                autotimeout: 3000,
                gap: 20,
                distance: 20,
                type: 1,
                position: "right top",
            });
        }

    });
});