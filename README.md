# Cafe Tech — Refatorado (Etapa 6)

Versão refatorada do sistema desktop Cafe Tech, com separação de
responsabilidades entre regra de negócio, persistência e interface (Swing),
seguindo os princípios SOLID (com ênfase no Princípio da Responsabilidade
Única e no Princípio da Inversão de Dependência).

## Estrutura de pacotes

```
cafe_tech
├── Main.java                          # testes manuais (main)
├── model/
│   ├── Produto.java                   # entidade (antiga ProdutosDTO)
│   ├── StatusProduto.java             # enum (evita magic strings)
│   ├── Usuario.java
│   └── Pedido.java
├── repository/
│   ├── ProdutoRepository.java         # interface (abstração / DIP)
│   ├── ProdutoRepositoryJDBC.java     # implementação real (MySQL)
│   └── ProdutoRepositoryEmMemoria.java# implementação para testes
├── service/
│   ├── ProdutoService.java            # regra de negócio / validação
│   ├── UsuarioService.java
│   ├── PedidoService.java
│   └── exception/
│       ├── ProdutoInvalidoException.java
│       └── RepositorioException.java
├── util/
│   └── ConexaoFactory.java            # abre conexão JDBC (antiga ConectaDAO)
└── view/
    └── ListarProdutosView.java        # tela Swing (só chama o service)
```

## Como importar no NetBeans

1. Abra o NetBeans → **File > New Project > Java with Ant > Java Application**
   (ou **Java Application** simples), nomeie como `CafeTechRefatorado`.
2. Apague a classe `Main` gerada automaticamente pelo NetBeans e copie todo o
   conteúdo da pasta `src/` deste pacote para dentro de `src/` do projeto
   criado (mantendo a estrutura de pastas/pacotes).
3. Se for usar a conexão real com o banco, adicione o driver
   **MySQL Connector/J** às bibliotecas do projeto
   (clique direito em *Libraries* → *Add JAR/Folder*).
4. Rode `cafe_tech.Main` para os testes de regra de negócio (não precisa de
   banco) ou `cafe_tech.view.ListarProdutosView` para a tela (precisa de
   banco MySQL configurado, ver `ConexaoFactory`).

## Como rodar os testes (`Main.main`)

`Main` executa três blocos de teste, todos imprimindo `[OK]` ou `[FALHA]`
no console:

1. **Regras de negócio sem banco** — usa `ProdutoRepositoryEmMemoria` para
   testar cadastro válido, nome vazio, valor negativo, listagem,
   atualização e exclusão, sem precisar de MySQL.
2. **Conexão com banco real** — tenta usar `ProdutoRepositoryJDBC`; se o
   MySQL local não estiver rodando, mostra um aviso (não quebra o teste).
3. **Usuario e Pedido** — testa `UsuarioService` e `PedidoService`.
