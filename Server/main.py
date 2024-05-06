import threading
from AirConditioner import *
from Client import *
import os

ip = os.getenv('BROKER_IP')
name = os.getenv('DEVICE_NAME')

device = AirConditioner(name)
client = Client(ip, 3002, 5000, device)

def questions():
    client.device.get_options()


threading.Thread(target=client.send_response, name="udp_sender").start()
threading.Thread(target=client.handle_tcp_connection, name="tcp_receiver").start()
threading.Thread(target=questions, name="question_thread").start()

