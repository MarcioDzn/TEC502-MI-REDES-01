# TEC502 - Sistema IoT

### Projeto
O projeto é dividido em 3 pastas principais:
- Dispositivo (Server)
    - Contém os arquivos referentes ao dispositivo, o qual simula um aparelho IoT real, desenvolvido em Python.
- Broker (Broker)
    - Contém os arquivos referentes ao serviço de mensageria que possibilita a troca de mensagens entre a interface gráfica e o dispositivo. Foi desenvolvido em Java utilizando o framework SpringBoot.
- Interface (Interface/iot-interface)
    - Contém os arquivos referentes à interface gráfica, a qual permite a manipulação do dispositivo pelo usuário, de maneira remota. Foi desenvolvido em HTML, CSS e JavaScript, a partir do framework ReactJS.

### Dispositivo
Os dispositivos são parte fundamental do projeto, pois geram dados que são enviados ao broker e mais tarde adquiridos pelo cliente final. Cada dispositivo pode ser manipulado por comandos advindos de um meio externo (broker) ou de uma interface local.

### Arquitetura do Dispositivo
Os arquivos referentes ao dispositivo são:
- `AirConditioner`: representa uma especificação do dispositivo.
- `Device`: representa o dispositivo em si.
- `Client`: lida com a comunicação, via protocolos UDP e TCP.
- `main`: arquivo principal de execução.
- `utils`: funções de auxílio.

#### Device e AirConditioner
O dispositivo em si é representado pela classe `Device`, que contém uma classe filha, a qual representa uma especificação do dispositivo, no caso, a `AirConditioner`, que diz respeito a um ar condicionado. Tais classes contam com os métodos:
- `handle_requests`: lida com comandos advindos do broker.
- `get_data`: retorna o dado “medido” pelo dispositivo.
- `get_options`: apresenta uma interface com as ações que o cliente pode realizar localmente no dispositivo, como desligar, ligar e alterar seu valor.


Ademais, essas classes contam com propriedades como:
- `name`: representa o nome do dispositivo.
- `online`: booleano que representa se o dispositivo está ligado ou desligado.
- `default_value`: valor padrão inicial medido pelo dispositivo.

#### Client
A classe `Client`, é responsável por manipular os sockets, a partir de métodos específicos:
- `send_udp_message`: envia mensagens para um destino utilizando o protocolo UDP
- `handle_tcp_connection`: cria um socket servidor para receber conexões TCP e receber dados utilizando o protocolo TCP.

Ademais, essa classe também contém atributos que guardam informações importantes, como:
- `address`: endereço do broker
- `device`: classe referente ao dispositivo em si
- `isConnected`: valor booleano que indica se foi estabelecida uma conexão TCP com o broker
- `deviceIp`: IP da máquina que está executando o dispositivo

### Comunicação
A comunicação entre o dispositivo e o broker é realizada a partir de sockets, via protocolo TCP/IP, para recebimento de informações ou UDP para envio de dados.

#### Comunicação TCP
Ao iniciar o dispositivo, um servidor socket de fluxo (TCP) no IP `0.0.0.0` e na porta `3000` é iniciado, o qual permite que o broker estabeleça uma conexão e envie comandos, via protocolo TCP.  Assim que um comando é recebido e interpretado a conexão é fechada.

O primeiro dado que um dispositivo deve receber é o de primeira conexão, representado pela string `first_conn`, acompanhada do IP da máquina que executa o próprio dispositivo. Após o recebimento desta informação, o dispositivo é liberado para enviar dados ao broker. 

Os demais dados recebidos do broker devem ser comandos, que permitem a realização de uma determinada ação por parte do aparelho. Os comandos que o dispositivo aceita são:
- `“turn_on”`: para ligar o dispositivo.
- `“turn_off”`: para desligar o dispositivo.

#### Comunicação UDP
Para o envio de informações do dispositivo ao broker utilizou-se um socket de datagrama (UDP). Por conta do tipo de protocolo não é necessário estabelecer uma conexão entre o broker e o dispositivo. Dessa forma, os dados são simplesmente enviados ao broker via protocolo UDP, a partir do IP e porta específicos da máquina que está executando-o. 

