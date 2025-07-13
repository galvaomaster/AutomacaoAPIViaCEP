# Desafio Técnico para Analista de Qualidade de Software - Granto Seguros
## Entrega: Anderson Galvão


## Análise Funcional da API

### 1. Pontos de Melhorias
#### a. Tratamento de erros e mensagens genéricas na validação de CEP

- Para o retorno do GET de CEP inválido, exibir: `"CEP com formato inválido"`, e não apenas retorno genérico `400`.
- CEP válido, porém inexistente na base, não deveria retornar erro. Poderia retornar algo como vazio ou `"0"`.

#### b. Pesquisa de CEP

- Para os três caracteres mínimos, não informa o tratamento caso envie campos vazios ou em branco.
- Falta clareza nos tipos de caracteres permitidos (ex: permite numéricos?).
- Falta tratamento de erros.

---

## 2. Cenários de Teste

### Cenário 1: Consulta de CEP válido com registro na base de dados

**Objetivo:** Validar o retorno de dados corretos do endereço para CEP válido e existente.
**Resultado esperado:** 
``` 
Status code 200 (OK).
Resposta JSON com dados válidos.
```

```
Dado que eu faça um GET para a https://viacep.com.br/
Quando o CEP é válido e existe na base de dados
E o formato é JSON
Então o response contém campos do endereço com dados válidos
```

---

### Cenário 2: Consulta de CEP válido **SEM** registro na base de dados

**Objetivo:** Validar o retorno para CEP válido, mas não cadastrado.
**Resultado esperado:** 
``` 
Status Code: 404 (Not Found).
Resposta com "erro": true.
```

```
Dado que eu faça um GET para a https://viacep.com.br/
Quando o CEP é válido mas NÃO existe na base de dados
E o formato é JSON
Então o response contém o campo "erro": true
```

---

### Cenário 3: Consulta de CEP com formato inválido

**Objetivo:** Consultar CEP com dígitos menores que 8.
**Resultado esperado:** 
``` 
Status Code: 400 (Bad request).
Resposta com mensagem de erro com formato inválido.
```

```
Dado que eu faça um GET para a https://viacep.com.br/
Quando o CEP contém menos de 8 dígitos
E o formato é JSON
Então o response retorna status code 400 (Bad Request)
```

---

### Cenário 4: Pesquisa por logradouro com UF e cidade válidos

**Objetivo:** Consultar CEP com dígitos menores que 8.
**Resultado esperado:** 
``` 
Status Code: 200 (OK).
Resposta JSON com "logradouro": "Recife Antigo", "uf": "PE".
```

```
Dado que eu faça um GET para a https://viacep.com.br/
Quando os parâmetros (UF, cidade e logradouro) são válidos  
Entao a API retorna status code 200  
E resposta é uma lista JSON com até 50 CEPs
```

---

### Cenário 5: Busca com parâmetros não informados

**Objetivo:** Validar mensagem de erro para parâmetros não informados.
**Resultado esperado:** 
``` 
Status Code: 400 (Bad Request).
Resposta JSON com "logradouro": "Recife Antigo", "uf": "PE".
```

```
Dado que eu faça um GET para a https://viacep.com.br/
Quando o <parâmetro> não é informado
E o formato é JSON
Então <erro_message>
```

**Exemplos:**

| parâmetro   | erro_message                                                           |
|-------------|------------------------------------------------------------------------|
| UF          | "Parâmetros obrigatórios faltantes: cidade e logradouro"              |
| cidade      | "Parâmetros obrigatórios faltantes: cidade e logradouro"              |
| logradouro  | "Parâmetros obrigatórios faltantes: cidade e logradouro"              |