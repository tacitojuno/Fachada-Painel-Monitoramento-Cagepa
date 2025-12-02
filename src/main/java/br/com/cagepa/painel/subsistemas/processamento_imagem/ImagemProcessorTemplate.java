package br.com.cagepa.painel.subsistemas.processamento_imagem;

import br.com.cagepa.painel.core.entidades.Leitura;
import br.com.cagepa.painel.infra.Logger;

import java.io.File;

//Template Method
public abstract class ImagemProcessorTemplate {

    public final Leitura processar(String caminhoImagem, String matriculaSHA) {
        Logger.getInstance().log("TEMPLATE: Iniciando processamento da imagem...");

        File imagem = lerImagem(caminhoImagem);
        if(!validarImagem(imagem)) {
            throw new RuntimeException("Imagem inválida ou não encontrada");
        }

        String textoExtraido = aplicarOCR(imagem);
        double valorLimpo = tratarDados(textoExtraido);

        return criarLeitura(matriculaSHA, valorLimpo);
    }

    protected abstract String aplicarOCR(File imagem);

    protected File lerImagem(String caminho) {
        return new File(caminho);
    }

    protected boolean validarImagem(File imagem) {
        return imagem.exists() || imagem.getName().endsWith(".jpg") || imagem.getName().endsWith(".png");
    }

    protected double tratarDados(String texto) {
        Logger.getInstance().log("TEMPLATE: Convertendo texto '" + texto + "' para número...");
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    protected Leitura criarLeitura(String matricula, double valor) {
        Logger.getInstance().log("TEMPLATE: Leitura criada com sucesso: " + valor + "m3");
        return new Leitura(matricula, valor);
    }
}
