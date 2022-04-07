Wrapper Desafio Framework

    Apis de produção e consumo baseado em Spring 2.6.6 / Java 17

    Passo-a-passo para uso:
    postgresql v13+ criar database nomeada "desafio"
    rodar em cmd ou ubuntu wls2 comando "docker-compose up" (arquivo com multiplos brokers kafka 3.0 já configurados para a aplicação) arquivo existente na raiz do projeto.
    importar projeto na IDE de sua preferencia configurar path de compilação para nivel Java 17

    Ao todo são 6 Módulos executáveis
        - eureka-server
        - gateway-service
        - user-service
        - auth-service
        - producer-service
        - consumer-service

    Executar todos via SpringAppConfiguration (não há necessidade de subir em ordem)
    apos disponibilidade de todos os Clients utilização livre.

    Obs: eureka-server com url de gerenciamento disponivel em http://localhost:9001 para verificar disponibilidade dos clients

    Mapeamento de portas disponiveis para subir a aplicação:
        postgresql: 5432
        zookeeper: 2181
        kafka-bootstrap-servers:
            - 19092
            - 29092
            - 39092
        eureka-server: 9001
        gateway-service: 8080
        auth-service: 8081
        user-service: 8082
        producer-service: 8083
        consumer-service: 8084

    Necessário manter portas disponíveis para funcionamento de todos os clients da aplicação.

OBS: Há um limite 15mb para transferencia de bus kafka entre consumer/producer