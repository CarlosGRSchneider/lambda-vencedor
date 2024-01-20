# Lambda vencedor

Este repositório faz parte de um projeto maior de uma arquitetura distribuida para um sistema de menor lance único na AWS. Esta parte é responsável por consultar o banco de dados e selecionar um vencedor, e deve ser rodada em uma função Lambda da AWS. O passo a passo para montar o sistema completo pode ser lido aqui:

https://medium.com/@carlosguilherme.schneider/aws-menor-lance-%C3%BAnico-em-uma-arquitetura-distribu%C3%ADda-f36e9c2af5e2

O pacote lambda contém uma classe chamada `Main`, que deve ser invocada na função Lambda. É necessário enviar qual premio está sendo leiloado e um valor de deslocamento de dias. O período do leilão é do número de dias deslocados, até o dia de ontem. Por exemplo, em um deslocamento de 7 dias, significa que o sorteio será semanal.
Este valor é utilizado na classe `Query`, que faz a seguinte consulta na base de dados(assumindo um deslocamento de 7 dias)

SELECT * FROM lance WHERE hora_do_lance BETWEEN '2024-01-11 00:00:00' AND '2024-01-18 23:59:59'
    AND valor NOT IN
        (SELECT valor FROM lance WHERE hora_do_lance between '2024-01-11 00:00:00' AND '2024-01-18 23:59:59'
        GROUP BY valor HAVING COUNT(valor) > 1)
   ORDER BY valor
   LIMIT 1;

Caso não haja vencedor nesta consulta, é enviado um e-mail para o administrador do sorteio, informando que não houve vencedor. Se um vencedor é encontrado, é enviado um e-mail para o vencedor informando sua vitória, e seu prêmio, bem como um e-mail para o administrador informando o sucesso do sorteio.
Os e-mails são enviados utilizando o AWS SES(Simple Email Service)

Para que o código funcione, é preciso informar 5 variáveis de ambiente na função Lambda:
Na classe `Main`
  * `EMAIL_SES`
  * `EMAIL_ADM`

Na classe `Query`
  * `DATABASE_URL`
  * `DATABASE_USER`
  * `DATABASE_PASSWORD`

--------------------------------------------------------------------------------------------------

# Winning Lambda

This repository is part of a larger project involving a distributed architecture for a lowest unique bid system on AWS. This component is responsible for querying the database and selecting a winner, and it should be executed as an AWS Lambda function. The step-by-step guide to assembling the complete system can be found here:

https://medium.com/@carlosguilherme.schneider/aws-menor-lance-%C3%BAnico-em-uma-arquitetura-distribu%C3%ADda-f36e9c2af5e2

The Lambda package contains a class named `Main`, which should be invoked within the Lambda function. It is necessary to provide information about the auctioned prize and a days-offset value. The auction period spans from the number of offset days to yesterday. For instance, with a 7-day offset, it means the draw will occur weekly. This value is utilized in the `Query` class, which performs the following database query (assuming a 7-day offset):

SELECT * FROM lance WHERE hora_do_lance BETWEEN '2024-01-11 00:00:00' AND '2024-01-18 23:59:59'
    AND valor NOT IN
        (SELECT valor FROM lance WHERE hora_do_lance between '2024-01-11 00:00:00' AND '2024-01-18 23:59:59'
        GROUP BY valor HAVING COUNT(valor) > 1)
   ORDER BY valor
   LIMIT 1;

If there is no winner in this query, an email is sent to the draw administrator, informing them that no winner was determined. If a winner is found, an email is sent to the winner, notifying them of their victory and prize. Additionally, an email is sent to the administrator, confirming the success of the draw. 
Emails are sent using AWS SES (Simple Email Service).

To ensure the code functions correctly, it is necessary to provide 5 environment variables in the Lambda function:
In the `Main` class:
  * `EMAIL_SES`
  * `EMAIL_ADM`

In the Query class:
  * `DATABASE_URL`
  * `DATABASE_USER`
  * `DATABASE_PASSWORD`
