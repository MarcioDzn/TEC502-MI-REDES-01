import random

class Device:
    

    def __init__(self, name):
        self.name = name
        self.status = "online"



    def handle_requests(self, request):
        if request == "turn_on":
            self.status = "online"
        
        elif request == "turn_off":
            self.status = "offline"


    def get_data(self):
        data = random.randint(18, 30)
        formatted_data = f"{data}°C"

        return formatted_data