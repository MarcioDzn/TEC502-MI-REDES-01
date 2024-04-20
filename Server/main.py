import threading
from AirConditioner import *
from Client import *

device = AirConditioner("Temperatura")
client = Client(("localhost", 3000), device)

def questions():
    while True:
        print("\n[1] Ligar dispositivo")
        print("[2] Desligar dispositivo")
        print("[3] Alterar valor padrão\n")

        resp = int(input("Escolha uma opcao: "))

        if (resp == 1):
            client.device.handle_requests("turn_on")

        elif (resp == 2):
            client.device.handle_requests("turn_off")

        elif (resp == 3):
            new_value = input("Informe o novo valor: ")
            client.device.set_default_value(new_value)


threading.Thread(target=client.send_response, name="udp_sender").start()
threading.Thread(target=client.receive_data, name="tcp_receiver").start()
threading.Thread(target=questions, name="question_thread").start()

