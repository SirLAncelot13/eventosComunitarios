const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const msjConfirmacion = 'Le solicitamos esperar un momento a que la solicitud termine.';
const titleConfirmacion = '¿Está seguro de realizar la acción solicitada?';
const msjExito = 'La actividad solicitada, se ha realizado de manera exitosa.';
const titleExito = 'Acción realizada exitosamente';
const msjError = 'No se ha logrado realizar la actividad solicitada, por lo cual le solicitamos contactar a soporte técnico para corregir el problema.';
const titleError = 'Error al realizar la acción';


function validarForm() {

  var expRegNombre = /^[a-zA-ZÑñÁáÉéÍíÓóÚúÜü\s]+$/;
  var expRegApellidos = /^[a-zA-ZÑñÁáÉéÍíÓóÚúÜü\s]+$/;
  var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
  var expTelefono= /^([0-9])*$/;


  var titulo = document.getElementById("titulo");
  var comentarios = document.getElementById("descripcion");
  var nombre = document.getElementById("nombre");
  var apellidos = document.getElementById("apellidos");
  var correo = document.getElementById("email");
  var fechaI = document.getElementById("fechaInicio").value;
  var fechaF = document.getElementById("fechaFin").value;
  var telefono = document.getElementById("telefono");
  var telefono2 = document.getElementById("telefono").value;

  //Campo nombre
  if (!nombre.value) {
    //alert("El campo nombre es requerido");
    console.log("El campo nombre admite letras y espacios.");
    nombre.focus();
    return false;
  }
  if (!expRegNombre.exec(nombre.value)) {
    //alert("El campo nombre admite letras y espacios.")
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo nombre admite letras y espacios.'
    })
    console.log("El campo nombre admite letras y espacios.");
    nombre.focus();
    return false;
  }
  //Campo apellido
  if (!apellidos.value) {
    //alert("El campo apellidos es requerido");
    apellidos.focus();
    return false;
  }
  if (!expRegApellidos.exec(apellidos.value)) {
    //alert("El campo apellidos admite letras y espacios.")
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo apellidos admite letras y espacios.'
    })
    apellidos.focus();
    return false;
  }


  //campo email
  if (!correo.value) {
    //alert("El campo correo es requerido");
    correo.focus();
    return false;
  }
  if (!emailRegex.exec(correo.value)) {
    //alert("El campo correo no tiene el formato correcto.")
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo correo no tiene el formato correcto.'
    })
    correo.focus();
    return false;
  }
  // campo asunto
  if (!titulo.value) {
    //alert("El campo asunto es requerido");
    titulo.focus();
    return false;
  }
  //campo comentarios
  if (!comentarios.value) {
    //alert("El campo comentarios es requerido");
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo nombre admite letras y espacios.'
    })
    comentarios.focus();
    return false;
  }

  if(new Date(fechaF) < new Date(fechaI)){
   //alert("La fecha final no puede ser menor a la de inicio");
   Swal.fire({
    icon: 'error',
    title: 'Oops...',
    text: 'La fecha final no puede ser menor a la de inicio'
  })
    return false;
  }

  if (!telefono.value) {
    //alert("El campo telefono es requerido");
    correo.focus();
    return false;
  }
  if (!expTelefono.exec(telefono.value)) {
    //alert("El campo telefono no tiene el formato correcto.")
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo telefono no tiene el formato correcto.'
    })
    correo.focus();
    return false;
  }

  if(telefono2.length < 10 || telefono2.length > 15){
    //alert("El campo telefono no tiene el tamaño correcto.")
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'El campo telefono no tiene el tamaño correcto.'
    })
    telefono.focus();
    return false;
  }

  return true;
}
// asociacion del boton enviar con el formulario en html//
/*window.onload = function () {
  var formulario = document.getElementById("evento-frm");
  formulario.onsubmit = validarForm;
}*/


