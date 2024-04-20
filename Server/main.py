import threading
from AirConditioner import *
from Client import *

device = AirConditioner("Temperatura")
client = Client(("localhost", 3000), device)

def questions():
    client.device.get_options()


threading.Thread(target=client.send_response, name="udp_sender").start()
threading.Thread(target=client.receive_data, name="tcp_receiver").start()
threading.Thread(target=questions, name="question_thread").start()

