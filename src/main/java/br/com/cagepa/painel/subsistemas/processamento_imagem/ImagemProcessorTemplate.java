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
        Logger.getInstance().log("TEMPLATE: Texto bruto recebido: '" + texto + "'");

        try {
            //Substitui vírgulas por pontos (caso o OCR leia 10,5 em vez de 10.5)
            String textoNormalizado = texto.replace(",", ".");

            //REGEX: Substitui tudo que NÃO for número ou ponto por ESPAÇO
            String apenasNumeros = textoNormalizado.replaceAll("[^0-9.]", " ");

            //Quebra em partes baseadas nos espaços e pega a primeira parte não vazia
            //Geralmente o contador principal é o primeiro número grande que aparece
            String[] partes = apenasNumeros.trim().split("\\s+");

            if (partes.length > 0 && !partes[0].isEmpty()) {
                String numeroLimpo = partes[0];
                Logger.getInstance().log("TEMPLATE: Número extraído após limpeza: " + numeroLimpo);
                return Double.parseDouble(numeroLimpo);
            }

        } catch (NumberFormatException e) {
            Logger.getInstance().logErro("Falha ao converter número: " + texto);
        }

        return 0.0;
    }

    protected Leitura criarLeitura(String matricula, double valor) {
        Logger.getInstance().log("TEMPLATE: Leitura criada com sucesso: " + valor + "m3");
        return new Leitura(matricula, valor);
    }
}
