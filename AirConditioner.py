from Device import *

class AirConditioner(Device):

    
    def __init__(self, name):
        super().__init__(name)


    def get_data(self):
        data = random.randint(18, 30)
        formatted_data = f"{data}°C"

        return formatted_data