function validFormRegistroPersona(e){
  e.preventDefault();
  let valid_name, valid_primer,valid_segundo,valid_tel,valid_edad,valid_sexo,
      valid_address,valid_email,valid_pass;

  let name = document.getElementById("nombre");
  let primero = document.getElementById("primer");
  let segundo = document.getElementById("segundo");
  let tel = document.getElementById("tel");
  let edad = document.getElementById("edad");
  let sexo = document.querySelectorAll('input[name="sexo"]');
  let address = document.getElementById("address");
  let email = document.getElementById("email");
  let pass = document.getElementById("pass");



  if (!name.value) {
    document.getElementById("sp-name").innerHTML = "Ingresar nombre";
    valid_name = false;
  }else{
    valid_name = true;
    document.getElementById("sp-name").innerHTML = "";
  }

  if (!primero.value) {
    document.getElementById("sp-primer").innerHTML = "Ingresar apellido paterno";
    valid_primer = false;
  }else{
    valid_primer = true;
    document.getElementById("sp-primer").innerHTML = "";
  }

   if (!segundo.value) {
    document.getElementById("sp-segundo").innerHTML = "Ingresar apellido materno";
    valid_segundo = false;
  }else{
    valid_segundo = true;
    document.getElementById("sp-segundo").innerHTML = "";
  }

   if (!tel.value) {
    document.getElementById("sp-tel").innerHTML = "Ingresar telefono";
    valid_tel = false;
  }else{
    if (tel.value.length != 10 ) {
      valid_tel = false;
      document.getElementById("sp-tel").innerHTML = "Ingresar 10 digitos del telefono";
    }else{
      valid_tel = true;
      document.getElementById("sp-tel").innerHTML = "";
    }
  }

  if (!sexo[0].checked && !sexo[1].checked) {
    document.getElementById("sp-sexo").innerHTML = "Seleccionar sexo";
    valid_sexo = false;
  }else{
    document.getElementById("sp-sexo").innerHTML = "";
    valid_sexo = true;
  }

  if (!edad.value) {
    document.getElementById("sp-edad").innerHTML = "Ingresar edad";
    valid_edad = false;
  }else{
    valid_edad = true;
    document.getElementById("sp-edad").innerHTML = "";
  }

  if (!address.value) {
    document.getElementById("sp-address").innerHTML = "Ingresar dirección";
    valid_address = false;
  }else{
    valid_address = true;
    document.getElementById("sp-address").innerHTML = "";
  }

  if (!email.value) {
    document.getElementById("sp-email").innerHTML = "Ingresar correo electrónico" ;
    valid_email = false;
  }else{
    if (re.test(email.value.toLowerCase())) {
      valid_email = true;
      document.getElementById("sp-email").innerHTML = "";
    }else{
      document.getElementById("sp-email").innerHTML = "Ingresar correo electrónico válido" ;
      valid_email = false;
    }
  }

  if (!pass.value) {
    document.getElementById("sp-pass").innerHTML = "Ingresar contraseña";
    valid_pass = false;
  }else{
    if (pass.value.length < 8) {
      document.getElementById("sp-pass").innerHTML = "Ingresar al menos 8 caracteres";
      valid_pass = false;
    }else{
      document.getElementById("sp-pass").innerHTML = "";
      valid_pass = true;
    }
  }

  if (valid_name && valid_primer && valid_segundo && valid_tel && valid_edad && valid_sexo 
      && valid_address && valid_email && valid_pass) {
    Swal.fire({
      title: titleConfirmacion,
      text: msjConfirmacion,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar',
      cancelButtonText:'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        document.getElementById("formRegisterPerson").submit();
      }
    });
    
  }
}

function validFormOferta(event){
  event.preventDefault();
  let validFechaInicio, validFechaFin, validFechaRegistro, validFechaFinRegistro,
      validDetalles, validHoraInicio, validHoraFin, validEvento;

  let fechaInicio = document.getElementById("fecha1");
  let fechaFin = document.getElementById("fecha2");
  let registro1 = document.getElementById("reg1");
  let registro2 = document.getElementById("reg2");
  let detail = document.getElementById("detail");
  let hora1 = document.getElementById("hora1");
  let hora2 = document.getElementById("hora2");
  let evento = document.getElementById("evento");

  if (!fechaInicio.value) {
    document.getElementById("sp-fecha1").innerHTML = "Seleccionar una fecha de inicio";
    validFechaInicio = false;
  }else{
    validFechaInicio = true;
    document.getElementById("sp-fecha1").innerHTML = "";
  }

  if (!fechaFin.value) {
    document.getElementById("sp-fecha2").innerHTML = "Seleccionar una fecha de fin";
    validFechaFin = false;
  }else{
    validFechaFin = true;
    document.getElementById("sp-fecha2").innerHTML = "";
  }

  if (!registro1.value) {
    document.getElementById("sp-reg1").innerHTML = "Seleccionar una fecha de registro inicial";
    validFechaRegistro = false;
  }else{
    validFechaRegistro = true;
    document.getElementById("sp-reg1").innerHTML = "";
  }

  if (!registro2.value) {
    document.getElementById("sp-reg2").innerHTML = "Seleccionar una fecha de registro final";
    validFechaFinRegistro = false;
  }else{
    validFechaFinRegistro = true;
    document.getElementById("sp-reg2").innerHTML = "";
  }

  if (!detail.value) {
    document.getElementById("sp-detail").innerHTML = "Ingresar detalles";
    validDetalles = false;
  }else{
    validDetalles = true;
    document.getElementById("sp-detail").innerHTML = "";
  }

  if (!hora1.value) {
    document.getElementById("sp-hora1").innerHTML = "Ingresar hora inicial";
    validHoraInicio = false;
  }else{
    validHoraInicio = true;
    document.getElementById("sp-horal").innerHTML = "";
  }

  if (!hora2.value) {
    document.getElementById("sp-hora2").innerHTML = "Ingresar hora final";
    validHoraFin = false;
  }else{
    validHoraFin = true;
    document.getElementById("sp-hora2").innerHTML = "";
  }

  if (validFechaInicio && validFechaFin && validFechaRegistro && validFechaFinRegistro && validDetalles 
    && validHoraInicio && validHoraFin && validEvento) {
    Swal.fire({
      title: titleConfirmacion,
      text: msjConfirmacion,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Aceptar',
      cancelButtonText:'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        document.getElementById("formRegistroOferta").submit();
      }
    });
    
  }
}