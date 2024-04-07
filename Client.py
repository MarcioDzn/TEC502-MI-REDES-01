import socket
from datetime import date

class Client:
    def __init__(self, address):
        self.address = address
        self.client_sock_tcp = self.initialize_client_tcp()

    
    def get_time(self):
        current_time = date.today()
        return current_time



    def initialize_client_tcp(self):
        client_sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        client_sock_tcp.connect(self.address)

        return client_sock_tcp


    def receive_data(self):
        while True:
            try:
                response = self.client_sock_tcp.recv(1024).decode().strip()

                if not response:
                    break
                
                if response == "get_time":
                    data = f"{response} {self.get_time()}"

                else:
                    data = f"{response} comando_invalido"


                print(data)
                self.send_data(data)

            except ConnectionResetError:
                print("Conexão com o servidor foi encerrada.")
                break

            except Exception as e:
                print("Erro durante a recepção de dados:", e)
                break

        self.client_sock_tcp.close()    


    def send_data(self, data):
        try:
            client_sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            client_sock_udp.connect((self.address[0], self.address[1] + 2000)) # porta 5000
            client_sock_udp.sendall(data.encode())
            
            client_sock_udp.close()

        except Exception as e:
            print("Erro durante o envio de dados:", e)


client = Client(("localhost", 3000))

client.receive_data()
