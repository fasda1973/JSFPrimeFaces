package br.com.fasda.id;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/fotos/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pega o nome do arquivo da URL (ex: /fotos/123_foto.jpg)
        String filename = request.getPathInfo().substring(1);
        File file = new File("C:/Dev/Java/fasda_erp/uploads/fotos", filename);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Define o tipo do arquivo (jpg, png, etc)
        String contentType = getServletContext().getMimeType(file.getName());
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));

        // Copia o arquivo para o corpo da resposta
        Files.copy(file.toPath(), response.getOutputStream());
    }
}