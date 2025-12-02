package br.com.cagepa.painel.subsistemas.processamento_imagem;

import br.com.cagepa.painel.infra.Logger;
import java.io.File;
import java.util.Random;

//Adapter para o Tesseract OCR
public class TesseractAdapter implements OCRAdapter {

    @Override
    public String extrairTexto(File imagem) {
        Logger.getInstance().log("ADAPTER: Solicitando leitura de OCR para o arquivo " + imagem.getName());

        //SIMULAÇÃO: Retorna um número aleatório como se tivesse lido a imagem
        double leituraSimulada = 100 + new Random().nextDouble() * 50;

        return String.format("%.2f", leituraSimulada).replace(",", ".");
    }
}
