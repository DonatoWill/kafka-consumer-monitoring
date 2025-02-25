import { check } from 'k6';
import { writer, produce, reader, consume, createTopic } from 'k6/x/kafka';

// Kafka configuration
const brokers = ['localhost:9092']; // Kafka brokers
const topic = 'order-in'; // Kafka topic name

const producer = writer(brokers, topic);

export let options = {
  vus: 5000, // Number of virtual users to simulate
  duration: '30s', // Duration of the test
};

function getRandomInt(max = 1000000) {
  return Math.floor(Math.random() * max + 1);
}

export default function () {
  const key = 'key-'+getRandomInt(); // Unique key for each message
  const value = JSON.stringify({
    clientId: 'Client ' + getRandomInt(),
    product: 'Product ' + getRandomInt(),
    totalAmount: Math.random() * 100,
    status: 'CREATED',
  });

  const messages = [{ key: key, value: value }];

  // Send messages to Kafka
  const sendResult = produce(producer, messages);

  // Check if the message was sent successfully
  check(sendResult, {
      'is sent': (err) => err == undefined,
    });
}

export function teardown() {
  producer.close(); // Close the Kafka writer after the test
}
