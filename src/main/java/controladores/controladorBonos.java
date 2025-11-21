/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.Afiliado;
import entidades.Bonosemitidos;
import entidades.Comercio;

import jakarta.enterprise.context.SessionScoped;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import repositorios.repositorioBonosEmitidos;
import repositorios.repositorioComercio;

import jakarta.inject.Named;

import jakarta.inject.Inject;

import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.application.FacesMessage;

import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.io.Serializable;


import java.awt.Color;

// --------------- PDF (iText old / lowagie) ---------------

import com.lowagie.text.Phrase;

//import jakarta.activation.ByteArrayDataSource;

import java.util.Properties;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.mail.util.ByteArrayDataSource;



import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.InternetAddress;

import jakarta.mail.Multipart;
import jakarta.mail.Message;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 *
 * @author bgsof
 */
@Named(value = "controladorBonos")
@SessionScoped
public class controladorBonos implements Serializable {

    @Inject
    private repositorioBonosEmitidos repoBonoEmitido;

    @Inject
    private repositorioComercio repoComercio;

    private Afiliado afiliado;                   // Afiliado seleccionado
    private Comercio comercioSeleccionado;       // Comercio elegido
    private Bonosemitidos bono;                  // Bono en creación

//public void cargarAfiliado() {
//    if (idAfiliado != null) {
//        // cargar afiliado
//        afiliado = repoAfi.PorId(idAfiliado).orElse(null);
//    }
//    // cargar lista de comercios
//    comercios = repoComercio.Listar();
//}
    public String iniciar(Afiliado afiliado) {
        this.afiliado = afiliado;
        this.bono = new Bonosemitidos();
        this.comercioSeleccionado = null;

        // Redirigir a la página de selección de comercio
        return "/bonos/emitirBono.xhtml?faces-redirect=true";
    }

    public List<Comercio> listarComercios() {
        return repoComercio.Listar();
    }

    public String emitirBono() {
        if (comercioSeleccionado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Debe seleccionar un comercio", null));
            return null;
        }

        // Validar que el comercio esté ACTIVO
        if (!comercioSeleccionado.getEstadoCom()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "El comercio seleccionado se encuentra inactivo. Elija otro comercio.", null));
            return null;
        }

        // Validar si ya tiene bono este mes para el comercio
        boolean existe = repoBonoEmitido.existeBonoDelMes(afiliado.getNroTarjeta(), comercioSeleccionado.getIdComercios());
        if (existe) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Ya reclamaste un bono este mes en este comercio", null));
            return null;
        }

        // Crear bono
        bono.setNroTarjeta(afiliado);
        bono.setIdComercios(comercioSeleccionado);
        bono.setFecEmision(new Date());
        bono.setFecVencimiento(finDelMes());

        repoBonoEmitido.Guardar(bono);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Bono emitido correctamente", null));

        // Redirigir a página de confirmación
        return "/bonos/confirmacionBono.xhtml?faces-redirect=true";
    }

    private Date finDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 30); // suma 30 días
        return cal.getTime();
    }

    public controladorBonos() {
    }

//    public Bonosemitidos bonoemitido() {
//        System.out.println("id" + id);
//        if (id != null && id > 0) {
//            repoBonoEmitido.PorId(id).ifPresent(b -> {
//                bonoemitido = b;
//            });
//        } else {
//            bonoemitido = new Bonosemitidos();
//        }
//        return bonoemitido;
//    }
    public repositorioBonosEmitidos getRepoBonoEmitido() {
        return repoBonoEmitido;
    }

    public void setRepoBonoEmitido(repositorioBonosEmitidos repoBonoEmitido) {
        this.repoBonoEmitido = repoBonoEmitido;
    }

