import socket, threading, utils
from AirConditioner import *
from time import sleep

class Client:


    def __init__(self, address, device):
        self.address = address
        self.device = device
        self.isConnected = False
        self.deviceIp = ""


    def handle_tcp_connection(self):   
        sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock_tcp.bind(("0.0.0.0", 3000))

        sock_tcp.listen(1)   
  

        while True:
            conn, addr = sock_tcp.accept()
            with conn:
                data = conn.recv(1024).decode().strip()

                formatted_data = data.split(" ")
                print(formatted_data)
                if (formatted_data[0] == "first_conn"):
                    self.isConnected = True
                    self.deviceIp = formatted_data[1]
                    print("Conexão estabelecida com o broker")
                else:
                    self.device.handle_requests(data)
   

    def send_alive_check(self, sock):
        while not self.isConnected:
            pass

        while True:
            try:
                time = utils.get_current_time()
                response = f"type::alive_check, ip::{self.deviceIp}, name::{self.device.name}, time::{time}, data::none, status::online"
                sock.sendto(response.encode(), (self.address[0], self.address[1] + 2000))
            except:
                print("\n[ERRO AO ENVIAR MENSAGEM DE VERIFICAÇÃO]")
            finally:
                sleep(3)


    def send_response(self):
        client_sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        threading.Thread(target=self.send_alive_check, args=(client_sock_udp,), name="alive_checker").start()

        while not self.isConnected:
            pass
        
        while True:
            try:
                data = str(self.device.get_data())
                time = utils.get_current_time()

                sleep(0.5) # 0.5 segundos de espera

                if self.device.online:
                    sent_off_message = False
                    response = f"type::data, ip::{self.deviceIp}, name::{self.device.name}, time::{time}, data::{data}, status::online"
                    client_sock_udp.sendto(response.encode(), (self.address[0], self.address[1] + 2000))

                elif not self.device.online:
                    response = f"type::data, ip::{self.deviceIp}, name::{self.device.name}, time::{time}, data::offline, status::offline"

                    if not sent_off_message:
                        client_sock_udp.sendto(response.encode(), (self.address[0], self.address[1] + 2000))
                    sent_off_message = True


            except Exception as e:
                print("\n[ERRO AO ENVIAR DADO MEDIDO]")


# device = AirConditioner("Temperatura")
# client = Client(("localhost", 3000), device)

# threading.Thread(target=client.send_response, name="udp_sender").start()
# client.receive_data()