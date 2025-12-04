package br.com.cagepa.painel.subsistemas.processamento_imagem;

import br.com.cagepa.painel.infra.Logger;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

//Adapter para o Tesseract OCR
public class TesseractAdapter implements OCRAdapter {

    private final ITesseract tesseract;

    public TesseractAdapter() {
        this.tesseract = new Tesseract();
        //Aponta para a pasta que criada na raiz do projeto
        this.tesseract.setDatapath("./tessdata");

        //Configurações para otimizar leitura de números (Opcional, mas ajuda na precisão)
        this.tesseract.setTessVariable("tessedit_char_whitelist", "0123456789.,m");
    }

    @Override
    public String extrairTexto(File imagem) {
        Logger.getInstance().log("OCR REAL: Lendo arquivo " + imagem.getName() + "...");

        try {
            String resultado = tesseract.doOCR(imagem);

            //Limpeza básica: remove quebras de linha e espaços extras
            String limpo = resultado.replaceAll("\\s+", "").trim();
            Logger.getInstance().log("OCR REAL: Texto bruto detectado -> [" + limpo + "]");

            return limpo;

        } catch (TesseractException e) {
            Logger.getInstance().logErro("Falha no Tesseract: " + e.getMessage());
            return "0"; //Retorno de segurança
        }
    }
}