//    public Bonosemitidos getBonoemitido() {
//        if (bonoemitido == null) {
//            bonoemitido();
//        }
//        return bonoemitido;
//    }
//
//    public void setBonoemitido(Bonosemitidos bonoemitido) {
//        this.bonoemitido = bonoemitido;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//    
//    public String guardar(){
//        repoBonoEmitido.Guardar(bonoemitido);
//        return "/afiliados/index.xhtml?faces-redirect=true";
//    }
    public String eliminar(Integer id) {
        repoBonoEmitido.Eliminar(id);
        return "/afiliados/index.xhtml?faces-redirect=true";
    }

    public List<Bonosemitidos> listar() {
        return repoBonoEmitido.Listar();
    }

    public Afiliado getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliado afiliado) {
        this.afiliado = afiliado;
    }
public List<Bonosemitidos> getListar() {
    return repoBonoEmitido.Listar(); // Devuelve siempre los bonos actualizados
}

    public Comercio getComercioSeleccionado() {
        return comercioSeleccionado;
    }

    public void setComercioSeleccionado(Comercio comercioSeleccionado) {
        this.comercioSeleccionado = comercioSeleccionado;
    }

    public Bonosemitidos getBono() {
        return bono;
    }

    public void setBono(Bonosemitidos bono) {
        this.bono = bono;
    }
    
    public void generarPdf(Integer idBono) {
    try {
        Bonosemitidos bono = repoBonoEmitido.PorId(idBono).orElse(null);

        if (bono == null) {
            System.out.println("Bono no encontrado");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEmision = bono.getFecEmision() != null ? sdf.format(bono.getFecEmision()) : "—";
        String fechaVenc = bono.getFecVencimiento() != null ? sdf.format(bono.getFecVencimiento()) : "—";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 40, 40, 40, 40);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        // COLORES
        Color colorPrimario = new Color(41, 128, 185); // azul
        Color colorFondo = new Color(236, 240, 241);   // gris claro

        // TITULO
        Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD, colorPrimario);
        Paragraph titulo = new Paragraph("COMPROBANTE DE BONO", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        doc.add(titulo);

        doc.add(new Paragraph("Mutual Manager", 
                new Font(Font.HELVETICA, 24, Font.ITALIC)));

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));

        // ==============================
        //  CARD DE AFILIADO
        // ==============================
        PdfPTable cardAfi = new PdfPTable(1);
        cardAfi.setWidthPercentage(100);

        PdfPCell headerAfi = new PdfPCell(new Phrase("Datos del Afiliado"));
        headerAfi.setBackgroundColor(colorPrimario);
        headerAfi.setPadding(8);
        headerAfi.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerAfi.setPhrase(new Phrase("Datos del Afiliado", 
                new Font(Font.HELVETICA, 13, Font.BOLD, Color.WHITE)));
        cardAfi.addCell(headerAfi);

        PdfPCell bodyAfi = new PdfPCell();
        bodyAfi.setBackgroundColor(colorFondo);
        bodyAfi.setPadding(10);
        bodyAfi.addElement(new Paragraph("Nombre: " 
                + bono.getNroTarjeta().getNombre() + " " + bono.getNroTarjeta().getApellido()));
        bodyAfi.addElement(new Paragraph("DNI: " + bono.getNroTarjeta().getDni()));
        cardAfi.addCell(bodyAfi);

        doc.add(cardAfi);
        doc.add(new Paragraph(" "));

        // ==============================
        //  CARD DE COMERCIO
        // ==============================
        PdfPTable cardCom = new PdfPTable(1);
        cardCom.setWidthPercentage(100);

        PdfPCell headerCom = new PdfPCell();
        headerCom.setBackgroundColor(colorPrimario);
        headerCom.setPadding(8);
        headerCom.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCom.setPhrase(new Phrase("Datos del Comercio",
                new Font(Font.HELVETICA, 13, Font.BOLD, Color.WHITE)));
        cardCom.addCell(headerCom);

        PdfPCell bodyCom = new PdfPCell();
        bodyCom.setBackgroundColor(colorFondo);
        bodyCom.setPadding(10);
        bodyCom.addElement(new Paragraph("Nombre: " + bono.getIdComercios().getNombre()));
        bodyCom.addElement(new Paragraph("Dirección: " + bono.getIdComercios().getDireccion()));
        cardCom.addCell(bodyCom);

        doc.add(cardCom);
        doc.add(new Paragraph(" "));

        // ==============================
        //  TABLA DETALLE BONO
        // ==============================
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10);

        PdfPCell cellHeader = new PdfPCell(new Phrase("Detalles del Bono"));
        cellHeader.setColspan(2);
        cellHeader.setBackgroundColor(colorPrimario);
        cellHeader.setPadding(8);
        cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeader.setPhrase(new Phrase("Detalles del Bono",
                new Font(Font.HELVETICA, 13, Font.BOLD, Color.WHITE)));
        tabla.addCell(cellHeader);

        tabla.addCell("Descuento:");
        tabla.addCell(bono.getIdComercios().getDescuento() + "%");

        tabla.addCell("Fecha de Emisión:");
        tabla.addCell(fechaEmision);

        tabla.addCell("Vencimiento:");
        tabla.addCell(fechaVenc);

        doc.add(tabla);

        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Presentar este comprobante en el comercio adherido."));
        doc.add(new Paragraph(" "));

        doc.add(new Paragraph("Firma del Afiliado: ______________________________"));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Firma del Comercio: ______________________________"));

        doc.close();

        // ====================================
        // DESCARGA EN EL NAVEGADOR
        // ====================================
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.setResponseContentType("application/pdf");
        ec.setResponseHeader("Content-Disposition",
                "attachment; filename=bono_" + bono.getIdBonos() + ".pdf");

        ec.getResponseOutputStream().write(baos.toByteArray());
        fc.responseComplete();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public byte[] generarPdfBono(Bonosemitidos bono) throws Exception {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document doc = new Document(PageSize.A4, 40, 40, 40, 40);
    PdfWriter.getInstance(doc, baos);

    doc.open();

    // ===============================
    // ESTILOS
    // ===============================
    Color colorPrimario = new Color(41, 128, 185); // azul
    Color colorSecundario = new Color(52, 73, 94); // gris oscuro
    Color colorFondoCard = new Color(236, 240, 241); // gris claro

    Font tituloPrincipal = new Font(Font.HELVETICA, 22, Font.BOLD, colorSecundario);
    Font subtitulo = new Font(Font.HELVETICA, 14, Font.BOLD, Color.WHITE);
    Font textoBold = new Font(Font.HELVETICA, 12, Font.BOLD);
    Font textoNormal = new Font(Font.HELVETICA, 12, Font.NORMAL);

    // ===============================
    // BANDA SUPERIOR
    // ===============================
    PdfPTable header = new PdfPTable(1);
    header.setWidthPercentage(100);

    PdfPCell headerCell = new PdfPCell(new Phrase("Mutual Manager", 
            new Font(Font.HELVETICA, 18, Font.BOLD, Color.WHITE)));

    headerCell.setBackgroundColor(colorPrimario);
    headerCell.setPadding(15);
    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    headerCell.setBorder(PdfPCell.NO_BORDER);
    header.addCell(headerCell);

    doc.add(header);

    doc.add(new Paragraph(" "));
    doc.add(new Paragraph("COMPROBANTE DE BONO", tituloPrincipal));
    doc.add(new Paragraph(" "));

    // Formato fecha
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // ===========================================================
    // TARJETA DATOS DEL AFILIADO — ESTILO CARD
    // ===========================================================
    PdfPTable cardAfi = new PdfPTable(1);
    cardAfi.setWidthPercentage(100);

    // Título de la tarjeta
    PdfPCell tituloAfi = new PdfPCell(new Phrase("Datos del Afiliado", subtitulo));
    tituloAfi.setBackgroundColor(colorSecundario);
    tituloAfi.setPadding(10);
    tituloAfi.setHorizontalAlignment(Element.ALIGN_CENTER);
    tituloAfi.setBorder(PdfPCell.NO_BORDER);
    cardAfi.addCell(tituloAfi);

    // Cuerpo de la tarjeta
    PdfPCell bodyAfi = new PdfPCell();
    bodyAfi.setBackgroundColor(colorFondoCard);
    bodyAfi.setPadding(12);

    bodyAfi.addElement(new Paragraph("Nombre: " +
            bono.getNroTarjeta().getNombre() + " " + bono.getNroTarjeta().getApellido(), textoNormal));

    bodyAfi.addElement(new Paragraph("DNI: " +
            bono.getNroTarjeta().getDni(), textoNormal));

    bodyAfi.setBorderColor(colorSecundario);
    bodyAfi.setBorderWidth(1.2f);
    cardAfi.addCell(bodyAfi);

    doc.add(cardAfi);
    doc.add(new Paragraph(" "));

    // ===========================================================
    // TARJETA DATOS DEL COMERCIO
    // ===========================================================
    PdfPTable cardCom = new PdfPTable(1);
    cardCom.setWidthPercentage(100);

    PdfPCell tituloCom = new PdfPCell(new Phrase("Datos del Comercio", subtitulo));
    tituloCom.setBackgroundColor(colorSecundario);
    tituloCom.setPadding(10);
    tituloCom.setHorizontalAlignment(Element.ALIGN_CENTER);
    tituloCom.setBorder(PdfPCell.NO_BORDER);
    cardCom.addCell(tituloCom);

    PdfPCell bodyCom = new PdfPCell();
    bodyCom.setBackgroundColor(colorFondoCard);
    bodyCom.setPadding(12);

    bodyCom.addElement(new Paragraph("Nombre: " +
            bono.getIdComercios().getNombre(), textoNormal));

    bodyCom.addElement(new Paragraph("Dirección: " +
            bono.getIdComercios().getDireccion(), textoNormal));

    bodyCom.setBorderColor(colorSecundario);
    bodyCom.setBorderWidth(1.2f);
    cardCom.addCell(bodyCom);

    doc.add(cardCom);
    doc.add(new Paragraph(" "));

    // ===========================================================
    // TABLA DETALLES DEL BONO — ESTILO ELEGANTE
    // ===========================================================
    PdfPTable tabla = new PdfPTable(2);
    tabla.setWidthPercentage(100);
    tabla.setSpacingBefore(10);

    PdfPCell headerTabla = new PdfPCell(new Phrase("Detalles del Bono", subtitulo));
    headerTabla.setBackgroundColor(colorPrimario);
    headerTabla.setPadding(10);
    headerTabla.setHorizontalAlignment(Element.ALIGN_CENTER);
    headerTabla.setColspan(2);
    headerTabla.setBorder(PdfPCell.NO_BORDER);
    tabla.addCell(headerTabla);

    // Celdas
    tabla.addCell(new PdfPCell(new Phrase("Descuento:", textoBold)));
    tabla.addCell(new PdfPCell(new Phrase(bono.getIdComercios().getDescuento() + "%", textoNormal)));

    tabla.addCell(new PdfPCell(new Phrase("Fecha de Emisión:", textoBold)));
    tabla.addCell(new PdfPCell(new Phrase(sdf.format(bono.getFecEmision()), textoNormal)));

    tabla.addCell(new PdfPCell(new Phrase("Vencimiento:", textoBold)));
    tabla.addCell(new PdfPCell(new Phrase(sdf.format(bono.getFecVencimiento()), textoNormal)));

    doc.add(tabla);

    doc.add(new Paragraph(" "));
    doc.add(new Paragraph("Presentar este comprobante en el comercio adherido.", textoNormal));
    doc.add(new Paragraph(" "));

    doc.add(new Paragraph("Firma Afiliado: ______________________________"));
    doc.add(new Paragraph(" "));
    doc.add(new Paragraph("Firma Comercio: ______________________________"));

    doc.close();

    return baos.toByteArray();
}
// -----------------------------------
    // 2) DESCARGA DEL PDF — BOTÓN DESCARGAR
    // -----------------------------------
    public void descargarPDF(Integer idBono) throws Exception {

        Bonosemitidos bono = repoBonoEmitido.PorId(idBono)
                .orElseThrow(() -> new Exception("No existe el bono"));

        byte[] pdfBytes = generarPdfBono(bono);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=bono_" + bono.getIdBonos() + ".pdf");

        OutputStream out = response.getOutputStream();
        out.write(pdfBytes);
        out.flush();
        fc.responseComplete();
    }

    // -----------------------------------------------------
    // 3) ENVIAR PDF POR CORREO — BOTÓN ENVIAR POR EMAIL
    // -----------------------------------------------------
  public void enviarCorreo(Integer idBono) throws Exception {

   Bonosemitidos bono = repoBonoEmitido.PorId(idBono)
           .orElseThrow(() -> new Exception("No existe el bono"));
    enviarPdfPorCorreo(bono);
     FacesContext.getCurrentInstance().addMessage(null,
         new FacesMessage("Correo enviado al afiliado."));
  }

    // ------------------------------------------
    // 4) LÓGICA COMPLETA PARA ENVIAR EL CORREO
    // ------------------------------------------
    public void enviarPdfPorCorreo(Bonosemitidos bono) throws Exception {

        // 📩 correo del afiliado
        String destinatario = bono.getNroTarjeta().getMail();

        // Generar PDF
        byte[] pdfBytes = generarPdfBono(bono);

        // Datos del correo emisor
        final String USER = "mutual.manager10@gmail.com"; 
        final String PASS = "Unse1234_";  // NO la contraseña normal

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER, PASS);
                    }
                });

        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(USER));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject("Comprobante de Bono");
        
        MimeBodyPart texto = new MimeBodyPart();
        texto.setText("Estimado afiliado, se adjunta el bono emitido.");

        
       DataSource ds = new ByteArrayDataSource(pdfBytes, "application/pdf");
    MimeBodyPart adj = new MimeBodyPart();
    adj.setDataHandler(new DataHandler(ds));
    adj.setFileName("bono_" + bono.getIdBonos() + ".pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(texto);
        multipart.addBodyPart(adj);

        mensaje.setContent(multipart);

        Transport.send(mensaje);
    }

