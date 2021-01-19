**Necessidades:**

Criar um endpoint para possibilitar a relacionar um item à um evento onde:
- deve ser fornecido o id do item;

E criar pelo ao menos testes de contrato para validar o comportamento. Mais detalhes aqui -> [Quarkus - Creating your first application : Testing](https://quarkus.io/guides/getting-started#testing)

**Restrições:**
- id do item é requerido e deve ser de um item já registrado;
- id do item deve ser único nos relacionamento para um evento. (1 Evento pode se relacionar com N itens, porém não pode se relacionar mais de uma vez com o mesmo item)

 **Resultado esperado**
- retornar 200 com o item do evento registrado;
- Em caso de falha, retorne 400

**Dicas**:  
*"***Disclamer:*** essas dicas são para estudar e conhecer um pouco mais sobre o Quarkus e sobre os frameworks envolvidos, então não encarem como uma regra, não existe "bala de prata", mas uma ou outra "ferramenta" a mais em nosso cinto de utilidades não faz mal algum, não é ?"* 
- O endpoint carrega o id do evento alvo, utilize essa informação para carregá-lo; 
- Para as validações, que tal utilizar a especificacao Bean Validation para validar o input da sua requisição? Tente criar validadores para as restrições que a especificação exige. Lembre-se que é possível utilizar o padrão Active Record fornecido pela extensão Panache para ter acesso ao contexto de persistência na criação de qualquer validador customizado.
- Menos é mais: não tente recriar ou adicionar alguma solução pensando em casos futuros que até então não existem. Essa 1o. versão do MVP vai evoluir e a naturalmente sua complexidade também. 
- Entre em contato com o grupo do projeto em caso de dúvida. Que tal fazer pair programming? Ou quem sabe sessões de coding dojo? (Gostei da idéia)

Segue a especificação OpenAPI v3 para esse endpoint: [github.com/dearrudam/rdb-backend-quarkus/blob/feature/criando-especificacao/src/main/resources/openapi-criar-item-evento.yaml](https://github.com/dearrudam/rdb-backend-quarkus/blob/feature/criando-especificacao/src/main/resources/openapi-criar-item-evento.yaml]

Uma versão reduzida:
```yaml
paths:
  /eventos/{eventoId}/itens:
    post:
      tags:
      - eventos
      description: Registrar um item ao evento para o id informado
      parameters:
      - name: eventoId
        in: path
        description: Id do evento
        required: true
        schema:
          type: number
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/RegistrarItemEventoRequest'
        required: true
      responses:
        200:
          description: Lista de todos os itens relacionados para o evento informado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/RegistrarItemEventoResponse'
        400:
          description: Requisição inválida
          content: {}
        404:
          description: Evento não encontrado
          content: {}
               
components:
  schemas:
        
    RegistrarItemEventoRequest:
      type: object
      properties:
        itemId:
          type: number
      required:
      - itemId
      
    RegistrarItemEventoResponse:
      type: object
      properties:
        itemId:
          type: number
        itemDescricao:
          type: string
          
```