Antes de serem de fato enviados, os dados são colocados em uma única string, organizados e padronizados em conjuntos chave:valor, representando um “pacote”. Dessa forma, os seguintes campos estão presentes neste pacote:
- `type`: representa o tipo de pacote a ser enviado.
- `ip`: representa o ip da máquina que executa o dispositivo.
- `name`: representa o nome do dispositivo.
- `time`: representa o horário em que o dado foi “medido” e enviado pelo. dispositivo
- `data`: representa o dado.
- `status`: representa o status do dispositivo no momento do envio do dado, pode ser “offline” ou “online”.

A estrutura do pacote enviado é:
`"type::<tipo_de_pacote>, ip::<ip_do_dispositivo>, name::<nome_do_dispositivo>, time::<tempo_de_captura_do_dado>, data::<dado>, status::<status>"`

Existem dois tipos de pacote, um de dados, representado por `“data”` e outro de aviso de atividade, representado por `“alive_check”`. O pacote de dados é enviado a 0.5 segundo e o de aviso de atividade a cada 3 segundos.

Quando o dispositivo se torna offline, a transmissão periódica de pacotes de dados cessa, depois de enviar um último pacote cujo campo `“data”` é `“offline”`. Entretanto, os pacotes do tipo de aviso de atividade permanecem sendo enviados, a fim de informar ao broker que mesmo offline o dispositivo ainda está conectado à rede.

#### Interface Local
A fim de permitir que o dispositivo seja manipulado de maneira local, sem necessidade de intermédio do broker, desenvolveu-se uma interface local. Dessa forma, é possível ligar, desligar e alterar o valor “medido” pelo dispositivo.

<div align="center">
  <img src="media/interface_local.png" alt="Interface local do dispositivo" height="100px" width="auto" />
  <br/> <em>Figura 1. Interface local.</em> <br/>
</div>

### API
Para manipular o dispositivo de maneira remota fez-se necessário utilizar o protocolo *HTTP*, através de uma *API REST*. Dessa forma, a partir da *API*, é possível realizar a conexão com um dispositivo, a busca de dispositivos e o envio de comandos para os dispositivos.

#### Arquitetura
Para construir a *API* utilizou-se o *framework SpringBoot*, em *Java*. Três camadas principais foram utilizadas para a organização, sendo elas: o *Controller*, o *Service* e o *Repository*.

A camada *Controller* contém funções que são executadas a depender de quando uma determinada rota é acessada. Essas funções podem receber parâmetros ou o corpo das requisições, quando necessário.

As funções da camada *Service* são responsáveis pela lógica de negócio das rotas. Dessa forma, podem acessar valores armazenados ou permitir o envio de dados via TCP.

Por fim, a camada *Repository* armazena dados que **podem** ser retornados diretamente a partir de certas rotas da API, como os dados de sensores conectados.

Ademais, para que a *API* funcione corretamente quando consumida através de uma interface gráfica no navegador, fez-se necessário configurar o CORS (*Cross-Origin Resource Sharing*). Dessa forma, a classe `CorsConfiguration`, no módulo cors, realiza os ajustes necessários, permitindo solicitações de qualquer origem e de qualquer método *HTTP*, como *GET*, *POST* e *PATCH*.

#### Rotas
`GET /api/sensor/`

Rota responsável por retornar uma lista de dados referentes a todos os dispositivos conectados ao broker.
 
`GET /api/sensor/{:id}`

Rota responsável por retornar os dados de um dispositivo em específico conectado ao broker, a partir de seu id.

`POST /api/sensor/`

Rota responsável por adicionar um novo dispositivo ao broker. O dispositivo deve estar previamente em execução.

Corpo da requisição: 
```
{
    ip: <ip_do_dispositivo>,
    port: <porta_do_dispositivo>
}
```


`PATCH /api/sensor/{:id}`

Rota responsável por enviar um comando a um dispositivo em específico, já conectado ao broker.

