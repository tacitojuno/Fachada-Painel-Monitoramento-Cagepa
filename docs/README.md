# 🚰 Sistema de Gerenciamento/Monitoramento de Clientes/Hidrômetros - CAGEPA (IFPB -  Padrões de Projeto)

> Sistema de gerenciamento e monitoramento de clientes e SHA's utilizando padrões de projeto aprendidos na disciplina Padrões de Projeto - IFPB.

### Progresso Geral - Changelog
Template para CTRL C + V: █

```
████████████====     (Funcionalidades)
```
```
████████████====     (Testes)
```
```
██████████████====== (Geral)
```

#### [04/12/2025]
- Finalização dos Requisitos de Negócio (Produto Mínimo Viável).
- Melhoria do CRUD: Adição da funcionalidade de remoção de clientes (Delete) no Gestor e na Fachada.
- Implementação de Monitoramento Temporal:
  - Refatoração da entidade Hidrometro para armazenar histórico (Lista de Leituras).
  - Criação de fluxo para persistir leituras durante a varredura do monitoramento.
  - Criação de funcionalidade de consulta de histórico por matrícula.
- Refatoração: Remoção de classes órfãs e arquivos de infraestrutura não utilizados.
- Atualização da Interface (CLI): Adição das opções "7. Consultar Histórico" e "8. Remover Cliente".
- Testes:
  - Validação do ciclo completo de CRUD.
  - Validação da persistência de histórico e consulta de consumo temporal.

#### [03/12/2025]
- Subsistema 3: Alertas e Notificação.
- Implementação do Chain of Responsability ("pipeline" de verificação).
- Utilizando novamente Template Method (Padronizar envio de notificações).
- Testes:
  - Sistema de Alertas (Chain of Resp. + Template Method).

#### [02/12/2025]
- Subsistema 4: Processamento de Imagens.
- Implementação do Template Method (Ler -> OCR -> Validar -> Salvar).
- Implementação do Adapter (Simular Tesseract OCR).
- Subsistema 2: Monitoramento de Consumo.
- Implementação de Strategy (Cálculo de Consumo) e Observer (Notificações de Leitura).
- Testes:
  - Processamento de Imagens (Template Method e Adapter funcionando).
  - Monitoramento de Consumo (Strategy e Observer funcionando).

#### [01/12/2025]
- Implementação do Subsistema 5 (Auth).
- Início da Implementação do Subsistema 1 (Gestão de Clientes).
- Criação de insfraestrutura básica (br.com.cagepa.painel.infra/Logger.java)
- Implementação de Padrões de Projeto Singleton, Proxy, Repository e Factory.
- Testes:
  - Bloqueio de Acesso sem Token (Proxy funcionando)
  - Login (AuthManager funcionando como Singleton)
  - Cadastro de Clientes (Factory e Repository funcionando)
  - Listagem de Dados

