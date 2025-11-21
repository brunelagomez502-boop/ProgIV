function validar() {
    // limpiar errores previos
    document.querySelectorAll("span.text-danger").forEach(s => s.textContent = "");

    // obtener campos (usar id con naming container de JSF: formulario:campo)
    const nombreEl = document.getElementById("formulario:nombre");
    const apellidoEl = document.getElementById("formulario:apellido");
    const telefonoOBJ = document.getElementById("formulario:telefono");
    const dniEl = document.getElementById("formulario:dni");
    const fechaEl = document.getElementById("formulario:fecNac");
    const fechaInMut = document.getElementById("formulario:fecIngMut");
    const sexoEl = document.querySelector('input[name$="sexo"]:checked');
    const domicilioEl = document.getElementById("formulario:domicilio");
    const emailOBJ = document.getElementById("formulario:mail");

    // valores (proteger acceso si algún elemento no existe)
    const nombre = nombreEl ? nombreEl.value.trim() : "";
    const apellido = apellidoEl ? apellidoEl.value.trim() : "";
    const telef = telefonoOBJ ? telefonoOBJ.value.trim() : "";
    const dni = dniEl ? dniEl.value.trim() : "";
    const fecha = fechaEl ? fechaEl.value.trim() : "";
    const fecInM = fechaInMut ? fechaInMut.value.trim() : "";
    const sexo = sexoEl ? sexoEl.value : "";
    const domicilio = domicilioEl ? domicilioEl.value.trim() : "";
    const email = emailOBJ ? emailOBJ.value.trim() : "";

    const regexTelefono = /^\+54(?:9\d{10}|\d{10})$/;
    // reglas
    const regexDni = /^\d{7,8}$/;
    // acepta dd/mm/yyyy o yyyy-mm-dd
    const regexFecha = /^(\d{2}\/\d{2}\/\d{4}|\d{4}-\d{2}-\d{2})$/;

    // formato email simple algo@algo.algo
    const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    let valido = true;
    let primerErrorEl = null;

    // Auxiliar para marcar error y fijar foco la primera vez
    function marcarError(spanId, msg, elemento) {
        const span = document.getElementById(spanId);
        if (span)
            span.textContent = msg;
        if (!primerErrorEl && elemento)
            primerErrorEl = elemento;
    }

    // Nombre
    if (nombre === "") {
        marcarError("errorNombre", "Campo obligatorio", nombreEl);
        valido = false;
    } else if (nombre.length < 2) {
        marcarError("errorNombre", "Debe tener al menos 2 caracteres", nombreEl);
        valido = false;
    }

    // Apellido
    if (apellido === "") {
        marcarError("errorApellido", "Campo obligatorio", apellidoEl);
        valido = false;
    } else if (apellido.length < 2) {
        marcarError("errorApellido", "Debe tener al menos 2 caracteres", apellidoEl);
        valido = false;
    }

    if (telef === "") {
        marcarError("errorTelefono", "Campo obligatorio", telefonoOBJ);
        valido = false;
    } else if (!regexTelefono.test(telef)) {
        marcarError("errorTelefono", "Formato invalido", telefonoOBJ);
        valido = false;
    }


    // DNI
    if (dni === "") {
        marcarError("errorDni", "Campo obligatorio", dniEl);
        valido = false;
    } else if (!regexDni.test(dni)) {
        marcarError("errorDni", "DNI inválido (7 u 8 números)", dniEl);
        valido = false;
    }

    // Fecha Nacimiento
    if (fecha === "") {
        marcarError("errorFechaNac", "Campo obligatorio", fechaEl);
        valido = false;
    } else if (!regexFecha.test(fecha)) {
        marcarError("errorFechaNac", "Formato inválido (dd/mm/aaaa o aaaa-mm-dd)", fechaEl);
        valido = false;
    }

    // Fecha Ingreso
    if (fecInM === "") {
    marcarError("errorFecIngMut", "Campo obligatorio", fechaInMut);
    valido = false;
} else if (!regexFecha.test(fecInM)) {
    marcarError("errorFecIngMut", "Formato inválido (dd/mm/aaaa o aaaa-mm-dd)", fechaInMut);
    valido = false;
} else {
    // Validar que no sea mayor a hoy
    let partes = fecInM.includes("/") ? fecInM.split("/") : fecInM.split("-"); // soporta dd/mm/aaaa o aaaa-mm-dd
    let fechaObj;

    if (partes.length === 3) {
        if (fecInM.includes("/")) {
            // dd/mm/aaaa
            fechaObj = new Date(partes[2], partes[1]-1, partes[0]);
        } else {
            // aaaa-mm-dd
            fechaObj = new Date(partes[0], partes[1]-1, partes[2]);
        }
    }

    const hoy = new Date();
    hoy.setHours(0,0,0,0); // ignorar horas
    if (fechaObj > hoy) {
        marcarError("errorFecIngMut", "La fecha no puede ser mayor a hoy", fechaInMut);
        valido = false;
    }
}


    // Sexo
    if (sexoEl === null) {
        marcarError("errorSexo", "Seleccione un sexo", sexoEl);
        valido = false;
    }

    // Domicilio
    if (domicilio === "") {
        marcarError("errorDomicilio", "Campo obligatorio", domicilioEl);
        valido = false;
    } else if (domicilio.length < 5) {
        marcarError("errorDomicilio", "Domicilio muy corto", domicilioEl);
        valido = false;
    }

    if (email === "") {
        marcarError("errorMail", "Campo obligatorio", emailOBJ);
    } else if (!regexEmail.test(email)) {
        marcarError("errorMail", "Formato invalido", emailOBJ);
        valido = false;
    }


    // si hay error, enfocar el primer campo inválido y prevenir submit
    if (!valido) {
        if (primerErrorEl) {
            try {
                primerErrorEl.focus();
            } catch (e) {
            }
        }
        return false;
    }

    return true;
}

