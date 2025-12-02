package br.com.cagepa.painel.subsistemas.processamento_imagem;

import java.io.File;

public class ProcessadorImagemImpl extends ImagemProcessorTemplate {

    private final OCRAdapter ocrAdapter;

    public ProcessadorImagemImpl() {
        this.ocrAdapter = new TesseractAdapter();
    }

    @Override
    protected String aplicarOCR(File imagem) {
        return ocrAdapter.extrairTexto(imagem);
    }
}
