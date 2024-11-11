                Kafka Listeners - Explained

LISTENERS are what interfaces Kafka binds to. ADVERTISED_LISTENERS are how clients can connect.

Is anyone listening? 🔗
Kafka is a distributed system. Data is read from & written to the Leader for a given partition, which could be on any of the brokers in a cluster. When a client (producer/consumer) starts, it will request metadata about which broker is the leader for a partition—and it can do this from any broker. The metadata returned will include the endpoints available for the Leader broker for that partition, and the client will then use those endpoints to connect to the broker to read/write data as required.

It’s these endpoints that cause people trouble. On a single machine, running ‘bare metal’ (no VMs, no Docker), everything might be the hostname (or just localhost) and it’s easy. But once you move into more complex networking setups, and multiple nodes, you have to pay more attention to it.

Let’s assume you have more than one network. This could be things like:

Docker internal network(s) plus host machine
Brokers in the cloud (eg. AWS EC2), and on-premises machines locally (or even in another cloud)
You need to tell Kafka how the brokers can reach each other, but also make sure that external clients (producers/consumers) can reach the broker they need to

The key thing is that when you run a client, the broker you pass to it is just where it’s going to go and get the metadata about brokers in the cluster from. The actual host & IP that it will connect to for reading/writing data is based on the data that the broker passes back in that initial connection—even if it’s just a single node and the broker returned is the same as the one connected to.

For configuring this correctly, you need to understand that Kafka brokers can have multiple listeners. A listener is a combination of

Host/IP
Port
Protocol

Let’s check out some config. Often the protocol is used for the listener name too, but here let’s make it nice and clear by using abstract names for the listeners:

KAFKA_LISTENERS: LISTENER_BOB://kafka0:29092,LISTENER_FRED://localhost:9092
KAFKA_ADVERTISED_LISTENERS: LISTENER_BOB://kafka0:29092,LISTENER_FRED://localhost:9092
KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_BOB:PLAINTEXT,LISTENER_FRED:PLAINTEXT
KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_BOB