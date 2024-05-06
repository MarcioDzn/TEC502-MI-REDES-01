import random

class Device:
    

    def __init__(self, name):
        self.name = name
        self.online = True


    # lida com os comandos
    def handle_requests(self, request):
        if not self.online:
            if request == "turn_on":
                self.online = True

        elif self.online:
            if request == "turn_off":
                self.online = False


    def get_data(self):
        data = random.randint(18, 30)
        formatted_data = f"{data}°C"

        return formatted_data