public String prepararEnvioCorreo(Bonosemitidos bono) {

    if (bono.getNroTarjeta() == null || bono.getNroTarjeta().getMail() == null) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN,
            "Atención", "El afiliado no tiene correo registrado."));
        return null;
    }

    String destinatario = bono.getNroTarjeta().getMail();
    String asunto = "Comprobante de Bono (Nro " + bono.getIdBonos() + ")";
    String cuerpo = "Estimado/a " + bono.getNroTarjeta().getNombre() + ",%0D%0A%0D%0A"
            + "Adjunte el comprobante PDF descargado previamente.%0D%0A"
            + "Comercio: " + bono.getIdComercios().getNombre() + "%0D%0A"
            + "Vencimiento: " + bono.getFecVencimiento() + "%0D%0A%0D%0A"
            + "Saludos cordiales.";

    try {
        String urlAsunto = URLEncoder.encode(asunto, "UTF-8");
        String urlCuerpo = URLEncoder.encode(cuerpo, "UTF-8");

        return "mailto:" + destinatario + "?subject=" + urlAsunto + "&body=" + urlCuerpo;

    } catch (UnsupportedEncodingException e) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Error", "No se pudo preparar el correo."));
        return null;
    }
}

    public repositorioComercio getRepoComercio() {
        return repoComercio;
    }

    public void setRepoComercio(repositorioComercio repoComercio) {
        this.repoComercio = repoComercio;
    }



}