#### [30/11/2025]
- Estruturação de arquitetura do projeto (Organização).
- Preenchimento de Camada Base (Entidades e DTO's), sem lógica complexa.

## Padrões de Projeto Implementados

### 1. **Singleton**

**Localização no Código:**
```
br.com.cagepa.painel.infra;
└── Logger.java   ← Implementação do Singleton
```
```
br.com.cagepa.painel.subsistemas.auth;
└── AuthManager.java   ← Implementação do Singleton
```

### 2. **Proxy**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.auth;
└── FachadaProxy.java   ← Implementação do Proxy
```

### 3. **Repository**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.clientes;
└── ClienteRepository.java   ← Implementação do Repository (Interface)
```

### 4. **Factory**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.clientes;
└── ClienteFactory.java   ← Implementação do Factory
```

### 5. **Template Method**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.processamento_imagem;
└── ImagemProcessorTemplate.java   ← Implementação do Template Method
```
```
br.com.cagepa.painel.subsistemas.notificacao;
└── NotificadorTemplate.java   ← Implementação do Template Method
└── AlertaService.java  ← Utilização do Template Method
```

### 6. **Adapter**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.processamento_imagem;
├── OCRAdapter.java   ← Interface do Adapter
└── TesseractAdapter.java  ← Implementação do Adapter
```

### 7. **Strategy**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.monitoramento;
├── ICalculoConsumoStrategy.java   ← Interface do Strategy
└── AgregadorConsumo.java  ← Utilização do Strategy
```

### 8. **Observer**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.monitoramento;
├── IMonitoramentoObserver.java   ← Interface do Observer
└── AgregadorConsumo.java  ← Utilização do Observer
```

### 9. **Chain of Responsability**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.notificacao;
├── RegraAlertaHandler.java   ← Implementação do Chain of Responsability
└── AlertaService.java  ← Utilização da Chain of Responsability
```

### 10. **Facade**

**Localização no Código:**
```
br.com.cagepa.painel.core.fachada;
├── IFachada.java   ← Interface da Fachada
└── FachadaPainel.java  ← Implementação da Fachada
```

## Testes Individuais de Funcionalidades Realizados (Só para visualizar os padrões inicialmente)

```
//Instancia o Proxy (Ele que controla o acesso à Fachada real)
IFachada sistema = new FachadaProxy();
```
```
JWTToken tokenAdmin = null;
```
- Teste 01: Tentar acessar sem login (Deve Falhar)
```
System.out.println("\n--- Teste 01: Acesso sem Token ---");
try {
    sistema.cadastrarCliente("Indivíduo", "00000000000", "fake_token");
} catch (AutenticacaoException e) {
    System.out.println("SUCESSO: O sistema bloqueou o acesso não autorizado.");
    System.out.println("Mensagem: " + e.getMessage());
}
```
- Teste 02: Realizar Login
```
System.out.println("\n--- Teste 02: Realizando Login ---");
try {
    tokenAdmin = sistema.login("admin@cagepa.com.br", "admin123");
    System.out.println("Login realizado! Token recebido: " + tokenAdmin.getTokenString().substring(0, 10) + "...");
} catch (Exception e) {
    System.out.println("Erro fatal no login: " + e.getMessage());
    return;
}
```
- Teste 03: Cadastrar Clientes (Deve Funcionar)
```
System.out.println("\n--- Teste 03: Cadastrando Clientes ---");
try {
    String tokenStr = tokenAdmin.getTokenString();

    sistema.cadastrarCliente("Indivíduo Um", "12345678900", tokenStr);
    sistema.cadastrarCliente("Empresa XYZ", "12345678000199", tokenStr);

    try {
        sistema.cadastrarCliente("Indivíduo Um", "12345678900", tokenStr);
    } catch (IllegalArgumentException e) {
        System.out.println("Validação funcionando: " + e.getMessage());
    }
} catch (Exception e) {
    System.out.println("Erro ao cadastrar: " + e.getMessage());
}
```
- Teste 04: Listar Dados
```
System.out.println("\n--- Teste 04: Listagem Geral ---");
List<ClienteDTO> clientes = sistema.listarClientes(tokenAdmin.getTokenString());

System.out.println("Total de Clientes: " + clientes.size());
for(ClienteDTO c : clientes) {
    System.out.println("- Cliente: " + c.nome() + " | Doc: " + c.cpfCnpj());
}
```
- Teste 05: Processamento de Imagem (Template Method + Adapter)
```
System.out.println("\n--- Teste 5: Processamento de Imagem ---");

br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl processador = new br.com.cagepa.painel.subsistemas.processamento_imagem.ProcessadorImagemImpl();

try {
    var leitura = processador.processar("C:/imagens/hidrometro_01.jpg", "SHA-123");
    System.out.println("RESULTADO FINAL: Leitura gerada -> " + leitura.getValorM3() + "m3");
} catch (Exception e) {
    System.out.println("Erro no processamento: " + e.getMessage());
}
```
- Teste 06: Monitoramento (Strategy + Observer)
```
System.out.println("\n--- Teste 6: Monitoramento de Consumo ---");

br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo agregador = new br.com.cagepa.painel.subsistemas.monitoramento.AgregadorConsumo();

//Configurando Observer
agregador.getSubject().adicionarObservador(l -> {
    System.out.println(">> ALERTA (Observer): Nova leitura detectada no SHA " + l.getMatriculaSHA() + " Valor: " + l.getValorM3());
});

//Simulando a chegada de uma leitura
var leituraRecente = new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 150.0);
agregador.novaLeituraRecebida(leituraRecente);

//Testando o Strategy (Cálculo)
List<br.com.cagepa.painel.core.entidades.Leitura> historico = List.of(
        new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 100.0),
        new br.com.cagepa.painel.core.entidades.Leitura("SHA-123", 200.0)
);

double media = agregador.processarHistorico(historico);
System.out.println("Cálculo via Strategy (Média): " + media);
```
- Teste 07: Sistema de Alertas (Chain of Responsibility + Template Method)
```
System.out.println("\n--- Teste 7: Sistema de Alertas ---");

br.com.cagepa.painel.subsistemas.notificacao.AlertaService alertaService =
        new br.com.cagepa.painel.subsistemas.notificacao.AlertaService();

//Cenário 1: Consumo normal (sem alertas)
System.out.println(">> Processando leitura normal (50m3)...");
alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-001", 50.0));

//Cenário 2: Consumo elevado (Chain preventiva)
System.out.println("\n>> Processando leitura alta (85m3)...");
alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-002", 85.0));

//Cenário 3: Consumo crítico (Chain crítica + Notificação)
System.out.println("\n>> Processando leitura crítica (110m3)...");
alertaService.processarLeitura(new br.com.cagepa.painel.core.entidades.Leitura("SHA-003", 110.0));
```
