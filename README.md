# RaceRank - Interface (JavaFX)

Esta é a interface gráfica do sistema RaceRank, desenvolvida em JavaFX. Ela permite aos usuários interagir com o sistema de tempos de voltas de forma intuitiva.

## Principais Telas
- **Registro de Voltas:** Formulário para cadastrar novos tempos de voltas de pilotos em pistas específicas.
- **Ranking:** Visualização detalhada de todas as voltas, com filtro por pista e ordenação automática do menor para o maior tempo.

## Pré-requisitos
- JDK 17 ou superior.
- Maven ou Gradle para gerenciamento de dependências.
- Certifique-se de que a API (Back-end) esteja em execução na porta 8080 antes de iniciar a interface.

## Como Executar
1. Importe o projeto como um projeto Maven/Gradle na sua IDE.
2. Configure as variáveis de ambiente, se necessário.
3. Clone o motor Back-End e siga as instruções de inicialização
4. Lembre-se de estar com o motor e o banco de dados rodando quando for rodar a interface. 
5. Execute a classe principal `Launcher.java`.

Este projeto consome a API desenvolvida neste repositório: `https://github.com/irislobato/racerankAPI`