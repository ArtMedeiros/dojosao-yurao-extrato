# Extrato

O serviço da **Extrato** tem basicamente a responsabilidade de guardar as informações das transações que ocorreram. Basicamente precisamos armazenar nele:
- Qual operação ocorreu (Pagamento de boleto, Depósito, etc)
- Qual o valor dessa operação
- Qual a data da transação

Sabendo disso, vamos as tarefas:

1. Como saber quais transações ocorreram
2. Consultando o extrato

---

## Consultando o extrato

### Necessidades

As pessoas devem conseguir visualizar no aplicativo as operações que aconteceram na sua conta. Portanto, precisamos listar sempre as 20 últimas transações que ocorreram para determinada pessoa. 

### Restrições

- O número de transações por página não pode ser negativo
- Uma página não pode ter mais que **50** transações
   
### Resultado Esperado

- Um endpoint que liste, por padrão, 20 transações ordenado pela transação mais recente.

---

## Registrando uma transação

### Necessidades

Toda transação deve ser registrada para fins de auditoria. Essas informações chegam na nossa API através do Kafka em um tópico chamado `transacoes`. Basicamente, é enviado um JSON com as seguintes informações:

- Operação
- Valor dessa operação
- Data da transação
- Cliente que efetuou a transação
- Conta participante
   
### Resultado Esperado

- Todos os dados devem estar disponíveis no banco de dados para serem consultados em um futuro.

