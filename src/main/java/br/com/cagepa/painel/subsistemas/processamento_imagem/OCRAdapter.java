package br.com.cagepa.painel.subsistemas.processamento_imagem;

import java.io.File;

public interface OCRAdapter {
    String extrairTexto(File imagem);
}
