# E-commerce Backend

Este repositório contém o código-fonte do **backend** da aplicação de e-commerce. Desenvolvido com **Spring Boot**, o projeto foca em fornecer APIs eficientes e seguras para integração com o frontend, utilizando boas práticas de desenvolvimento.

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem principal para desenvolvimento.
- **Spring Boot**: Framework para criação de aplicações Java robustas.
- **MySQL**: Banco de dados utilizado para persistência.
- **Spring Security**: Gerenciamento de autenticação e autorização.
- **JWT**: Implementação de autenticação baseada em tokens.
- **Hibernate**: Framework de ORM para integração com o banco de dados.

## ⚙️ Funcionalidades

- **CRUD de Produtos**: Cadastro, leitura, atualização e remoção de produtos.
- **Autenticação e Autorização**: Usuários e administradores com diferentes permissões.
- **Gestão de Pedidos**: Criação e rastreamento de pedidos realizados.
- **Integração com Gateways de Pagamento**: (Planejado para futuras versões).
- **APIs Documentadas**: Documentação via Swagger para facilitar o consumo.

## 📂 Estrutura do Projeto

O backend segue uma estrutura modularizada para organização do código:
```
e-commerce-backend/ 
├── src/ 
│ ├── main/ 
│ │ ├── java/com/projeto/ecommerce/ 
│ │ │ ├── config/ Configurações do projeto 
│ │ │ ├── controller/ Controladores das rotas 
│ │ │ ├── dto/ Objetos de transferência de dados 
│ │ │ ├── model/ Entidades do banco de dados 
│ │ │ ├── repository/ Interfaces de acesso ao banco 
│ │ │ ├── security/ Configurações de segurança 
│ │ │ ├── service/ Lógica de negócios 
│ │ │ └── utils/ Funções auxiliares 
│ ├── resources/ Configurações e scripts SQL 
│ └── test/ Testes unitários e de integração 
└── pom.xml Configurações do Maven
```

## 👥 Colaboradores

Este projeto foi desenvolvido com a colaboração de:

- 👨‍💻 [Danilo Paravani](https://github.com/DaniloParavani) 
- 👨‍💻 [Enzo Janssen](https://github.com/enzojanssen)
- 👨‍💻 [Erick Ramos](https://github.com/erickramosxp)  
- 👩‍💻 [Francieli](https://github.com/fran-lucini0908) 
- 👨‍💻 [Gabriel Willian](https://github.com/gabrielwillianfb)  
- 👨‍💻 [Gabriel Bertollo](https://github.com/GabrielPortalBertollo)
- 👨‍💻 [Henrique Junqueira](https://github.com/henriquejunqueira)  
- 👨‍💻 [José Carlos](https://github.com/JoseCarlosVSJ)  
- 👨‍💻 [Luiz Nonato](https://github.com/luiznslobato)  
- 👨‍💻 [Marlon Muller](https://github.com/MarlonMuller) 

## 🖥️ Como Executar

1. **Clone o repositório**:  
   <code>git clone https://github.com/Projeto-Final-MaisPraTi/e-commerce-backend.git  
   cd e-commerce-backend</code>

2. **Configue o banco de dados**:  
   Atualize o arquivo application.properties com as credencias do seu banco MySQL.

3. **Execute o projeto**:  
   <code>mvn spring-boot:run</code>

4. **Acesse a API**:  
   Abra [http://localhost:8080](http://localhost:8080) no navegador.

## 📜 Rotas

### Público
- **`POST /api/auth/login`**: Login de usuários.
- **`POST /api/auth/register`**: Registro de novos usuários.
- **`GET /api/products`**: Listagem de produtos.

### Privado (autenticado)
- **`POST /api/orders`**: Criar um pedido.
- **`GET /api/orders/{id}`**: Detalhes de um pedido.

### Administrativo
- **`POST /api/products`**: Criar um produto.
- **`PUT /api/products/{id}`**: Atualizar um produto.
- **`DELETE /api/products/{id}`**: Remover um produto.

## 🚀 Roadmap

Funcionalidades futuras planejadas:
- Relatórios de vendas.
- Integração com serviços de terceiros (ex: PayPal, Stripe).
- Monitoramento de erros com ferramentas externas.

## 🤝 Contribuições

Contribuições são bem-vindas! Para contribuir:
1. Faça um fork do repositório.
2. Crie uma branch para a sua funcionalidade: <code>git checkout -b minha-feature</code>.
3. Commit suas mudanças: <code>git commit -m 'Adicionei minha funcionalidade'</code>.
4. Envie para o repositório remoto: <code>git push origin minha-feature</code>.
5. Abra um Pull Request.

## 📜 Licença

Este projeto está sob a licença MIT. Consulte o arquivo <code>[LICENSE](LICENSE)</code> para mais detalhes.
