package com.example.agroecologico;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailsHandler extends AsyncTask{

    private static final String DE = "asoproorganicosappcali@gmail.com";
    private static final String PASSWORD = "univalle1234";

    private String para;
    private String asunto;
    private String mensaje;
    private Context context;
    private Session session;

    private ProgressDialog progressDialog;

    public emailsHandler(Context context, String para, String asunto, String mensaje){
        this.context = context;
        this.para = para;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Enviando mensaje","por favor espere...",false,false);
    }

    @Override
    protected void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        progressDialog.dismiss();
        Toast.makeText(context,"Correo enviado!!",Toast.LENGTH_LONG).show();
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(DE, PASSWORD);
            }
        });
        try {
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(DE));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            mm.setSubject(asunto);
            mm.setText(mensaje);
            Transport.send(mm);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
