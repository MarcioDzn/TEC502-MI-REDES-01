import socket
from datetime import date

class Client:
    def __init__(self, address):
        self.online = True
        self.address = address
        self.client_sock_tcp = self.initialize_client_tcp()

    
    def get_time(self):
        current_time = date.today()
        return current_time
    

    def handle_requests(self, request):
        if request == "turn_on":
            self.online = True
            response = f"{request} ligado"

        elif not self.online:
            return f"{request} dispositivo_esta_desligado"

        elif request == "get_time":
            response = f"{request} {self.get_time()}"

        elif request == "turn_off":
            self.online = False
            response = f"{request} desligado"

        else:
            response = f"{request} comando_invalido"

        return response


    def initialize_client_tcp(self):
        client_sock_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        client_sock_tcp.connect(self.address)

        return client_sock_tcp


    def receive_data(self):
        while True:
            try:
                request = self.client_sock_tcp.recv(1024).decode().strip()

                if not request:
                    break
                
                
                response = self.handle_requests(request)

                self.send_response(response)

            except ConnectionResetError:
                print("Conexão com o servidor foi encerrada.")
                break

            except Exception as e:
                print("Erro durante a recepção de dados:", e)
                break

        self.client_sock_tcp.close()    


    def send_response(self, response):
        try:
            client_sock_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            client_sock_udp.connect((self.address[0], self.address[1] + 2000)) # porta 5000
            client_sock_udp.sendall(response.encode())
            
            client_sock_udp.close()

        except Exception as e:
            print("Erro durante o envio de dados:", e)


client = Client(("localhost", 3000))

client.receive_data()