Corpo da requisição: 

```
{
	command: <comando>
}
```

### Broker
#### Arquitetura
- Pasta `models`
    - `DeviceModel`: contém informações referentes aos dispositivos.
    - `RequestModel`: contém informações referentes à requisição de dados.
    - `ResponseModel`: contém informações referentes às respostas advindas dos sensores.

- Pasta `repositories`
    - `DevicesRepository`: contém um hashmap referente aos dispositivos que já - estão enviando informações ou ainda vão.
    - `ResponseRepository`: contém um hashmap com a resposta mais recente de cada dispositivo.

- Pasta `controllers`
    - `BrokerController`: responsável por lidar inicialmente com as requisições HTTP.

- Pasta services
    - `BrokerService`: responsável pela lógica de negócio da API.
    - `DeviceService`: responsável pela validação de atividade dos dispositivos.

- Pasta `infra`
    - `ErrorHandler`: responsável por lidar com erros nas requisições HTTP.

- Pasta `socket`
    - `SocketServer`: responsável pela comunicação com os dispositivos via protocolos TCP e UDP.

- Pasta `utils`
    - `ResponseSplit`: responsável por organizar os dados do pacote de informações enviado pelo dispositivo.

#### Comunicação
A comunicação TCP e UDP no broker é realizada na classe `SocketServer`, e conta com métodos específicos, como:
- `receiveMessage`: responsável pelo recebimento de dados via protocolo UDP
- `sendMessageToClient`: responsável pelo envio de dados via protocolo TCP 
- `sendDeviceTCPConnection`: responsável por percorrer uma lista de IPs de dispositivos a fim de tentar estabelecer uma conexão com eles.

Para estabelecer a comunicação TCP com um dispositivo em específico é necessário utilizar a rota `POST /api/sensor/`, com o corpo adequado, contendo o IP e a porta da máquina cujo dispositivo se encontra em execução. 

Em seguida, um objeto do tipo `DeviceModel` é criado, contendo as informações de IP e porta. Esse objeto é adicionado ao hashmap `DevicesRepository`, cuja chave é o IP do dispositivo e o conteúdo é o objeto do tipo `DeviceModel`.

#### Conexão inicial do dispositivo

A função `sendDeviceTCPConnection`, na classe `SocketServer` é responsável por percorrer os dispositivos armazenados no hashmap supracitado, a fim de tentar estabelecer uma conexão TCP com eles. Se a conexão puder ser estabelecida, a mensagem `“first_conn”` é enviada ao dispositivo, que passa então a enviar mensagens via UDP para o broker.

#### Comunicação TCP

Para realizar o envio de informações do broker para o dispositivo fez-se necessário utilizar o protocolo TCP. A função responsável por realizar o envio é a `sendMessageToClient`.

Dessa forma, sempre que deseja-se enviar tais dados, uma conexão é realizada entre o broker e um dispositivo em específico, a partir de seu IP, na porta `3000`. Após o dado ser enviado com sucesso, a conexão é fechada apropriadamente. 

Caso não seja possível estabelecer a conexão, a fim de enviar um dado, uma exceção é gerada.

#### Comunicação UDP
Para receber os dados advindos dos dispositivos em execução, a partir do protocolo UDP, um loop é executado ininterruptamente, na função receiveMessage. Sempre que um dado novo chega, suas informações são armazenadas no *hashmap* da classe `ResponseRepository`, tendo como chave o IP da máquina que o enviou.

O processamento e armazenamento de dados são realizados em threads. Dessa forma, para cada dado que chega, uma thread nova é criada. Isso permite que várias informaçõe sejam processadas ao mesmo tempo, sem gerar gargalos.

#### Validação dos dispositivos
Para identificar sensores que não estão mais enviando dados, devido a problemas de conexão, é utilizado o método `invalidateDeviceConnection`, na classe DeviceRepository. Esse método torna “desconectados” os sensores que pararam de enviar dados a mais de 6 segundos.

