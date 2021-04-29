Para excutar o projeto no Docker verifique se o arquivo jar do projeto está na pasta target.

Vá ate a raiz do e rode o seguinte comando: "docker build -t nome-da-imagem .", com isso será
gerado a imagem do projeto.

Agora com a imagem criada para rodar o container use o seguinte comando: "docker run -p 8080:8080 nome-da-imagem"

Com isso o projeto estara execudando e disponivel na porta 8080.