function validarComercio() {
    
    document.querySelectorAll('.text-danger.small').forEach(el => el.textContent = '');
    
    const nombreObj = document.getElementById("formulario:nombre");
    const direccionObj = document.getElementById("formulario:direccion");
    const responsableObj = document.getElementById("formulario:responsable");
    const telefonoObj = document.getElementById("formulario:telefono");
    const mailObj = document.getElementById("formulario:email");
    const descuentoObj = document.getElementById("formulario:descuento");
    const fechaObj = document.getElementById("formulario:fecIniAct");


    const nombre = nombreObj.value.trim();
    const direccion = direccionObj.value.trim();
    const responsable = responsableObj.value.trim();
    const telefono = telefonoObj.value.trim();
    const mail = mailObj.value.trim();
    const descuento = descuentoObj.value.trim();
    const fecha = fechaObj.value.trim();

    const errorNom = document.getElementById("errorNombre");
    const errorDir = document.getElementById("errorDireccion");
    const errorResp = document.getElementById("errorResponsable");
    const errorTel = document.getElementById("errorTelefono");
    const errorEmail = document.getElementById("errorMail");
    const errorDesc = document.getElementById("errorDescuento");
    const errorFec = document.getElementById("errorFechaInicio");


    // formato email simple algo@algo.algo
    const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // teléfono: solo dígitos, máximo entero con signo (32 bits)
    const regexTelefono = /^\d{7,10}$/;

    let isValid = true;

    if (nombre === "") {
        errorNom.textContent = "Campo Obligatorio";
        isValid = false;
    }

    if (direccion === "") {
        errorDir.textContent = "Campo Obligatorio";
        isValid = false;
    }

    if (responsable === "") {
        errorResp.textContent = "Campo Obligatorio";
        isValid = false;
    }

    if (telefono === "") {
        errorTel.textContent = "Campo Obligatorio";
        isValid = false;
    } else if(!regexTelefono.test(telefono)) {
        errorTel.textContent = "Debe contener solo números (7 a 10 dígitos)";
        isValid = false;
    }
    
    if (mail === "") {
        errorEmail.textContent = "Campo Obligatorio";
        isValid = false;
    } else if(!regexEmail.test(mail)) {
        errorEmail.textContent = "Formato Invalido";
        isValid = false;
    }
    
    if (descuento === "") {
        errorDesc.textContent = "Campo Obligatorio";
        isValid = false;
    }else if (descuento <=0){
        errorDesc.textContent = "Valor invalido: debe ser mayor a 0";
        isValid = false;
    }
    
   if (fecha === "") {
    errorFec.textContent = "Campo Obligatorio";
    isValid = false;
} else {
    // Convertir fecha a objeto Date
    let partes = fecha.includes("/") ? fecha.split("/") : fecha.split("-");
    let fechaObj;

    if (partes.length === 3) {
        if (fecha.includes("/")) {
            // dd/mm/yyyy
            fechaObj = new Date(partes[2], partes[1]-1, partes[0]);
        } else {
            // yyyy-mm-dd
            fechaObj = new Date(partes[0], partes[1]-1, partes[2]);
        }
    }

    const hoy = new Date();
    hoy.setHours(0,0,0,0); // ignorar horas
    if (fechaObj > hoy) {
        errorFec.textContent = "La fecha no puede ser mayor a hoy";
        isValid = false;
    }
}

    
    return isValid;

}
