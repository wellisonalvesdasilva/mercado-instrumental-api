    alter table usuario 
       add column tx_hash_afiliado varchar(255);
       
    alter table usuario 
       add constraint UK_p1f88wc5hm8ksgdj2c65a71ty unique (tx_hash_afiliado);