# 🚰 Sistema de Gerenciamento/Monitoramento de Clientes/Hidrômetros - CAGEPA (IFPB -  Padrões de Projeto)

> Sistema de gerenciamento e monitoramento de clientes e SHA's utilizando padrões de projeto aprendidos na disciplina Padrões de Projeto - IFPB.

### Progresso Geral - Changelog
Template para CTRL C + V: █

```
████████============ (Funcionalidades)
```
```
████████============ (Testes)
```
```
██████============== (Geral)
```

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

### 6. **Adapter**

**Localização no Código:**
```
br.com.cagepa.painel.subsistemas.processamento_imagem;
├── OCRAdapter.java   ← Interface do Adapter
└── TesseractAdapter.java  ← Implementação do Adapter (Simulação do Tesseract OCR)
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