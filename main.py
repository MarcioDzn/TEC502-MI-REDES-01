import threading
from AirConditioner import *
from Client import *

device = AirConditioner("Temperatura")
client = Client(("localhost", 3000), device)

def questions():
    while True:
        print("\n[1] Conectar (Não implementado)")
        print("[2] Desconectar (Não implementado)")
        print("[3] Ligar dispositivo")
        print("[4] Desligar dispositivo\n")

        resp = int(input("Escolha uma opcao: "))

        if (resp == 3):
            client.device.handle_requests("turn_on")
            print("Dispositivo ligado")

        elif (resp == 4):
            client.device.handle_requests("turn_off")
            print("Dispositivo desligado")



threading.Thread(target=client.send_response, name="udp_sender").start()
threading.Thread(target=client.receive_data, name="tcp_receiver").start()
threading.Thread(target=questions, name="question_thread").start()

