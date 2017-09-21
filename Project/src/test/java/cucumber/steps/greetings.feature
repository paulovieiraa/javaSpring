#language: pt

Funcionalidade: Realizar o serviço REST utilizando Spring Boot

  Cenario: Buscar todos os greetings salvos no banco de dados.
    Quando chamarmos a BASE_URL concatenado com a url "/api/greetings"
    Entao o serviço devera retonar HTTP-STATUS 200, listando os greetings

  Cenario: Buscar um greeting por id
    Quando chamarmos a BASE_URL concatenado com a url "/api/greetings/" concatenando com o id "5" do greeting selecionado
    Entao o serviço devera retonar HTTP-STATUS 200, listando o greeting

  Cenario: Inserir um greeting
    Quando chamarmos a BASE_URL concatenado com a url "/api/greetings", inserindo o texto "Inserindo um texto!"
    Entao o serviço devera retonar HTTP-STATUS 201, listando o greeting inserido

#  Cenario: Atualizar um greeting
#    Quando chamarmos a BASE_URL concatenado com a url "/api/greetings/" com o id "4", inserindo o "id" e alterando o  "texto" do greeting
#    Entao o serviço devera retonar HTTP-STATUS 200, listando o greeting atualizado

#  Cenario: Deleta um greeting
#    Quando chamarmos a BASE_URL concatenado com a url "/api/greetings/" concatenando com o id "6" do greeting selecionado
#    Entao o serviço devera retonar HTTP-STATUS 204