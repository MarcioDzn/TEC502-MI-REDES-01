import socket, threading, utils
from AirConditioner import *
from time import sleep

class Client:


    def __init__(self, address, device):
        self.address = address
        self.device = device


    def receive_data(self):
        server_on = False
        while True:
            try:
                # reconecta o client ao broker
                while not server_on:
                    try:
                        client_sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        client_sock_tcp.connect(self.address)

                        server_on = True
                    except Exception as e:
                        pass

                    sleep(3) # 3 segundos 


                request = client_sock_tcp.recv(1024).decode().strip()

                if not request:
                    break
                
                self.device.handle_requests(request)

            except ConnectionResetError:
                server_on = False

            except Exception as e:
                print("Erro durante a recepção de dados:", e)
                

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
                    
                    if self.device.status == "unpaused":
                        sent_pause_message = False
                        client_sock_udp.sendall(response.encode())

                    elif self.device.status == "paused":
                        if not sent_pause_message:
                            client_sock_udp.sendall(response.encode())
                        sent_pause_message = True


                elif not self.device.online:
                    response = f"{self.device.name} {time} offline"

                    if not sent_off_message:
                        client_sock_udp.sendall(response.encode())
                    sent_off_message = True


            except Exception as e:
                print("Erro durante o envio de dados:", e)
                break


# device = AirConditioner("Temperatura")
# client = Client(("localhost", 3000), device)

# threading.Thread(target=client.send_response, name="udp_sender").start()
# client.receive_data()