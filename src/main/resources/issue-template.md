**Necessidades:**

Criar um endpoint para possibilitar o cadastrar de eventos onde:
- cada evento tem que ter nome
- cada evento tem que ter descricao
- cada evento tem uma data para iniciar
- cada evento tem uma data para acabar

E criar pelo ao menos testes de contrato para validar o comportamento. Mais detalhes aqui -> [Quarkus - Creating your first application : Testing](https://quarkus.io/guides/getting-started#testing)

**Restrições:**
- nome é requerido e deve ser único;
- descrição não é requerido, porém deve ter no máximo 400 caracteres caso for fornecido;
- data inicial é requerida. 
- data inicial deve seguir o padrão ISO 8601 ( YYYY-MM-DD )
- data final não é requirida então caso não for informada, assumir a mesa data de início, significando o evento de um dia
- data final deve seguir o padrão ISO 8601 ( YYYY-MM-DD ) caso for fornecida;
- data final deve ser igual ou maior que a data inicial;

 **Resultado esperado**
- novo evento registrado e 200 retornado com o evento criado;
- Em caso de falha, retorne 400

**Dicas**:  
*"***Disclamer:*** essas dicas são para estudar e conhecer um pouco mais sobre o Quarkus e sobre os frameworks envolvidos, então não encarem como uma regra, não existe "bala de prata", mas uma ou outra "ferramenta" a mais em nosso cinto de utilidades não faz mal algum, não é ?"* 
- Para as validações, que tal utilizar o Hibernate Validator para validar o input da sua requisição? Tente criar validadores para as restrições que a especificação exige. Lembre-se que é possível utilizar o padrão Active Record fornecido pela extensão Panache para ter acesso ao contexto de persistência na criação de qualquer validador customizado.
- Menos é mais: não tente recriar ou adicionar alguma solução pensando em casos futuros que até então não existem. Essa 1o. versão do MVP vai evoluir e a naturalmente sua complexidade também. 
- Entre em contato com o grupo do projeto em caso de dúvida. Que tal fazer pair programming? Ou quem sabe sessões de coding dojo? (Gostei da idéia)

Segue a especificação OpenAPI v3 para esse endpoint: [github.com/dearrudam/rdb-backend-quarkus/blob/feature/criando-especificacao/src/main/resources/openapi-criar-evento.yaml](https://github.com/dearrudam/rdb-backend-quarkus/blob/feature/criando-especificacao/src/main/resources/openapi-criar-evento.yaml]

Uma versão reduzida:
```yaml
paths:
  /eventos:
    post:
      tags:
      - eventos
      description: Adiciona um novo evento
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/NovoEventoRequest'
        required: true
      responses:
        200:
          description: novo evento registrado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/NovoEventoResponse'
        400:
          description: Requisição inválida
          content: {}
components:
  schemas:    
    NovoEventoRequest:
      type: object
      properties:
        nome:
          type: string
        descricao:
          type: string
        dataInicio:
          type: string
          format: date
        dataFim:
          type: string
          format: date
      required:
      - nome
      - dataInicio
  
    NovoEventoResponse:
      type: object
      properties:
        id:
          type: number
        nome:
          type: string
        descricao:
          type: string
        dataInicio:
          type: string
          format: date
        dataFim:
          type: string
          format: date
```