### Interface gráfica
Para permitir que o usuário manipule os dispositivos de maneira remota, foi desenvolvida uma interface gráfica. Essa interface é um documento *web* desenvolvido em *HTML*, *CSS* e *JavaScript*, a partir do *framework ReactJS*.

A partir da interface é possível:
- Adicionar o broker
- Adicionar dispositivos
- Ligar e desligar um dispositivo
- Visualizar informações sobre os dispositivos, como dados “medidos”, status, horário de “medição” dos dados e nome.


<div align="center">
  <img src="media/interface_grafica_total.png" alt="Interface gráfica do dispositivo" height="300px" width="auto" />
  <br/> <em>Figura 2. Visão geral da interface gráfica.</em> <br/>
</div>


#### Adicionar broker
Para que a interface se comunique com o broker, é necessário adicionar o IP da máquina que está executando o broker. O campo de entrada para isso está localizando no canto superior esquerdo da tela.

<div align="center">
  <img src="media/ativar_broker.png" alt="Campo para ativar broker" height="45px" width="auto" />
  <br/> <em>Figura 3. Campo para adicionar um dispositivo.</em> <br/>
</div>

#### Adicionar dispositivo
Para adicionar um dispositivo é necessário escrever, no campo no lado superior direito da tela, o IP da máquina executando-o. 

Caso a máquina em questão não esteja executando o dispositivo, ou o IP seja inválido, nada ocorre. Na situação em que o IP é válido e a máquina está executando o aparelho, suas informações são exibidas na interface. Se o IP de uma máquina de um dispositivo que não está em execução for adicionado, assim que o aparelho passar a funcionar, será adicionado.

<div align="center">
  <img src="media/adicionar_dispositivo.png" alt="Campo para adicionar dispositivo" height="60px" width="auto" />
  <br/> <em>Figura 4. Campo para adicionar o broker.</em> <br/>
</div>

#### Ligar/Desligar dispositivo
Para ligar ou desligar um dispositivo é necessário clicar em seu quadro específico. Uma interface ao lado direito da tela irá ser mostrada, a qual contém um botão de *“power”*.

Caso o botão esteja verde, o dispositivo está ligado, e poderá ser desligado ao clicar nele. Já se o botão estiver vermelho, o aparelho em questão está desligado, e poderá ser ligado ao clicar novamente no botão.

Se o botão estiver desabilitado o dispositivo não está em execução ou a conexão foi perdida, e nada poderá ser feito até que esteja executando ou conectado novamente.

<div align="center">
  <img src="media/power_ligado.png" alt="DIspositivo ligado" height="200px" width="auto" />
  <br/> <em>Figura 5. Dispositivo ligado.</em> <br/> <br/>
</div>

<div align="center">
  <img src="media/power_desligado.png" alt="DIspositivo desligado" height="200px" width="auto" />
  <br/> <em>Figura 6. Dispositivo desligado.</em> <br/> <br/>
</div>

<div align="center">
  <img src="media/power_desconectado.png" alt="Dispositivo desconectado" height="193px" width="auto" />
  <br/> <em>Figura 7. Dispositivo desconectado.</em> <br/> <br/>
</div>

### Como executar?

Para poder manipular corretamente os dispositivos, bem como visualizar os valores gerados por eles, é necessário executar três sistemas principais, de preferência em máquinas diferentes.

Ademais, a fim de garantir que as execuções ocorram sem erros, os sistemas devem ser executados em containers docker. 

Broker
1. Navegue até o diretório principal.
Execute o comando: 
```bash
docker compose up broker –build
```


Interface 
1. Navegue até o diretório principal.
2. Execute o comando: 
```bash
docker compose up client –build
```
3. Acesse a aplicação em [http://localhost:3001](http://localhost:3001)

Dispositivo
1. Navegue até a pasta do dispositivo: 
```bash
cd Server/
```

2. Crie a imagem docker: 
```bash
docker build -t <nome_da_imagem> .
```
3. Execute o comando: 

```bash
docker container run -it -p 3000:3000 -e BROKER_IP=<ip_do_broker> -e DEVICE_NAME=<nome_do_dispositivo> <nome_da_imagem>
```

