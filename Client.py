import socket, threading, utils
from AirConditioner import *
from time import sleep

class Client:


    def __init__(self, address, device):
        self.address = address
        self.device = device


    def receive_data(self):
        client_sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client_sock_tcp.connect(self.address)

        while True:
            try:
                request = client_sock_tcp.recv(1024).decode().strip()

                if not request:
                    break
                
                self.device.handle_requests(request)

            except ConnectionResetError:
                print("Conexão com o servidor foi encerrada.")
                break

            except Exception as e:
                print("Erro durante a recepção de dados:", e)
                break

        client_sock_tcp.close()    


    def send_response(self):
        client_sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        client_sock_udp.connect((self.address[0], self.address[1] + 2000)) # porta 5000

        while True:
            try:
                data = str(self.device.get_data())
                time = utils.get_current_time()

                sleep(0.5) # 0.5 segundos de espera

                if self.device.online:
                    sent_off_message = False
                    response = f"{self.device.name} {time} {data}"

                    client_sock_udp.sendall(response.encode())


                else:
                    response = f"{self.device.name} {time} offline"
                    if not sent_off_message:
                        client_sock_udp.sendall(response.encode())
                    sent_off_message = True



            except Exception as e:
                print("Erro durante o envio de dados:", e)


# device = AirConditioner("Temperatura")
# client = Client(("localhost", 3000), device)

# threading.Thread(target=client.send_response, name="udp_sender").start()
# client.receive_data()

