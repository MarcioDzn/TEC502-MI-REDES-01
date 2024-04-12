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
        print("[4] Desligar dispositivo")
        print("[5] Pausar dispositivo")
        print("[6] Despausar dispositivo")
        print("[7] Alterar valor padrão\n")

        resp = int(input("Escolha uma opcao: "))

        if (resp == 3):
            client.device.handle_requests("turn_on")

        elif (resp == 4):
            client.device.handle_requests("turn_off")
            
        elif (resp == 5):
            client.device.handle_requests("pause")

        elif (resp == 6):
            client.device.handle_requests("unpause")

        elif (resp == 7):
            new_value = input("Informe o novo valor: ")
            client.device.set_default_value(new_value)


threading.Thread(target=client.send_response, name="udp_sender").start()
threading.Thread(target=client.receive_data, name="tcp_receiver").start()
threading.Thread(target=questions, name="question_thread").start()

