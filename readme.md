port:
    acs_base: 9001
    acs_recruit: 9002
    acs_qa: 9003            中间表tb_pl  <problemid + lableid ==>联合主键>
    acs_article: 9004       缓存处理 spring-boot-starter-data-redis  redis.host  service中引入RedisTemplate
    acs_gathering: 9005     缓存处理 spring-boot-starter-data-redis  Spring Cache
    acs_spit: 9006          SpringDataMongoDB 实现持久层  spring-boot-starter-data-mongodb
    acs_search: 9007        spring-boot-starter-data-elasticsearch
    acs_user: 9008          spring-boot-starter-data-redis  spring-boot-starter-amqp
    acs_eureka: 6868        springboot与springCloud版本
    acs_sms: 9009           spring-boot-starter-amqp  从rabbitMQ中提取消息，调用阿里大于短信接口实现短信发送 
    acs_friend: 9010
    管理后台微服务网关
    acs_manager: 9011       spring-cloud-starter-netflix-zuul  eureka-client 和zuul的依赖
    网站前台微服务网关       
    acs_web: 9012           