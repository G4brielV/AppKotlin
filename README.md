# AppKotlin - Estudo de Kotlin e Arquitetura Android
Este projeto foi desenvolvido com o objetivo estritamente acadêmico de aplicar **boas práticas de programação e padrões de arquitetura consolidados no ecossistema Android**.

  **Nota sobre Design:** O foco deste projeto não é a estética visual ou UI design complexo. 
  A prioridade total foi dada à organização do código, ao entendimento do fluxo de dados e à implementação de regras de negócio e padrões bem estabelecidos. 

## 🛠 **Tecnologias e Ferramentas**
- **Interface:** Jetpack Compose
- **Gerenciamento de Estado:** StateFlow e SharedFlow
- **Assincronismo:** Kotlin Coroutines
- **Rede:** Retrofit 
- **Navegação:** Compose Navigation 

## 🏗️ Estrutura e Arquitetura (MVVM)
O projeto segue o padrão **MVVM (Model-View-ViewModel)**, garantindo a separação de responsabilidades:
1. **Model:** Camada de dados que gerencia a lógica de negócios e persistência.
   - **Data:** Implementação de repositórios, serviços de rede e local (Token).
   - **Domain:** Modelos puros (User) e contratos de repositórios.
2. **ViewModel:** Atua como ponte, expondo estados (StateFlow) que a UI observa e processando ações do usuário.
3. **View (UI):** Telas em Compose que reagem às mudanças de estado e enviam eventos para o ViewModel.

A injeção de dependência é feita manualmente através da ViewModelFactory, que centraliza a criação das instâncias necessárias para os ViewModels.

## 📱 **Funcionalidades do app**
O sistema utiliza a API pública **[DummyJSON](https://dummyjson.com/)** para simular um ambiente real de backend com autenticação e listagem de dados.

### Endpoints e Fluxos:
1. **Tela de Login (```LoginScreen```)**
   - Endpoint: ```POST /auth/login```
   -  Envio de credenciais e armazenamento seguro do token via **TokenManager**.
2. **Tela de Listagem (UserListScreen)**
   - **Endpoint:** ```GET /users```
   - Busca de usuários com **paginação**, mapeando **(UserDto)** para **(User)** (domain)
3. **Tela de Detalhes (UserDetailScreen)**
   - **Endpoint:** ```GET /users/{id}```
   - Recuperação de informações específicas de um perfil selecionado.
  
## 📁 **Estrutura de pastas**
```
app/src/main/java/com/example/appkotlin/
├── data/                   # Implementação de dados 
│   ├── local/              # Persistência de Tokens (TokenManager)
│   ├── remote/             # Chamadas API (Retrofit, DTOs, ApiService)
│   └── repository/         # Implementação das interfaces de Repositório
├── di/                     # Injeção de Dependência (ViewModelFactory)
├── domain/                 # Regras de Negócio Puras
│   ├── model/              # Modelos de domínio (Entities)
│   └── repository/         # Interfaces de contrato dos Repositórios
├── navigation/             # Gerenciamento de rotas (NavGraph)
└── ui/                     # Camada de Apresentação (MVVM)
    ├── login/              # Tela e ViewModel de Autenticação
    ├── userlist/           # Tela e ViewModel de Listagem
    └── detail/             # Tela e ViewModel de Detalhes
```
